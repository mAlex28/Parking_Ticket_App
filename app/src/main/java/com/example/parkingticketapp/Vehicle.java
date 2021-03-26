package com.example.parkingticketapp;

public class Vehicle {
    public String type, vVehicleNo;
    public String vHours;
    public String vTotal;
    public String date;

    public Vehicle() {

    }

    public Vehicle(String date, String vVehicleNo, String type, String vHours, String vTotal) {
        this.date = date;
        this.vVehicleNo = vVehicleNo;
        this.type = type;
        this.vHours = vHours;
        this.vTotal = vTotal;

    }
}
