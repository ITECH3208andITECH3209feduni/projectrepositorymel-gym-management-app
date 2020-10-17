package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gms.R;

public class PaymentActivity extends AppCompatActivity {

    Button btnPay;
    TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bindViews();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        String price=extras.getString("Price");
        tvPrice.setText(price);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity.this,MembersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bindViews() {
        btnPay=findViewById(R.id.submitbtn);
        tvPrice=findViewById(R.id.price1);
    }
}
