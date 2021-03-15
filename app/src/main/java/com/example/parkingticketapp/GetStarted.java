package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetStarted extends AppCompatActivity implements View.OnClickListener {

    private TextView admin;
    private Button bookTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);


        admin = (TextView) findViewById(R.id.goToAdmin);
        admin.setOnClickListener(this);

        bookTicket = (Button) findViewById(R.id.buttonTicket);
        bookTicket.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToAdmin:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.buttonTicket:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}