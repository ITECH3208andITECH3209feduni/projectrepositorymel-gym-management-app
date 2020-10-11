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

import com.app.gms.R;
import com.app.gms.activities.AddNews;
import com.app.gms.activities.StaffActivity;
import com.app.gms.adapters.NewsAdapter;
import com.app.gms.adapters.StaffAdapter;
import com.app.gms.models.News;
import com.app.gms.models.Staff;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    FloatingActionButton fab;
    DatabaseReference databaseReference;
    DatabaseReference childRef;
    RecyclerView recyclerView;
    NewsAdapter adapter;
    ArrayList<News> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        bindViews(view);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("News");
        childRef= FirebaseDatabase.getInstance().getReference().child("url");
        list=new ArrayList<>();
        adapter=new NewsAdapter(this.getContext(),list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    News news = dataSnapshot1.getValue(News.class);
                    list.add(news);
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddNews.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void bindViews(View view) {
        fab=view.findViewById(R.id.add);
        recyclerView=view.findViewById(R.id.recycler_view);
    }
}
