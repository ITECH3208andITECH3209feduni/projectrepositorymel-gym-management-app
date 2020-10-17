package com.app.gms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.R;
import com.app.gms.models.Trainers;

import java.util.ArrayList;

public class TrainerAdapter_Member extends RecyclerView.Adapter {

    Context context;
    ArrayList<Trainers> trainers;
    TextView tv_name,tv_email,tv_contact,tv_address;

    public TrainerAdapter_Member(Context context, ArrayList<Trainers> trainers) {
        this.context = context;
        this.trainers = trainers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.trainer_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        tv_name.setText(trainers.get(position).getName());
        tv_email.setText(trainers.get(position).getEmail());
        tv_contact.setText(trainers.get(position).getContact());
        tv_address.setText(trainers.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return trainers.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.name);
            tv_email=itemView.findViewById(R.id.email);
            tv_contact=itemView.findViewById(R.id.contact);
            tv_address=itemView.findViewById(R.id.address);
        }
    }

}
