package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.gms.R;

public class SubscibedPlan extends AppCompatActivity {

    Button btnUnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscibed_plan);
        btnUnSub=findViewById(R.id.submitbtn);
        btnUnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Plan Unsubscribed",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
