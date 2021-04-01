package com.example.parkingticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Timer;
import java.util.TimerTask;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser admin;
    private DatabaseReference reference;
    private String adminId;

    private EditText updateFullname, updateUsername, updateEmail;

    private Button resetPassword, goToDashboard, editProfile;

    String _USERNAME = null;
    String _NAME = null;
    String _EMAIL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        resetPassword = (Button) findViewById(R.id.resetPassBtn);
        resetPassword.setOnClickListener(this);

        goToDashboard = (Button) findViewById(R.id.goToDashboard);
        goToDashboard.setOnClickListener(this);

        admin = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Admins");
        adminId = admin.getUid();

        updateFullname = (EditText) findViewById(R.id.displayFullname);
        updateUsername = (EditText) findViewById(R.id.displayUsername);
        updateEmail = (EditText) findViewById(R.id.displayEmail);

        displayAdmin();

        editProfile = (Button) findViewById(R.id.editButton);
        editProfile.setOnClickListener(this);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(Profile.this, Dashboard.class);
                startActivity(intent);
                finishscreen();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 60000);
    }

    private void finishscreen() {
        this.finish();
    }

    private void displayAdmin() {
        reference.child(adminId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin adminProfile = snapshot.getValue(Admin.class);

                if (adminProfile != null) {
                    String fullname = adminProfile.fullname;
                    String username = adminProfile.username;
                    String email = adminProfile.email;

                    updateFullname.setText(fullname);
                    updateUsername.setText(username);
                    updateEmail.setText(email);

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
                updateData();
                break;
        }
    }

    private void updateData() {
        _NAME = updateFullname.getText().toString();
        _USERNAME = updateUsername.getText().toString();
        _EMAIL = updateEmail.getText().toString();

        if (isNameChanged() || isUsernameChanged() || isEmailChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error occurred. Unable to update data", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailChanged() {
        if (_EMAIL.equals(updateEmail.getText().toString())) {
            reference.child(adminId).child("email").setValue(updateEmail.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isUsernameChanged() {
        if (_USERNAME.equals(updateUsername.getText().toString())) {
            reference.child(adminId).child("username").setValue(updateUsername.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (_NAME.equals(updateFullname.getText().toString())) {
            reference.child(adminId).child("fullname").setValue(updateFullname.getText().toString());
            return true;
        } else {
            return false;
        }
    }
}