package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.gms.R;
import com.app.gms.adapters.NotificationAdapter;
import com.app.gms.adapters.PlansAdapter;
import com.app.gms.models.Notifications;
import com.app.gms.models.Plans;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberNotification extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button read;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    ArrayList<Notifications> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_notification);
        bindViews();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Notifications");
        list=new ArrayList<>();
        adapter=new NotificationAdapter(MemberNotification.this,list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notifications plans = dataSnapshot1.getValue(Notifications.class);
                    assert plans != null;
                    list.add(plans);
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void bindViews() {
        recyclerView=findViewById(R.id.recycler_view);
        read=findViewById(R.id.submitbtn);
    }
}
