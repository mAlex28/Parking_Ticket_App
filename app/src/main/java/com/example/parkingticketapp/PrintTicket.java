package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrintTicket extends AppCompatActivity {

    private TextView timeview, numberview, typeview, hoursview, priceview;
    private Button printTicketBtn;
    private String time, number, type, hours, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_ticket);

        // get values from previous page
        Bundle bundle = getIntent().getExtras();
        time = bundle.getString("time");
        number = bundle.getString("number");
        type = bundle.getString("type");
        hours = bundle.getString("hours");
        total = bundle.getString("total");

        timeview = findViewById(R.id.currentTime);
        numberview = findViewById(R.id.vehicleNo);
        typeview = findViewById(R.id.vehicleType);
        hoursview = findViewById(R.id.vehicleHours);
        priceview = findViewById(R.id.vehiclePrice);

        // set values to the text views
        timeview.setText(time);
        numberview.setText(number);
        typeview.setText(type);
        hoursview.setText(hours);
        priceview.setText(total);

        printTicketBtn = findViewById(R.id.printTicket);



    }
}