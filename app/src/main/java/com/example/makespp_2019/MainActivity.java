package com.example.makespp_2019;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity{

	ImageButton toSend, toReceive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toSend = findViewById(R.id.sendButton);
		toReceive = findViewById(R.id.receiveButton);

		toSend.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, SendMessage.class);
			startActivity(intent);
		});

		toReceive.setOnClickListener(v -> {
			Intent intent = new Intent(MainActivity.this, ReceiveMessage.class);
			startActivity(intent);
		});
	}
}
