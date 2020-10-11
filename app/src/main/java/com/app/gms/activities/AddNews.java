package com.app.gms.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gms.R;
import com.app.gms.fragments.NewsFragment;
import com.app.gms.models.News;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;

public class AddNews extends AppCompatActivity {

    Button btnAdd;
    TextView url,description;
    DatabaseReference ref;
    String imageUrl,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        bindViews();
        ref= FirebaseDatabase.getInstance().getReference().child("News");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNews();
            }
        });
    }

    private void addNews() {
        imageUrl=url.getText().toString();
        desc=description.getText().toString();
        if (imageUrl.isEmpty())
        {
            url.setError("Enter Image Url");
        }
        else if (desc.isEmpty())
        {
            description.setError("Enter Description");
        }
        else{
            News news=new News();
            news.setUrl(imageUrl);
            news.setDescription(desc);
            ref.push().setValue(news);
            Intent intent=new Intent(AddNews.this,AdminActivity.class);
            startActivity(intent);
        }
    }


    private void bindViews() {
        btnAdd=findViewById(R.id.submitbtn);
        url=findViewById(R.id.imageUrl);
        description=findViewById(R.id.desc);
    }

}
