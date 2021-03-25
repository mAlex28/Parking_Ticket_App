package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Ticket extends AppCompatActivity {
    TextView mTotPrice;
    EditText mHours, mVehicleNo;
    Button printTicket;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Intent i = getIntent();
        type = i.getStringExtra("type");

        mTotPrice = (TextView) findViewById(R.id.totalPrice);
        mHours = (EditText) findViewById(R.id.hours);
        mVehicleNo = (EditText) findViewById(R.id.vehicleNO);
        printTicket = (Button) findViewById(R.id.btnPrint);

        TextChangeHandler tch = new TextChangeHandler();
        mHours.addTextChangedListener(tch);
    }

    private void calculate() {
        String stayingHours = mHours.getText().toString();

        try {
            // convert hours to int
            int hoursInInt = Integer.parseInt(stayingHours);
            // store price of each vehicle per hour
            int price = 0;
            if (type.equals("Car")) {
                price = 50;
            } else if (type.equals("Other")) {
                price = 70;
            } else if (type.equals("Bike")) {
                price = 20;
            } else if (type.equals("Tuk-tuk")) {
                price = 20;
            }

            // calculate total price
            int totalPrice = price * hoursInInt;
            mTotPrice.setText(totalPrice);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged(Editable e){
            calculate();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after){
        }

        public void onTextChanged(CharSequence s, int start, int before, int after){
        }
    }
}