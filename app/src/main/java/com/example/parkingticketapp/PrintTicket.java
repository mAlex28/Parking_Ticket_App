package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PrintTicket extends AppCompatActivity {

    private TextView timeview, numberview, typeview, hoursview, priceview;
    private ProgressBar mProgressBar;
    private Button printTicketBtn;
    private String time, number, type, hours, total;
    private int i = 0;
    private Handler handler = new Handler();

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

        mProgressBar = findViewById(R.id.progressBar);
        printTicketBtn = findViewById(R.id.printTicket);

        printTicketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                i = mProgressBar.getProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (i < 100) {
                            i += 1;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(i);
                                    startActivity(new Intent(PrintTicket.this, GetStarted.class));
                                }
                            });
                            try {
                                // Sleep for 50 ms to show progress you can change it as well.
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}