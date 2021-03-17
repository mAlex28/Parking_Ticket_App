package com.example.parkingticketapp;

public class User {
    public String type, number;
    public int hours;
    public float total;

    public User() {

    }

    public User(String type, String number, int hours, float total) {
        this.type = type;
        this.number = number;
        this.hours = hours;
        this.total = total;
    }
}
