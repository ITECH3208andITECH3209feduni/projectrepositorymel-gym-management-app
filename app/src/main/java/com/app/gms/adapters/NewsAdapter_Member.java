package com.app.gms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.R;
import com.app.gms.activities.DeleteNews;
import com.app.gms.models.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter_Member extends RecyclerView.Adapter {

    Context context;
    ArrayList<News> news;
    TextView tv_desc;
    ImageView tv_image;

    public NewsAdapter_Member(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.news_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        tv_desc.setText(news.get(position).getDescription());
        Picasso.with(context).load(news.get(position).getUrl()).into(tv_image);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_image=itemView.findViewById(R.id.image_view);
            tv_desc=itemView.findViewById(R.id.desc);
        }
    }
}
