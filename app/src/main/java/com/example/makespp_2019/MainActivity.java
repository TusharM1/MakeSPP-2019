package com.example.makespp_2019;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import java.util.TreeMap;

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
			String morseCode = MorseCodeTranslator.plainTextToMorse(input.getText().toString());
			for (char letter : morseCode.toCharArray())
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
