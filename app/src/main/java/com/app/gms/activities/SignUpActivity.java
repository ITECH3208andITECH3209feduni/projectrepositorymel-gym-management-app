package com.app.gms.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.gms.R;
import com.app.gms.models.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity {

    EditText et_name,et_age,et_email,et_contact,et_address,et_pass,r_pass;
    Spinner spinner;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ImageView imageView;
    Button btnRegister;
    public Uri imageUri;
    String userId="";
    Members members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindViews();
        loadDropDown();
        databaseReference=FirebaseDatabase.getInstance().getReference("Members");
        userId=databaseReference.push().getKey();
        firebaseAuth=FirebaseAuth.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChoose();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }

            private void register() {
                String name=et_name.getText().toString();
                String age=et_age.getText().toString();
                String contact=et_contact.getText().toString();
                String address=et_address.getText().toString();
                String email=et_email.getText().toString();
                String password=et_pass.getText().toString();
                String rpass=r_pass.getText().toString();
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
                else if (email.isEmpty())
                {
                    et_email.setError("Enter Email");
                }
                else if (password.isEmpty())
                {
                    et_pass.setError("Enter Password");
                }
                else if (rpass.isEmpty())
                {
                    r_pass.setError("Retype Password");
                }
                else if (!rpass.equals(password))
                {
                    Toast.makeText(getApplicationContext(),"Password should be same",Toast.LENGTH_SHORT).show();
                }
                else {
                   members=new Members(name,age,gender,contact,address,email,password);
                    databaseReference.child("Members").child(userId).setValue(members);
                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void fileChoose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private void bindViews() {
        et_name=findViewById(R.id.etName);
        et_age=findViewById(R.id.etAge);
        et_contact=findViewById(R.id.etContact);
        et_address=findViewById(R.id.etAddress);
        et_email=findViewById(R.id.etEmail);
        et_pass=findViewById(R.id.pass);
        r_pass=findViewById(R.id.r_pass);
        spinner=findViewById(R.id.drop_down);
        btnRegister=findViewById(R.id.submitbtn);
        imageView=findViewById(R.id.image_view);
    }
    private void loadDropDown() {
        String[] items=new String[]{"","Male","Female","Other"};
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        spinner.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1)
        {
            assert data != null;
            imageUri=data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }
}
