package com.app.gms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.gms.R;
import com.app.gms.activities.AddTiming;
import com.app.gms.models.Timings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TimingFragment extends Fragment {

    TextView tv_saturday, tv_sunday, tv_monday, tv_tuesday, tv_wednesday, tv_thursday, tv_friday;

   FloatingActionButton fab;
   DatabaseReference ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timing, container, false);
        ref= FirebaseDatabase.getInstance().getReference().child("Timings");
        bindViews(view);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Timings timings=dataSnapshot1.getValue(Timings.class);
                    String saturday,sunday,monday,tuesday,wednesday,thursday,friday;
                    if (timings!=null)
                    {
                        saturday=timings.getSaturday();
                        sunday=timings.getSunday();
                        monday=timings.getMonday();
                        tuesday=timings.getTuesday();
                        wednesday=timings.getWednesday();
                        thursday=timings.getThursday();
                        friday=timings.getFriday();
                        tv_saturday.setText(saturday);
                        tv_sunday.setText(sunday);
                        tv_monday.setText(monday);
                        tv_tuesday.setText(tuesday);
                        tv_wednesday.setText(wednesday);
                        tv_thursday.setText(thursday);
                        tv_friday.setText(friday);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddTiming.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void bindViews(View v) {
        fab=v.findViewById(R.id.add);
        tv_saturday=v.findViewById(R.id.sat);
        tv_sunday =v.findViewById(R.id.sun);
        tv_monday =v.findViewById(R.id.mon);
        tv_tuesday =v.findViewById(R.id.tue);
        tv_wednesday =v.findViewById(R.id.wed);
        tv_thursday =v.findViewById(R.id.thu);
        tv_friday =v.findViewById(R.id.fri);
    }


}
