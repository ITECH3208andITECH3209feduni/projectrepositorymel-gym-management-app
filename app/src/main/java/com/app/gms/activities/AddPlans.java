package com.app.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.gms.R;
import com.app.gms.models.Plans;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlans extends AppCompatActivity {

    Button btnAdd;
    TextView tv_title,description,tv_price,tv_trainer;
    DatabaseReference ref;
    String title,desc,price,trainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plans);
        bindViews();
        ref= FirebaseDatabase.getInstance().getReference().child("Plans");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlan();
            }
        });
    }

    private void addPlan() {
        title=tv_title.getText().toString();
        desc=description.getText().toString();
        price=tv_price.getText().toString();
        trainer=tv_trainer.getText().toString();
        if (title.isEmpty())
        {
            tv_title.setError("Enter Title");
        }
        else if (desc.isEmpty())
        {
            description.setError("Enter Description");
        }
        else if (price.isEmpty())
        {
            description.setError("Enter Price");
        }
        else if (price.isEmpty())
        {
            description.setError("Enter Trainer");
        }
        else{
            Plans plans=new Plans();
            plans.setTitle(title);
            plans.setDescription(desc);
            plans.setPrice(price);
            plans.setTrainer(trainer);
            ref.push().setValue(plans);
            Toast.makeText(getApplicationContext(),"Plan Added",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(AddPlans.this,AdminActivity.class);
            startActivity(intent);
        }
    }


    private void bindViews() {
        btnAdd=findViewById(R.id.submitbtn);
        tv_title=findViewById(R.id.title1);
        description=findViewById(R.id.desc);
        tv_price=findViewById(R.id.price);
        tv_trainer=findViewById(R.id.trainer);
    }

}
