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
import com.app.gms.activities.DeletePlans;
import com.app.gms.models.Plans;

import java.util.ArrayList;

public class PlansAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Plans> plans;
    TextView tv_desc,tv_title,tv_price,tv_trainer;

    public PlansAdapter(Context context, ArrayList<Plans> plans) {
        this.context = context;
        this.plans = plans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.plan_card_admin,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        tv_desc.setText(plans.get(position).getDescription());
        tv_title.setText(plans.get(position).getTitle());
        tv_price.setText(plans.get(position).getPrice());
        tv_trainer.setText(plans.get(position).getTrainer());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DeletePlans.class);
                intent.putExtra("Title",plans.get(position).getTitle());
                intent.putExtra("Desc",plans.get(position).getDescription());
                intent.putExtra("Price",plans.get(position).getPrice());
                intent.putExtra("Trainer",plans.get(position).getTrainer());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.et_title);
            tv_desc=itemView.findViewById(R.id.description);
            tv_price=itemView.findViewById(R.id.price);
            tv_trainer=itemView.findViewById(R.id.trainer);
        }
    }
}
