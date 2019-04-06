package com.example.makespp_2019;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ReceiveMessage extends AppCompatActivity {

    ImageButton backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        backToMain = findViewById(R.id.receiveHomeButton);
        backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ReceiveMessage.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
