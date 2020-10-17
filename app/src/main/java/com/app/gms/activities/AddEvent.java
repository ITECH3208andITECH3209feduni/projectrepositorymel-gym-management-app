package com.app.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.gms.R;
import com.app.gms.models.Events;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends AppCompatActivity {

    Button btnAdd;
    TextView tv_event,tv_venue,tv_timing,tv_details;
    DatabaseReference ref;
    String sEvent,sVenue,sTimings,sDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        bindViews();
        ref= FirebaseDatabase.getInstance().getReference().child("Events");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });
    }

    private void addEvent() {
        sEvent=tv_event.getText().toString();
        sVenue=tv_venue.getText().toString();
        sTimings=tv_timing.getText().toString();
        sDetails=tv_details.getText().toString();
        if (sEvent.isEmpty())
        {
            tv_event.setError("Enter Event Name");
        }
        else if (sVenue.isEmpty())
        {
            tv_venue.setError("Enter Venue");
        }
        else if (sTimings.isEmpty())
        {
            tv_timing.setError("Enter Timings");
        }
        else if (sDetails.isEmpty())
        {
            tv_details.setError("Enter Details");
        }
        else{
            Events events=new Events();
            events.setEvent(sEvent);
            events.setVenue(sVenue);
            events.setTimings(sTimings);
            events.setDetails(sDetails);
            ref.push().setValue(events);
            Intent intent=new Intent(AddEvent.this,AdminActivity.class);
            startActivity(intent);
        }
    }


    private void bindViews() {
        btnAdd=findViewById(R.id.submitbtn);
        tv_event=findViewById(R.id.event);
        tv_venue=findViewById(R.id.venue);
        tv_timing=findViewById(R.id.timing);
        tv_details=findViewById(R.id.details);
    }

}
