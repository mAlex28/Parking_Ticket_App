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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getvVehicleNo() {
        return vVehicleNo;
    }

    public void setvVehicleNo(String vVehicleNo) {
        this.vVehicleNo = vVehicleNo;
    }

    public String getvHours() {
        return vHours;
    }

    public void setvHours(String vHours) {
        this.vHours = vHours;
    }

    public String getvTotal() {
        return vTotal;
    }

    public void setvTotal(String vTotal) {
        this.vTotal = vTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
