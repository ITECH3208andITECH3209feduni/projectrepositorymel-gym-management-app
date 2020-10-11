package com.app.gms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.R;
import com.app.gms.activities.MembersDetails;
import com.app.gms.activities.TrainerDetail;
import com.app.gms.models.Members;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Members> members;
    TextView tv_name,tv_email,tv_contact,tv_address;

    public MembersAdapter(Context context, ArrayList<Members> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.members_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        tv_name.setText(members.get(position).getName());
        tv_email.setText(members.get(position).getEmail());
        tv_contact.setText(members.get(position).getContact());
        tv_address.setText(members.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MembersDetails.class);
                intent.putExtra("Name",members.get(position).getName());
                intent.putExtra("Age",members.get(position).getAge());
                intent.putExtra("Gender",members.get(position).getGender());
                intent.putExtra("Address",members.get(position).getAddress());
                intent.putExtra("Contact",members.get(position).getContact());
                intent.putExtra("Email",members.get(position).getEmail());
                intent.putExtra("Password",members.get(position).getPassword());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
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
