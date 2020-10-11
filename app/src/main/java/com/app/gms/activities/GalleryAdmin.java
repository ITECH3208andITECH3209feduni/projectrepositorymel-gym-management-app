package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.gms.R;
import com.app.gms.adapters.ImageAdapter;
import com.app.gms.models.Images;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdmin extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton btnNew;
    RecyclerView recyclerView;
    private DatabaseReference mDatabaseRef;
    private List<Images> mUploads;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_admin);
        bindViews();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Images");
        mUploads=new ArrayList<>();
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnNew.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(GalleryAdmin.this, AddPicture.class);
                startActivity(intent);
            }
        });
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Images upload=postSnapshot.getValue(Images.class);
                    mUploads.add(upload);
                }
                mAdapter=new ImageAdapter(GalleryAdmin.this, mUploads);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GalleryAdmin.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
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
}
