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

public class ShowNotifications extends AppCompatActivity {
    TextView tvTitle,tvDesc;
    String title;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifications);
        bindViews();
        title=getIntent().getStringExtra("Title");
        final String description=getIntent().getStringExtra("Description");
        tvTitle.setText(title);
        tvDesc.setText(description);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Notifications").orderByChild("title").equalTo(title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent intent=new Intent(ShowNotifications.this,MemberNotification.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    private void bindViews() {
        tvTitle=findViewById(R.id.et_title);
        tvDesc=findViewById(R.id.description);
        btnDelete=findViewById(R.id.submitbtn);
    }
}
