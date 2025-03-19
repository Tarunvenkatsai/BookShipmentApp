package com.example.bookshipmentapplication.models;

import com.google.gson.annotations.SerializedName;

public class ShippingRateResponse {
    @SerializedName("rate")
    private double rate;

    public double getRate(){
        return rate;
    }

}
