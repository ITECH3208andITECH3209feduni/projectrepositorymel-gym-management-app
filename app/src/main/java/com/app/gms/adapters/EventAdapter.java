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
import com.app.gms.activities.DeleteEvent;
import com.app.gms.models.Events;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Events> events;
    TextView tv_event,tv_venue,tv_timing,tv_details;

    public EventAdapter(Context context, ArrayList<Events> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.event_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        tv_event.setText(events.get(position).getEvent());
        tv_venue.setText(events.get(position).getVenue());
        tv_timing.setText(events.get(position).getTimings());
        tv_details.setText(events.get(position).getDetails());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DeleteEvent.class);
                intent.putExtra("Event",events.get(position).getEvent());
                intent.putExtra("Venue",events.get(position).getVenue());
                intent.putExtra("Timing",events.get(position).getTimings());
                intent.putExtra("Detail",events.get(position).getDetails());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_event=itemView.findViewById(R.id.et_title);
            tv_venue=itemView.findViewById(R.id.et_venue);
            tv_timing=itemView.findViewById(R.id.et_timing);
            tv_details=itemView.findViewById(R.id.et_description);
        }
    }
}
