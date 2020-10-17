package com.app.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.adapters.StaffAdapter;
import com.app.gms.models.Staff;
import com.app.gms.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaffActivity extends AppCompatActivity implements StaffAdapter.onItemClickListener{

    Toolbar toolbar;
    FloatingActionButton btnNew;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    StaffAdapter adapter;
    ArrayList<Staff> list;
    String name,age,gender,contact,address,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        bindViews();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Staff");
        list=new ArrayList<>();
        adapter=new StaffAdapter(StaffActivity.this,list);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Staff staff = dataSnapshot1.getValue(Staff.class);
                    name=staff.name;
                    age=staff.age;
                    gender=staff.gender;
                    contact=staff.contact;
                    address=staff.address;
                    email=staff.email;
                    password=staff.password;
                    list.add(staff);
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(StaffActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnNew.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(StaffActivity.this, AddStaff.class);
                startActivity(intent);
            }
        });
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar1);
        btnNew=findViewById(R.id.add);
        recyclerView=findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(int position) {
        list.get(position);
        Intent intent=new Intent(StaffActivity.this,StaffMemberItem.class);
        intent.putExtra("name",name);
        intent.putExtra("age",age);
        intent.putExtra("gender",gender);
        intent.putExtra("contact",contact);
        intent.putExtra("address",address);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
    }
}