package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.gms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemberPassChange extends AppCompatActivity {
    Button change_pass;
    EditText pass1,pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_pass_change);
        bindViews();
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordChange();
            }
        });
    }

    private void passwordChange() {
        if (pass1.getText().toString().isEmpty()||pass2.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Fill Both Fields",Toast.LENGTH_SHORT).show();
        }
        else
        {
            final String oldpass=pass1.getText().toString();
            final String newpass=pass1.getText().toString();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference();
            myref.child("Members").child("Password").setValue(newpass);
            Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_SHORT).show();
        }
    }

    private void bindViews() {
        change_pass=findViewById(R.id.submitbtn);
        pass1=findViewById(R.id.password1);
        pass2=findViewById(R.id.password2);
    }
}
