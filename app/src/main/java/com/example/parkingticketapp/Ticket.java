package com.example.parkingticketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Ticket extends AppCompatActivity {
    private TextView mTotPrice;
    private EditText mHours, mVehicleNo;
    private Button printTicket;
    private String type;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        Intent i = getIntent();
        type = i.getStringExtra("type");

        mTotPrice = findViewById(R.id.totalPrice);
        mHours = findViewById(R.id.hours);
        mVehicleNo = findViewById(R.id.vehicleNO);
        printTicket = findViewById(R.id.btnPrint);

        // get current date and time
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        TextChangeHandler tch = new TextChangeHandler();
        mHours.addTextChangedListener(tch);

        // change vehicle no to capital
        mVehicleNo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        printTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vHours = mHours.getText().toString().trim();
                String vVehicleNo = mVehicleNo.getText().toString().trim();
                String vTotal = mTotPrice.getText().toString().trim();

                int checkHours = Integer.parseInt(vHours);

                // check if the fields are empty
                if (TextUtils.isEmpty(vHours) || TextUtils.isEmpty(vVehicleNo)) {
                    Toast.makeText(Ticket.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                    return;

                } else if (checkHours > 5) {
                    Toast.makeText(Ticket.this, "Maximum five hours allowed", Toast.LENGTH_SHORT).show();
                    return;

                } else {

                    // store vehicle info in firebase
                    Vehicle vehicle = new Vehicle(date, vVehicleNo, type, vHours, vTotal);
                    reference = FirebaseDatabase.getInstance().getReference().child("VehicleInfo");
                    reference.push().setValue(vehicle);

                    Bundle bundle = new Bundle();
                    bundle.putString("time", date);
                    bundle.putString("number", vVehicleNo);
                    bundle.putString("type", type);
                    bundle.putString("hours", vHours);
                    bundle.putString("total", vTotal);
                    Intent i = new Intent(Ticket.this, PrintTicket.class);
                    i.putExtras(bundle);
                    startActivity(i);
                }
            }
        });

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(Ticket.this, GetStarted.class);
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

            mTotPrice.setText(String.valueOf(totalPrice));

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