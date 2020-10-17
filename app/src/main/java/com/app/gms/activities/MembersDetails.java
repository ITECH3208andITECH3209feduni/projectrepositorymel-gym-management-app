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

public class MembersDetails extends AppCompatActivity {

    TextView tvName,tvAge,tvGender,tvAddress,tvContact,tvEmail,tvPassword;
    String name;
    Button btnDelete,btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_details);
        bindViews();
        name=getIntent().getStringExtra("Name");
        final String age=getIntent().getStringExtra("Age");
        final String gender=getIntent().getStringExtra("Gender");
        final String address=getIntent().getStringExtra("Address");
        final String contact=getIntent().getStringExtra("Contact");
        final String email=getIntent().getStringExtra("Email");
        String password=getIntent().getStringExtra("Password");
        tvName.setText(name);
        tvAge.setText(age);
        tvGender.setText(gender);
        tvAddress.setText(address);
        tvContact.setText(contact);
        tvEmail.setText(email);
        tvPassword.setText(password);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Members");
        Query query = ref.child("Members").orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent intent=new Intent(MembersDetails.this,AdminActivity.class);
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
        tvName=findViewById(R.id.name);
        tvAge=findViewById(R.id.age);
        tvGender=findViewById(R.id.gender);
        tvAddress=findViewById(R.id.address);
        tvContact=findViewById(R.id.contact);
        tvEmail=findViewById(R.id.email);
        tvPassword=findViewById(R.id.password);
        btnDelete=findViewById(R.id.delete_btn);
        btnUpdate=findViewById(R.id.updatebtn);
    }
}
