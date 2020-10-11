package com.app.gms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.app.gms.R;
import com.app.gms.activities.ChatActivity;
import com.app.gms.activities.MemberAttendance;
import com.app.gms.activities.PaymentActivity;
import com.app.gms.activities.ViewTrainers;


public class HomeMember extends Fragment {

    CardView attendanceCard,trainers,message,payment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_member, container, false);
        bindViews(view);
        attendanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MemberAttendance.class);
                startActivity(intent);
            }
        });
        trainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ViewTrainers.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void bindViews( View view) {
        attendanceCard=view.findViewById(R.id.attendance);
        trainers=view.findViewById(R.id.trainer);
        message=view.findViewById(R.id.message_card);
    }
}
