package com.example.parkingticketapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private ImageButton logout, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout = (ImageButton) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        profile = (ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.logout:
                logoutAlert();
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    public void logoutAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want log out?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Object button;
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(Dashboard.this,"Logging Out...",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Dashboard.this, Login.class));
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}