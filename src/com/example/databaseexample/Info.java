package com.example.databaseexample;

import android.content.Context;
import android.widget.Toast;

public class Info {
	public static void message(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
