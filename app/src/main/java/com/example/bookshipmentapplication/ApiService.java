package com.example.bookshipmentapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("getRates") 
    Call<List<ShippingRate>> getShippingRates();
}
