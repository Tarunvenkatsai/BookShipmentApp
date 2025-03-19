package com.example.bookshipmentapplication.api;

import com.example.bookshipmentapplication.models.ShippingRateResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("getShippingRate")
    Call<ShippingRateResponse> getShippingRate(
            @Query("pickup") String pickup,
            @Query("delivery") String delivery,
            @Query("courier") String courier
    );
}
