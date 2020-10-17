package com.app.gms.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.R;
import com.app.gms.adapters.ImageAdapter;
import com.app.gms.models.Images;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PictureGallery extends AppCompatActivity {
    Toolbar toolbar;
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
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Images upload=postSnapshot.getValue(Images.class);
                    mUploads.add(upload);
                }
                mAdapter=new ImageAdapter(PictureGallery.this, mUploads);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PictureGallery.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
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
