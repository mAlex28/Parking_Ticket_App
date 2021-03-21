package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView mCar, mBike, mWheeler, mOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCar = (CardView) findViewById(R.id.car);
        mCar.setOnClickListener(this);

        mBike = (CardView) findViewById(R.id.bike);
        mBike.setOnClickListener(this);

        mWheeler = (CardView) findViewById(R.id.wheeler);
        mWheeler.setOnClickListener(this);

        mOther = (CardView) findViewById(R.id.other);
        mOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car:
            case R.id.other:
            case R.id.bike:
            case R.id.wheeler:
                startActivity(new Intent(this, Ticket.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}