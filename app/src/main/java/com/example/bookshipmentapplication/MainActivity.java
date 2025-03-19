package com.example.bookshipmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPickup, editTextDelivery;
    private Spinner spinnerShipping;
    private TextView textViewRates;
    private Button btnPriceCalculation, btnProceedPayment;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPickup = findViewById(R.id.editTextPickup);
        editTextDelivery = findViewById(R.id.editTextDelivery);
        spinnerShipping = findViewById(R.id.spinnerShipping);
        textViewRates = findViewById(R.id.textViewRates);
        btnPriceCalculation = findViewById(R.id.btnPriceCalculation);
        btnProceedPayment = findViewById(R.id.btnProceedPayment);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.shipping_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        
        spinnerShipping.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnPriceCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchShippingRates();
            }
        });

        btnProceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPaymentScreen();
            }
        });
    }

    private void fetchShippingRates() {
        String selectedCourier = spinnerShipping.getSelectedItem().toString(); 
        String pickup = editTextPickup.getText().toString().trim();
        String delivery = editTextDelivery.getText().toString().trim();

        if (pickup.isEmpty() || delivery.isEmpty()) {
            Toast.makeText(this, "Please enter both addresses", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<ShippingRate>> call = apiService.getShippingRates();
        call.enqueue(new Callback<List<ShippingRate>>() {
            @Override
            public void onResponse(Call<List<ShippingRate>> call, Response<List<ShippingRate>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ShippingRate> rates = response.body();

                    ShippingRate selectedRate = null;
                    for (ShippingRate rate : rates) {
                        if (rate.getCourierName().equalsIgnoreCase(selectedCourier)) {
                            selectedRate = rate;
                            break; // Stop loop once we find the match
                        }
                    }


                    if (selectedRate != null) {
                        textViewRates.setText(selectedRate.getCourierName() + ": $" + selectedRate.getPrice());
                    } else {
                        textViewRates.setText("No matching shipping rate found");
                    }
                } else {
                    textViewRates.setText("No data available");
                }
            }

            @Override
            public void onFailure(Call<List<ShippingRate>> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch rates", t);
                textViewRates.setText("Error fetching rates");
            }
        });
    }




    private void goToPaymentScreen() {
        String shippingCost = textViewRates.getText().toString().trim();

        if (shippingCost.isEmpty() || shippingCost.equals("No data available") || shippingCost.equals("Error fetching rates")) {
            Toast.makeText(this, "Please calculate the price before proceeding", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
        intent.putExtra("shippingCost", shippingCost);
        startActivity(intent);
    }
}
