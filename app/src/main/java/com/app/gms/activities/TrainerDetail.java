package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.gms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TrainerDetail extends AppCompatActivity {

    TextView tvName,tvAge,tvGender,tvAddress,tvContact,tvEmail,tvPassword;
    String name;
    Button btnDelete,btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_detail);
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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TrainerDetail.this,TrainersUpdate.class);
                intent.putExtra("Email",email);
                intent.putExtra("Name",name);
                intent.putExtra("Age",age);
                intent.putExtra("Gender",gender);
                intent.putExtra("Contact",contact);
                intent.putExtra("Address",address);
                startActivity(intent);
            }
        });
    }

    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Trainers").orderByChild("name").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent intent=new Intent(TrainerDetail.this,AdminActivity.class);
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
        btnDelete=findViewById(R.id.deletebtn);
        btnUpdate=findViewById(R.id.updatebtn);
    }
}
