package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.gms.models.Staff;
import com.app.gms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStaff extends AppCompatActivity {

    EditText et_name,et_age,et_contact,et_email,et_pass,r_pass,et_address;
    Spinner sp_gender;
    Button add;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    String name,age,address,contact,email,pass,rpass,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        mAuth = FirebaseAuth.getInstance();
        bindViews();
        String[] items=new String[]{"Male","Female","Other"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        sp_gender.setAdapter(adapter);
        ref=FirebaseDatabase.getInstance().getReference().child("Staff");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStaff();
            }
        });
    }

    private void addStaff() {
        name=et_name.getText().toString();
        age=et_age.getText().toString();
        contact=et_contact.getText().toString();
        address=et_address.getText().toString();
        email=et_email.getText().toString();
        pass=et_pass.getText().toString();
        rpass=r_pass.getText().toString();
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
        else if (email.isEmpty())
        {
            et_email.setError("Enter Email");
        }
        else if (pass.isEmpty())
        {
            et_pass.setError("Enter Password");
        }
        else if (rpass.isEmpty())
        {
            et_name.setError("Retype Password");
        }
        else if (et_pass.getText().length()<6)
        {
            Toast.makeText(getApplicationContext(),"Password should be greater than 6 digits",Toast.LENGTH_LONG).show();
        }
        else if (!pass.equals(rpass))
        {
            Toast.makeText(getApplicationContext(),"Password should match",Toast.LENGTH_LONG).show();
        }

        else {
            insertIntoDatabase();
        }
    }

    private void insertIntoDatabase() {
        gender=sp_gender.getSelectedItem().toString();
        Staff staff=new Staff();
        staff.setName(name);
        staff.setAge(age);
        staff.setGender(gender);
        staff.setContact(contact);
        staff.setAddress(address);
        staff.setEmail(email);
        staff.setPassword(pass);
        ref.push().setValue(staff);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Staff Added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddStaff.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Intent intent=new Intent(AddStaff.this,StaffActivity.class);
        startActivity(intent);
        finish();
    }

    private void bindViews() {
        et_name=findViewById(R.id.etName);
        et_age=findViewById(R.id.etAge);
        sp_gender=findViewById(R.id.drop_down);
        et_contact=findViewById(R.id.etContact);
        et_email=findViewById(R.id.etEmail);
        et_address=findViewById(R.id.etAddress);
        et_pass=findViewById(R.id.password);
        r_pass=findViewById(R.id.rPassword);
        add=findViewById(R.id.submitbtn);
    }
}
