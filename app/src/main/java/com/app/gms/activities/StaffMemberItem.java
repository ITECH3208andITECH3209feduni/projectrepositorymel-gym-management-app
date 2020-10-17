package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.app.gms.R;

public class StaffMemberItem extends AppCompatActivity {

    TextView tv_name,tv_age,tv_gender,tv_contact,tv_address,tv_email,tv_password;
    String s_name,s_age,s_gender,s_contact,s_address,s_email,s_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_member_item);
        bindViews();
        Intent intent=new Intent();
        s_name=intent.getStringExtra("name");
        s_age=intent.getStringExtra("age");
        s_gender=intent.getStringExtra("gender");
        s_contact=intent.getStringExtra("contact");
        s_address=intent.getStringExtra("address");
        s_email=intent.getStringExtra("email");
        s_password=intent.getStringExtra("password");
        tv_name.setText(s_name);
        tv_age.setText(s_name);
        tv_gender.setText(s_name);
        tv_contact.setText(s_name);
        tv_address.setText(s_name);
        tv_email.setText(s_name);
        tv_password.setText(s_name);
    }

    private void bindViews() {
        tv_name=findViewById(R.id.name);
        tv_age=findViewById(R.id.age);
        tv_gender=findViewById(R.id.gender);
        tv_contact=findViewById(R.id.contact);
        tv_address=findViewById(R.id.address);
        tv_email=findViewById(R.id.email);
        tv_password=findViewById(R.id.password);
    }
}
