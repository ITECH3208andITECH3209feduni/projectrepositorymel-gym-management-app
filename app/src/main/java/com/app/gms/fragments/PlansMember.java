package com.app.gms.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.gms.R;
import com.app.gms.activities.SubscibedPlan;
import com.app.gms.adapters.PlansAdapter;
import com.app.gms.adapters.PlansAdapter_Member;
import com.app.gms.models.Plans;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlansMember extends Fragment {

    DatabaseReference databaseReference;
    DatabaseReference childRef;
    RecyclerView recyclerView;
    PlansAdapter_Member adapter;
    ArrayList<Plans> list;
    Button btnView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans_member, container, false);
        bindViews(view);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Plans");
        list=new ArrayList<>();
        adapter=new PlansAdapter_Member(this.getContext(),list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Plans plans = dataSnapshot1.getValue(Plans.class);
                    list.add(plans);
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SubscibedPlan.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void bindViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
        btnView=view.findViewById(R.id.plans);
    }
}
