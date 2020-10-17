package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AdminPassChange extends AppCompatActivity {
    Button change_pass;
    EditText pass1,pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pass_change);
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
            final String newPass=pass2.getText().toString();
            final FirebaseUser user;
            user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            final String email = user.getEmail();
            assert email != null;
            AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Password Updated",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void bindViews() {
        change_pass=findViewById(R.id.submitbtn);
        pass1=findViewById(R.id.password1);
        pass2=findViewById(R.id.password2);
    }
}
