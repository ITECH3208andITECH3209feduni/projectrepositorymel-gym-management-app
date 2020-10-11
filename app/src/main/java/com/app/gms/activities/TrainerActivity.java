package com.app.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.adapters.TrainerAdapter;
import com.app.gms.models.Trainers;
import com.app.gms.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrainerActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton btnNew;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    TrainerAdapter adapter;
    ArrayList<Trainers> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        bindViews();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Trainers");
        list=new ArrayList<>();
        adapter=new TrainerAdapter(TrainerActivity.this,list);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Trainers trainers = dataSnapshot1.getValue(Trainers.class);
                    list.add(trainers);

                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(TrainerActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnNew.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                Intent intent = new Intent(TrainerActivity.this, AddTrainer.class);
                startActivity(intent);
            }
            });
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        btnNew=findViewById(R.id.add);
        recyclerView=findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}