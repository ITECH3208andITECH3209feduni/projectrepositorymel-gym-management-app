package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.gms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class StaffUpdate extends AppCompatActivity {
    Button btnUpdate;
    EditText name,age,address,contact;
    Spinner sp_gender;
    String _name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_update);
        bindViews();
        String[] items=new String[]{"Male","Female","Other"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        sp_gender.setAdapter(adapter);
        final String email=getIntent().getStringExtra("Email");
        _name=getIntent().getStringExtra("Name");
        name.setText(_name);
        final String _age=getIntent().getStringExtra("Age");
        age.setText(_age);
        final String _contact=getIntent().getStringExtra("Contact");
        contact.setText(_contact);
        final String _address=getIntent().getStringExtra("Address");
        address.setText(_address);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference();
                delete();
                myref.child("Staff").child(_name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("name").setValue(name.getText().toString());
                        dataSnapshot.getRef().child("age").setValue(age.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(email);
                        dataSnapshot.getRef().child("gender").setValue(sp_gender.getSelectedItem().toString());
                        dataSnapshot.getRef().child("contact").setValue(contact.getText().toString());
                        dataSnapshot.getRef().child("address").setValue(address.getText().toString());
                        Intent intent=new Intent(StaffUpdate.this,AdminActivity.class);
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

    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Staff").orderByChild("name").equalTo(_name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    private void bindViews() {
        btnUpdate=findViewById(R.id.updateBtn);
        sp_gender=findViewById(R.id.drop_down);
        name=findViewById(R.id.etName);
        age=findViewById(R.id.etAge);
        contact=findViewById(R.id.etContact);
        address=findViewById(R.id.etAddress);
    }
}
