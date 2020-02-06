package com.example.cse110_project;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalTime;

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

    public static Route retrieveRecentRoute() {
        // Dummy implementation
        Route route = new Route("Dummy");
        route.setSteps(970);
        route.setDuration(LocalTime.of(5, 46));
        return route;
    }
}
