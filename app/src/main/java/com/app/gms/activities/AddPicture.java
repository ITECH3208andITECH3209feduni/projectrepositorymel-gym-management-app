package com.app.gms.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.gms.R;
import com.app.gms.models.Images;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddPicture extends AppCompatActivity {

    Button btn_upload,btn_choose;
    ImageView im;
    public Uri imageUri;
    private StorageReference mStorageRef;
    private StorageTask task;
    private DatabaseReference mDatabaseRef;
    private ProgressBar uploadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        bindViews();
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Images");
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChoose();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    fileUpload();
            }
        });
    }

    private void fileUpload() {
        final StorageReference ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imageUri));
        task = ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadProgress.setProgress(0);
                            }
                        }, 500);
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Images upload = new Images(uri.toString());
                                String uploadID = mDatabaseRef.push().getKey();
                                mDatabaseRef.child(uploadID).setValue(upload);
                                Toast.makeText(AddPicture.this, "Upload successfully", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(AddPicture.this,GalleryAdmin.class);
                                startActivity(intent);
                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPicture.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        uploadProgress.setProgress((int) progress);
                    }
                });
    }

    private String getExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mm=MimeTypeMap.getSingleton();
        return mm.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileChoose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    private void bindViews() {
        btn_choose=findViewById(R.id.upload);
        btn_upload=findViewById(R.id.submitbtn);
        im=findViewById(R.id.image_view);
        uploadProgress = findViewById(R.id.uploadProgress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1)
        {
            assert data != null;
            imageUri=data.getData();
            Picasso.with(this).load(imageUri).into(im);
        }
    }
}
