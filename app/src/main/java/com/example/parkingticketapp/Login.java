package com.example.parkingticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView register, resetPassword, getStarted;
    private EditText mEmail, mPassword;
    private Button mLogin;

    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.adminRegister);
        register.setOnClickListener(this);

        resetPassword = (TextView) findViewById(R.id.forgotPassword);
        resetPassword.setOnClickListener(this);

        getStarted = (TextView) findViewById(R.id.getStarted);
        getStarted.setOnClickListener(this);

        mLogin = (Button) findViewById(R.id.loginBtn);
        mLogin.setOnClickListener(this);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adminRegister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ResetPassword.class));
                break;
            case R.id.getStarted:
                startActivity(new Intent(this, GetStarted.class));
            case R.id.loginBtn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = mEmail.getText().toString().trim();
        String pass = mPassword.getText().toString().trim();

        if (email.isEmpty()) {
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Invalid email");
            mEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //redirect to user profile
                    startActivity(new Intent(Login.this, Dashboard.class));
                } else {
                    Toast.makeText(Login.this, "Invalid credentials! Failed to log in", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}