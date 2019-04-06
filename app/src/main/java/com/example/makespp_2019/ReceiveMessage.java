package com.example.makespp_2019;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Thread.sleep;

public class ReceiveMessage
        extends AppCompatActivity
        implements SensorListener
{
    Vibrator vibrator;
    ImageButton backToMain;
    float last_x = -1.0f;
    float last_y = -1.0f;
    float last_z = -1.0f;

    SensorManager sensorManager;
    private long lastUpdate;
    FirebaseDatabase mDatabaseRef;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        FirebaseApp.initializeApp(this);
        mDatabaseRef = FirebaseDatabase.getInstance();
        DatabaseReference myRef= mDatabaseRef.getReference("STRING");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                code = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef.setValue("");
        backToMain = findViewById(R.id.receiveHomeButton);
        backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(ReceiveMessage.this, MainActivity.class);
            startActivity(intent);

        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > 800) {

                    Log.d("sensor", code);
                    Toast.makeText(this, "Code Received!", Toast.LENGTH_SHORT).show();
                    for (char letter : code.toCharArray())
                        switch (letter) {
                            case '.':

                                vibrator.vibrate(100);
                                break;
                            case '-':
                                vibrator.vibrate(300);
                                break;
                            case ' ':
                                try {
                                    sleep(600);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
