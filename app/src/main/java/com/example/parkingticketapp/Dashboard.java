package com.example.parkingticketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private ImageButton logout, profile;
    private Handler handler;
    private Runnable r;

    ListView vehicleView;
    List<Vehicle> vehicleList;

    DatabaseReference vehicleReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(this);

        vehicleView = findViewById(R.id.vehicleListView);
        vehicleList = new ArrayList<>();

        vehicleReference = FirebaseDatabase.getInstance().getReference("VehicleInfo");

        vehicleReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleList.clear();

                for (DataSnapshot vehicleDataSnap : snapshot.getChildren()) {
                    Vehicle vehicle = vehicleDataSnap.getValue(Vehicle.class);
                    vehicleList.add(vehicle);
                }

                ListAdapter adapter = new ListAdapter(Dashboard.this, vehicleList);
                vehicleView.setAdapter(adapter);
                vehicleView.setStackFromBottom(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Dashboard.this, "user is inactive from last 10 minutes",Toast.LENGTH_SHORT).show();
            }
        };
        startHandler();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();//stop first and then start
        startHandler();
    }
    public void stopHandler() {
        handler.removeCallbacks(r);
    }
    public void startHandler() {
        handler.postDelayed(r, 10*60*1000); //for 5 minutes
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.logout:
                logoutAlert();
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