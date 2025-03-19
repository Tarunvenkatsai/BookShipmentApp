package com.example.bookshipmentapplication;

public class ShippingRate {
    private String courier_name;  // Matches API field
    private double price;

    public String getCourierName() {
        return courier_name; // Correct field mapping
    }

    public double getPrice() {
        return price;
    }
}

