package com.example.cse110_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

public class UserData {
    public final static int NO_HEIGHT_FOUND = 0;

    public static int retrieveHeight(Context c) {
        SharedPreferences pref = c.getSharedPreferences("user_data", c.MODE_PRIVATE);
        return pref.getInt("height", NO_HEIGHT_FOUND);
    }

    public static void saveHeight(Context c, int height) {
        SharedPreferences pref = c.getSharedPreferences("user_data", c.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("height", height);
        editor.apply();
    }
}
