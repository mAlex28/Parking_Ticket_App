package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText editFullname, editUsername, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String fullname = data.getStringExtra("fullname");
        String username = data.getStringExtra("username");
        String email = data.getStringExtra("email");

        editFullname = findViewById(R.id.editFullname);
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);

        editFullname.setText(fullname);
        editUsername.setText(username);
        editEmail.setText(email);

        Log.d(TAG, "onCreate:" + fullname + " " + username + " " + email);
    }
}