package com.example.makespp_2019;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    Button button;
    EditText input;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        input = findViewById(R.id.input);

        button = findViewById(R.id.convert);
        button.setOnClickListener(v -> {
            for (char letter : input.getText().toString().toCharArray())
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
        });

    }

}
