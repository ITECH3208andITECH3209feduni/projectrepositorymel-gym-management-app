package com.app.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.app.gms.R;
import com.app.gms.models.MembersAttendance;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class MemberAttendance extends AppCompatActivity {

    TextView tv_date;
    Toolbar toolbar;
    RadioButton radioButton;
    Button btn;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_attendance);
        bindViews();
        Calendar calendar=Calendar.getInstance();
        String current_date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tv_date.setText(current_date);
        ref= FirebaseDatabase.getInstance().getReference().child("MembersAttendance");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance();
            }

            private void addAttendance() {
                if (radioButton.isChecked())
                {
                    String date=tv_date.getText().toString();
                    String attendance="Present";
                    MembersAttendance memberAttendance=new MembersAttendance();
                    memberAttendance.setDate(date);
                    memberAttendance.setAttendance(attendance);
                    ref.push().setValue(memberAttendance);
                    Toast.makeText(getApplicationContext(),"Attendance Added",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MemberAttendance.this,MembersActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Select Present",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void bindViews() {
        tv_date=findViewById(R.id.date);
        toolbar=findViewById(R.id.toolbar);
        radioButton=findViewById(R.id.attendance);
        btn=findViewById(R.id.submitbtn);
    }
}
