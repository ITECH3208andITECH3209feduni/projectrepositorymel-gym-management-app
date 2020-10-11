package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.gms.R;
import com.app.gms.models.Timings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTiming extends AppCompatActivity {

    EditText saturday,sunday,monday,tuesday,wednesday,thursday,friday,saturday1,sunday1,monday1,tuesday1,wednesday1,thursday1,friday1;
    Button submit;
    DatabaseReference ref;
    String sat,sun,mon,tue,wed,thu,fri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timing);
        bindViews();
        ref= FirebaseDatabase.getInstance().getReference().child("Timings");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }
    private void addData() {
        String sat=saturday.getText().toString()+" "+"TO"+" "+saturday1.getText().toString();
        String sun=sunday.getText().toString()+" "+"TO"+" "+sunday1.getText().toString();
        String mon=monday.getText().toString()+" "+"TO"+" "+monday1.getText().toString();
        String tue=tuesday.getText().toString()+" "+"TO"+" "+tuesday1.getText().toString();
        String wed=wednesday.getText().toString()+" "+"TO"+" "+wednesday1.getText().toString();
        String thu=thursday.getText().toString()+" "+"TO"+" "+thursday1.getText().toString();
        String fri=friday.getText().toString()+" "+"TO"+" "+friday1.getText().toString();
        Timings timings=new Timings();
        timings.setSaturday(sat);
        timings.setSunday(sun);
        timings.setMonday(mon);
        timings.setTuesday(tue);
        timings.setWednesday(wed);
        timings.setThursday(thu);
        timings.setFriday(fri);
        ref.push().setValue(timings);
        Toast.makeText(getApplicationContext(),"Time Added",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(AddTiming.this, AdminActivity.class);
        startActivity(intent);
        finish();
    }
    private void bindViews() {
        saturday=findViewById(R.id.sat1);
        saturday1=findViewById(R.id.sat2);
        sunday=findViewById(R.id.sun1);
        sunday1=findViewById(R.id.sun2);
        monday=findViewById(R.id.mon1);
        monday1=findViewById(R.id.mon2);
        tuesday=findViewById(R.id.tu1);
        tuesday1=findViewById(R.id.tu2);
        wednesday=findViewById(R.id.wed1);
        wednesday1=findViewById(R.id.wed2);
        thursday=findViewById(R.id.thu1);
        thursday1=findViewById(R.id.thu2);
        friday=findViewById(R.id.fri1);
        friday1=findViewById(R.id.fri2);
        submit=findViewById(R.id.submitbtn);
    }
}
