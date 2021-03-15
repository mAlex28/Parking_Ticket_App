package com.example.parkingticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser admin;
    private DatabaseReference reference;
    private String adminId;

    private TextView fullnameTextView, usernameTextView, emailTextView;

    private Button resetPassword, goToDashboard, editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        resetPassword = (Button) findViewById(R.id.resetPassBtn);
        resetPassword.setOnClickListener(this);

        goToDashboard = (Button) findViewById(R.id.goToDashboard);
        goToDashboard.setOnClickListener(this);

        editProfile = (Button) findViewById(R.id.editButton);
        editProfile.setOnClickListener(this);

        admin = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Admins");
        adminId = admin.getUid();

        fullnameTextView = (TextView) findViewById(R.id.displayFullname);
        usernameTextView = (TextView) findViewById(R.id.displayUsername);
        emailTextView = (TextView) findViewById(R.id.displayEmail);

        reference.child(adminId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin adminProfile = snapshot.getValue(Admin.class);

                if (adminProfile != null) {
                    String fullname = adminProfile.fullname;
                    String username = adminProfile.username;
                    String email = adminProfile.email;

                    fullnameTextView.setText(fullname);
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Oops... something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetPassBtn:
                startActivity(new Intent(this, ResetPassword2.class));
                break;
            case R.id.goToDashboard:
                startActivity(new Intent(this, Dashboard.class));
                break;
            case R.id.editButton:
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("fullname", fullnameTextView.getText().toString());
                i.putExtra("username", usernameTextView.getText().toString());
                i.putExtra("email", emailTextView.getText().toString());
                startActivity(i);
                break;
        }
    }
}