package com.app.gms.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.gms.R;

public class ChatActivity extends AppCompatActivity {

    EditText allMessages,sendMessage;
    ImageButton btnSnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bindViews();
        btnSnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessages();
            }
        });
    }

    private void sendMessages() {
        String message=sendMessage.getText().toString();
        allMessages.setText(message);
    }

    private void bindViews() {
        allMessages=findViewById(R.id.all_messages);
        sendMessage=findViewById(R.id.send_message);
        btnSnd=findViewById(R.id.send);
    }
}
