package com.app.gms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gms.activities.StaffDetails;
import com.app.gms.activities.TrainerDetail;
import com.app.gms.models.Staff;
import com.app.gms.R;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Staff> staff;
    TextView tv_name,tv_email,tv_contact,tv_address;

    public StaffAdapter(Context context, ArrayList<Staff> staff) {
        this.context = context;
        this.staff = staff;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.staff_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        tv_name.setText(staff.get(position).getName());
        tv_email.setText(staff.get(position).getEmail());
        tv_contact.setText(staff.get(position).getContact());
        tv_address.setText(staff.get(position).getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, StaffDetails.class);
                intent.putExtra("Name",staff.get(position).getName());
                intent.putExtra("Age",staff.get(position).getAge());
                intent.putExtra("Gender",staff.get(position).getGender());
                intent.putExtra("Address",staff.get(position).getAddress());
                intent.putExtra("Contact",staff.get(position).getContact());
                intent.putExtra("Email",staff.get(position).getEmail());
                intent.putExtra("Password",staff.get(position).getPassword());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staff.size();
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
    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
