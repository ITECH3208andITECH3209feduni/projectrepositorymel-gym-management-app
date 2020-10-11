package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.gms.R;
import com.app.gms.adapters.MembersAdapter;
import com.app.gms.adapters.StaffAdapter;
import com.app.gms.models.Members;
import com.app.gms.models.Staff;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowMembers extends AppCompatActivity {

    Toolbar toolbar;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MembersAdapter adapter;
    ArrayList<Members> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_members);
        bindViews();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Members").child("Members");
        list=new ArrayList<>();
        adapter=new MembersAdapter(ShowMembers.this,list);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Members members = dataSnapshot1.getValue(Members.class);
                    list.add(members);
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShowMembers.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar1);
        recyclerView=findViewById(R.id.recycler_view);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
