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
import com.app.gms.models.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MemberProfile extends AppCompatActivity {

    String Email,Password;
    Spinner spinner;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText et_name,et_age,et_contact,et_address;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);
        bindViews();
        loadDropDown();
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Members");
        updateData();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        assert extras != null;
        Email=extras.getString("Email");
        Password=extras.getString("Password");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                updateData();
            }
        });
    }

    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Members").orderByChild("email").equalTo(Email);
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

    private void updateData() {
        String name=et_name.getText().toString();
        String age=et_age.getText().toString();
        String contact=et_contact.getText().toString();
        String address=et_address.getText().toString();
        String email=Email;
        String password=Password;
        String gender="Male";
        if (name.isEmpty())
        {
            et_name.setError("Enter Name");
        }
        else if (age.isEmpty())
        {
            et_age.setError("Enter Age");
        }
        else if (contact.isEmpty())
        {
            et_contact.setError("Enter Contact");
        }
        else if (address.isEmpty())
        {
            et_address.setError("Enter Address");
        }
        else {
            Members members=new Members(name,age,gender,contact,address,email,password);
            databaseReference.child("Members").child("Members").setValue(members);
            Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MemberProfile.this,MembersActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void bindViews() {
        spinner=findViewById(R.id.drop_down);
        et_name=findViewById(R.id.etName);
        et_age=findViewById(R.id.etAge);
        et_contact=findViewById(R.id.etContact);
        et_address=findViewById(R.id.etAddress);
        btnUpdate=findViewById(R.id.submitbtn);
    }

    private void loadDropDown() {
        String[] items=new String[]{"","Male","Female","Other"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);

    }
}
