package com.example.bookshipmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private TextView textViewAmount;
    private EditText editTextCardNumber, editTextExpiry, editTextCVV;
    private Button btnPayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

    
        textViewAmount = findViewById(R.id.textViewAmount);
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextExpiry = findViewById(R.id.editTextExpiry);
        editTextCVV = findViewById(R.id.editTextCVV);
        btnPayNow = findViewById(R.id.btnPayNow);

        
        Intent intent = getIntent();
        if (intent.hasExtra("shippingCost")) {
            String shippingCost = intent.getStringExtra("shippingCost");
            textViewAmount.setText("Total Amount: $" + shippingCost);
        }

      
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void processPayment() {
        String cardNumber = editTextCardNumber.getText().toString().trim();
        String expiry = editTextExpiry.getText().toString().trim();
        String cvv = editTextCVV.getText().toString().trim();

        if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Please enter valid payment details", Toast.LENGTH_SHORT).show();
            return;
        }

       
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_LONG).show();
        finish(); 
    }
}
