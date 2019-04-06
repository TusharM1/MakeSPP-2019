package com.example.makespp_2019;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Thread.sleep;

public class SendMessage extends AppCompatActivity {

    EditText sendInput;
    Vibrator vibrator;
    ImageButton sendButton;
    ImageButton backToMain;
    ConstraintLayout layout;
    TextView description;
    String code;

    long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        sendInput = findViewById(R.id.sendEnterText);
        sendButton = findViewById(R.id.sendSendButton);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        layout = findViewById(R.id.sendLayout);
        description = findViewById(R.id.sendDirections);

        code = "";
        currentTime = 0;

        layout.setOnClickListener(v -> {
            if (currentTime == 0)
                currentTime = System.currentTimeMillis();
            long difference = System.currentTimeMillis() - currentTime;

            if (difference < 500)
                code += " ";
            else if (difference < 1000)
                code += "  ";
            else code += "   ";
            code += "A";
            currentTime = System.currentTimeMillis();
            description.setText(code);
        });

        layout.setOnLongClickListener(v -> {
            if (currentTime == 0)
                currentTime = System.currentTimeMillis();
            long difference = System.currentTimeMillis() - currentTime;

            if (difference < 500)
                code += " ";
            else if (difference < 1000)
                code += "  ";
            else code += "   ";
            code += "B";

            currentTime = System.currentTimeMillis();
            description.setText(code);
            return true;
        });

        backToMain = findViewById(R.id.sendHomeButton);
        backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(SendMessage.this, MainActivity.class);
            startActivity(intent);
        });

        sendButton.setOnClickListener(v -> {
            String morseCode = MorseCodeTranslator.morseToPlainText(description.getText().toString() + " ");
//            String morseCode = MorseCodeTranslator.morseToPlainText("A A A A  B B B   A  ");
            description.setText(morseCode);
//            String morseCode = MorseCodeTranslator.plainTextToMorse(sendInput.getText().toString());
//            for (char letter : morseCode.toCharArray())
//                switch (letter) {
//                    case '.':
//                        vibrator.vibrate(100);
//                        break;
//                    case '-':
//                        vibrator.vibrate(300);
//                        break;
//                    case ' ':
//                        try {
//                            sleep(600);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                }
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
                "A B ", 			    // A
                "B A A A  ",		// B
                "B A B A  ",		// C
                "B A A  ",			// D
                "A  ",				// E
                "A A B A  ",		// F
                "B B A  ",			// G
                "A A A A  ",		// H
                "A A  ",			// I
                "A B B B  ",		// J
                "B A B  ",			// K
                "A B A A  ",		// L
                "B B  ",			// M
                "B A  ",			// N
                "B B B  ",			// O
                "A B B A  ",		// P
                "B B A B  ",		// Q
                "A B A  ", 			// R
                "A A A  ", 			// S
                "B  ",			    // T
                "A A B  ",			// U
                "A A A B  ",		// V
                "A B B  ",			// W
                "B A A B  ",		// X
                "B A B B  ",		// Y
                "B B A A  ",		// Z

                "A B B B B  ",		// 1
                "A A B B B  ",		// 2
                "A A A B B  ",		// 3
                "A A A A B  ",		// 4
                "A A A A A  ",		// 5
                "B A A A A  ",		// 6
                "B B A A A  ",		// 7
                "B B B A A  ",		// 8
                "B B B B A  ",		// 9
                "B B B B B  ",		// 0
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

        private static String morseToPlainText(String morseCode) {

            Log.d("TEST", morseCodeDictionary.get("h"));
            Log.d("TEST", String.valueOf("A A A A  ".matches(morseCodeDictionary.get("h"))));

            /*String[] plaintext = morseCode.split(" {2}");
            Log.d("TAG-1", Arrays.toString(plaintext));
            for(int i = 0; i < plaintext.length; i++){
                String t = plaintext[i];
                if(t.charAt(t.length()-1) != ' '){
                    t += ' ';
                }
            }
            try {
                for (String s : plaintext)
                    for (Map.Entry e : morseCodeDictionary.entrySet()) {
                        Log.d("TAG-1", plaintext.toString());
                        s = s.replace(e.getValue().toString() + "  ", e.getKey().toString());
                    }
            }catch (Exception ignored) {}
            String returnString = "";
            for (String str: plaintext) {
                returnString += str;
            }

            return returnString;*/
            String returnString = "";

            Log.d("FUCK", morseCode);

            ArrayList<String> strings = new ArrayList<>();
            String ssss = "";

            while (morseCode.contains("  ")) {
                String string = morseCode.substring(0, morseCode.indexOf("  ") + 2);
                Log.d("TAG-1", string);
                strings.add(string);
                morseCode = morseCode.substring(morseCode.indexOf("  ") + 2);
            }

            Log.d("TAG-1", strings.toString());

            try {
                for (String s : strings) {
                    if (s.charAt(0) == ' ')
                        s = s.substring(1);
                    for (Map.Entry e : morseCodeDictionary.entrySet()) {
                        Log.d("BITCH", e.toString());
                        if (s.equals(e.getValue().toString())) {
                            Log.d("TAG-2", s + "/");
                            ssss += (s.replace(e.getValue().toString(), e.getKey().toString()));
                        }
                        if (s.matches(e.toString()))
                            Log.d("TAG-3", s + "/");
                        Log.d("TAG-4", e.toString());
                    }
                }
            }catch (Exception ignored) {
                Log.d("TAG-2", ignored.getMessage());
            }

            return ssss.toString();
        }

    }
}
