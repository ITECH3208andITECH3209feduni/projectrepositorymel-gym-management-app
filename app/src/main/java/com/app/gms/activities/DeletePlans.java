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

public class DeletePlans extends AppCompatActivity {

    Button deleteBtn,updateButton;
    TextView tv_title,description,tv_price,tv_trainer;
    String title,desc,price,trainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plans);
        bindViews();
        title=getIntent().getStringExtra("Title");
        desc=getIntent().getStringExtra("Desc");
        price=getIntent().getStringExtra("Price");
        trainer=getIntent().getStringExtra("Trainer");
        tv_title.setText(title);
        description.setText(desc);
        tv_price.setText(price);
        tv_trainer.setText(trainer);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference();
                delete();
                myref.child("Plans").child(title).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("title").setValue(tv_title.getText().toString());
                        dataSnapshot.getRef().child("description").setValue(description.getText().toString());
                        dataSnapshot.getRef().child("price").setValue(tv_price.getText().toString());
                        dataSnapshot.getRef().child("trainer").setValue(tv_trainer.getText().toString());
                        Intent intent=new Intent(DeletePlans.this,AdminActivity.class);
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

    private void bindViews() {
        deleteBtn=findViewById(R.id.delete_btn);
        updateButton=findViewById(R.id.updatebtn);
        tv_title=findViewById(R.id.title1);
        description=findViewById(R.id.desc);
        tv_price=findViewById(R.id.price);
        tv_trainer=findViewById(R.id.trainer);
    }
    void delete()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("Plans").orderByChild("title").equalTo(title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent intent=new Intent(DeletePlans.this,AdminActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });
    }
}
