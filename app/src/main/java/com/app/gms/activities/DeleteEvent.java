package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteEvent extends AppCompatActivity {

    Button deleteBtn,updateButton;
    String event,venue,timing,details;
    TextView tv_event,tv_venue,tv_timing,tv_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);
        bindViews();
        event=getIntent().getStringExtra("Event");
        venue=getIntent().getStringExtra("Venue");
        timing=getIntent().getStringExtra("Timing");
        details=getIntent().getStringExtra("Detail");
        tv_event.setText(event);
        tv_venue.setText(venue);
        tv_timing.setText(timing);
        tv_details.setText(details);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference();
                delete();
                myref.child("Events").child(event).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("event").setValue(tv_event.getText().toString());
                        dataSnapshot.getRef().child("venue").setValue(tv_venue.getText().toString());
                        dataSnapshot.getRef().child("timings").setValue(tv_timing.getText().toString());
                        dataSnapshot.getRef().child("details").setValue(tv_details.getText().toString());
                        Intent intent=new Intent(DeleteEvent.this,AdminActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());
                    }
                });
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindViews() {
        deleteBtn=findViewById(R.id.delete_btn);
        updateButton=findViewById(R.id.updatebtn);
        tv_event=findViewById(R.id.event);
        tv_venue=findViewById(R.id.venue);
        tv_timing=findViewById(R.id.timing);
        tv_details=findViewById(R.id.details);
    }
    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Events").orderByChild("event").equalTo(event);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent intent=new Intent(DeleteEvent.this,AdminActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }
}
