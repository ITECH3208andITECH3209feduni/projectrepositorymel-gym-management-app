package com.app.gms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.R;
import com.app.gms.adapters.NewsAdapter_Member;
import com.app.gms.models.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewsMember extends Fragment {

    DatabaseReference databaseReference;
    DatabaseReference childRef;
    RecyclerView recyclerView;
    NewsAdapter_Member adapter;
    ArrayList<News> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_member, container, false);
        bindViews(view);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("News");
        childRef= FirebaseDatabase.getInstance().getReference().child("url");
        list=new ArrayList<>();
        adapter=new NewsAdapter_Member(this.getContext(),list);
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

        return view;
    }

    private void bindViews(View view) {
        recyclerView=view.findViewById(R.id.recycler_view);
    }
}
