package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView mCar, mBike, mWheeler, mOther;
    TextView mTypeCar, mTypeBike, mTypeWheeler, mTypeOther;

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

        mTypeCar = (TextView) findViewById(R.id.typeCar);
        mTypeBike = (TextView) findViewById(R.id.typeBike);
        mTypeWheeler = (TextView) findViewById(R.id.typeWheeler);
        mTypeOther = (TextView) findViewById(R.id.typeOther);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car:
                String car = mTypeCar.getText().toString();
                Intent i = new Intent(MainActivity.this, Ticket.class);
                i.putExtra("type", car);
                startActivity(i);
                break;
            case R.id.other:
                String other = mTypeOther.getText().toString();
                i = new Intent(MainActivity.this, Ticket.class);
                i.putExtra("type", other);
                startActivity(i);
                break;
            case R.id.bike:
                String bike = mTypeBike.getText().toString();
                i = new Intent(MainActivity.this, Ticket.class);
                i.putExtra("type", bike);
                startActivity(i);
                break;
            case R.id.wheeler:
                String wheeler = mTypeWheeler.getText().toString();
                i = new Intent(MainActivity.this, Ticket.class);
                i.putExtra("type", wheeler);
                startActivity(i);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}