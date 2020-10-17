package com.app.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.gms.R;
import com.app.gms.models.Members;
import com.app.gms.models.Staff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    Spinner spinner;
    Button btnLogin;
    TextView register;
    EditText et_email,et_password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        loadDropDown();
        mAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void updateStaffUI(FirebaseUser currentUser) {
        if (currentUser!=null) {
            Intent intent = new Intent(LoginActivity.this, StaffMain.class);
            startActivity(intent);
            finish();
        }
    }

    private void login() {
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();
        if (email.isEmpty()) {
            et_email.setError("Enter Email");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText()).matches()) {
            et_email.setError("Enter Valid Email");
            return;
        } else if (password.isEmpty()) {
            et_password.setError("Enter Password");
            return;
        } else if (spinner.getSelectedItem().toString().trim().equals("Account Type...")) {
            Toast.makeText(LoginActivity.this, "Select Account Type.",
                    Toast.LENGTH_SHORT).show();
        } else if (spinner.getSelectedItem().toString().trim().equals("Admin")) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "User not found",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    });
        } else if (spinner.getSelectedItem().toString().trim().equals("Member")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Members");
            progressBar.setVisibility(View.VISIBLE);
            databaseReference.child("Members").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            Members members = user.getValue(Members.class);

                            assert members != null;
                            if (members.password.equals(password)) {
                                Intent intent = new Intent(LoginActivity.this, MembersActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("Email",email);
                                bundle.putString("Password",password);
                                intent.putExtras(bundle);
                                progressBar.setVisibility(View.GONE);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else if (spinner.getSelectedItem().toString().trim().equals("Staff")) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateStaffUI(user);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "User not found",
                                        Toast.LENGTH_SHORT).show();
                                updateStaffUI(null);
                            }
                        }

                    });
        }

    }
    private void loadDropDown() {
        String[] items=new String[]{"Account Type...","Admin","Staff","Member"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);

    }

    private void bindViews() {
        spinner=findViewById(R.id.drop_down);
        btnLogin=findViewById(R.id.submitbtn);
        register=findViewById(R.id.register);
        et_email=findViewById(R.id.uname);
        et_password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progress_bar);
    }

}