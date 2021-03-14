package com.example.parkingticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");
    private FirebaseAuth mAuth;

    private EditText mFullName, mUsername, mPassword, mConfPassword, mEmail;
    private Button mRegisterBtn;
    private TextView mLoginPage;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mLoginPage = (TextView) findViewById(R.id.adminLogin);
        mLoginPage.setOnClickListener(this);

        mRegisterBtn = (Button) findViewById(R.id.registerBtn);
        mRegisterBtn.setOnClickListener(this);

        mFullName = (EditText) findViewById(R.id.fullname);
        mUsername = (EditText) findViewById(R.id.username);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfPassword = (EditText) findViewById(R.id.confpassword);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adminLogin:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.registerBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = mEmail.getText().toString().trim();
        String fullname = mFullName.getText().toString().trim();
        String user = mUsername.getText().toString().trim();
        String pass = mPassword.getText().toString().trim();
        String confpass = mConfPassword.getText().toString().trim();

        if (fullname.isEmpty()) {
            mFullName.setError("Full name is required");
            mFullName.requestFocus();
            return;
        }

        if (user.isEmpty()) {
            mUsername.setError("Username is required");
            mUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Please provide valid email");
            mEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            mPassword.setError("Weak password");
            mPassword.requestFocus();
            return;
        }

        if (confpass.isEmpty()) {
            mConfPassword.setError("Password is required");
            mConfPassword.requestFocus();
            return;
        }else if (!pass.equals(confpass)) {
            mConfPassword.setError("Passwords do not match");
            mConfPassword.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Admin admin = new Admin(fullname, user, email);

                            FirebaseDatabase.getInstance().getReference("Admins")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Admin registered successfully", Toast.LENGTH_LONG).show();
                                        mProgressBar.setVisibility(View.GONE);

                                        // redirect to login
                                    } else {
                                        Toast.makeText(Register.this, "Admin registration failed", Toast.LENGTH_LONG).show();
                                        mProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Admin registration failed", Toast.LENGTH_LONG).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}