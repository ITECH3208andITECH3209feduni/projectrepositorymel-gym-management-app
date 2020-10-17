package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.gms.R;
import com.app.gms.models.News;
import com.app.gms.models.Notifications;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNotification extends AppCompatActivity {

    Button send;
    EditText title,desc;
    String Stitle,Sdesc;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);
        bindViews();
        ref= FirebaseDatabase.getInstance().getReference().child("Notifications");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stitle=title.getText().toString();
                Sdesc=desc.getText().toString();
                if (Sdesc.isEmpty()||Stitle.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Fill both fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    Notifications notifications=new Notifications();
                    notifications.setTitle(Stitle);
                    notifications.setDescription(Sdesc);
                    ref.push().setValue(notifications);
                    Intent intent=new Intent(AdminNotification.this,AdminActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void bindViews() {
        send=findViewById(R.id.submitbtn);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
    }
}
