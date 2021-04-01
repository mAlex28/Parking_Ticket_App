package com.example.parkingticketapp;

import android.app.Application;

import com.mazenrashed.printooth.Printooth;

public class Printer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Printooth.INSTANCE.init(this);
    }
}
