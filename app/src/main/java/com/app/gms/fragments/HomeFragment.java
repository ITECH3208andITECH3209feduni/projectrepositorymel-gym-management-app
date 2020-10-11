package com.app.gms.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gms.activities.MembersActivity;
import com.app.gms.activities.ShowMembers;
import com.app.gms.activities.StaffActivity;
import com.app.gms.activities.TrainerActivity;
import com.app.gms.R;

public class HomeFragment extends Fragment {

    CardView cv_trainer,cv_staff,cv_member;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bindViews(view);
        cv_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TrainerActivity.class);
                startActivity(intent);
            }
        });
        cv_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), StaffActivity.class);
                startActivity(intent);
            }
        });
        cv_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ShowMembers.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void bindViews(View v) {
        cv_trainer=v.findViewById(R.id.trainer);
        cv_staff=v.findViewById(R.id.staff);
        cv_member=v.findViewById(R.id.members);
    }
}
