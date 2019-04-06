package com.example.makespp_2019;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeMap;

import static java.lang.Thread.sleep;

public class SendMessage extends AppCompatActivity {

    EditText sendInput;
    Vibrator vibrator;
    ImageButton sendButton;
    ImageButton backToMain;
    FirebaseDatabase mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        FirebaseApp.initializeApp(this);
        sendInput = findViewById(R.id.sendEnterText);
        sendButton = findViewById(R.id.sendSendButton);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mDatabaseRef = FirebaseDatabase.getInstance();
        DatabaseReference myRef= mDatabaseRef.getReference("STRING");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("HELP", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MEPLEASE", "Failed to read value.", error.toException());
            }
        });
        backToMain = findViewById(R.id.sendHomeButton);
        backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(SendMessage.this, MainActivity.class);
            startActivity(intent);
        });

        sendButton.setOnClickListener(v -> {
            String morseCode = SendMessage.MorseCodeTranslator.plainTextToMorse(sendInput.getText().toString());
            myRef.setValue(morseCode);
            Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();
            Log.wtf("HOLY", "HELP ME");

            //for (char letter : morseCode.toCharArray())
                /*switch (letter) {
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
                }*/
        });
    }
    private static class MorseCodeTranslator {

        private static TreeMap<String, String> morseCodeDictionary = new TreeMap<>();

        private static final String[] keys = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " "
        };

        private static final String[] values = {
                ". -  ", 			// A
                "- . . .  ",		// B
                "- . - .  ",		// C
                "- . .  ",			// D
                ".  ",				// E
                ". . - .  ",		// F
                "- - .  ",			// G
                ". . . .  ",		// H
                ". .  ",			// I
                ". - - -  ",		// J
                "- . -  ",			// K
                ". - . .  ",		// L
                "- -  ",			// M
                "- .  ",			// N
                "- - -  ",			// O
                ". - - .  ",		// P
                "- - . -  ",		// Q
                ". - .  ", 			// R
                ". . .  ", 			// S
                "-  ",			    // T
                ". . -  ",			// U
                ". . . -  ",		// V
                ". - -  ",			// W
                "- . . -  ",		// X
                "- . - -  ",		// Y
                "- - . .  ",		// Z

                ". - - - -  ",		// 1
                ". . - - -  ",		// 2
                ". . . - -  ",		// 3
                ". . . . -  ",		// 4
                ". . . . .  ",		// 5
                "- . . . .  ",		// 6
                "- - . . .  ",		// 7
                "- - - . .  ",		// 8
                "- - - - .  ",		// 9
                "- - - - -  ",		// 0
                "  "				// Space
        };

        static {
            for (int i = 0; i < keys.length; i++)
                morseCodeDictionary.put(keys[i], values[i]);
        }

        private static String plainTextToMorse(String plaintext) {
            String morseCode = plaintext;
            try {
                for (String s : morseCodeDictionary.keySet())
                    morseCode = morseCode.replace(s, morseCodeDictionary.get(s));
            }
            catch (Exception ignored) {}
            return morseCode;
        }

    }
}
