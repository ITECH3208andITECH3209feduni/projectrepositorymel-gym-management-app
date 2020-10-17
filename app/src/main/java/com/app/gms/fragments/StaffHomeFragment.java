package com.app.gms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.app.gms.R;
import com.app.gms.models.StaffAttendance;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class StaffHomeFragment extends Fragment {

    TextView tv_date;
    RadioButton radioButton;
    Button btn;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_home, container, false);
        bindViews(view);
        Calendar calendar=Calendar.getInstance();
        String current_date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        tv_date.setText(current_date);
        ref= FirebaseDatabase.getInstance().getReference().child("StaffAttendance");
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
                    StaffAttendance staffAttendance=new StaffAttendance();
                    staffAttendance.setDate(date);
                    staffAttendance.setAttendance(attendance);
                    ref.push().setValue(staffAttendance);
                    Toast.makeText(getContext(),"Attendance Added",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Select Present",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void bindViews(View view) {
        tv_date=view.findViewById(R.id.date);
        radioButton=view.findViewById(R.id.attendance);
        btn=view.findViewById(R.id.submitbtn);
    }
}
