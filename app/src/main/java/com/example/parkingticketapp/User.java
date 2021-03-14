package com.example.parkingticketapp;

public class User {
    public String type, number, hours;

    public User() {

    }

    public User(String type, String number, String hours) {
        this.type = type;
        this.number = number;
        this.hours = hours;
    }
}
