
package com.example.cse110_project.user_routes;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cse110_project.util.DataConstants;

public class UserData {
    public static String retrieveEmail(Context c) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, Context.MODE_PRIVATE);
        return pref.getString(DataConstants.EMAIL_KEY, DataConstants.NO_EMAIL_FOUND);
    }

    public static int retrieveHeight(Context c) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, Context.MODE_PRIVATE);
        return pref.getInt(DataConstants.HEIGHT_KEY, DataConstants.NO_HEIGHT_FOUND);
    }

    public static String retrieveRouteList(Context c) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, Context.MODE_PRIVATE);
        return pref.getString(DataConstants.ROUTES_LIST_KEY, DataConstants.NO_ROUTES_FOUND);
    }

    public static void saveEmail(Context c, String email) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(DataConstants.EMAIL_KEY, email);
        editor.apply();
    }

    public static void saveHeight(Context c, int height) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(DataConstants.HEIGHT_KEY, height);
        editor.apply();
    }

    public static void saveRoute(Context c, UserRoute route) {
        String routeList = retrieveRouteList(c);
        String routeID = Integer.toString(route.getID());

        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (routeList.equals(DataConstants.NO_ROUTES_FOUND)) {
            editor.putString(DataConstants.ROUTES_LIST_KEY, routeID);
        } else {
            editor.putString(DataConstants.ROUTES_LIST_KEY,
                    routeList + DataConstants.LIST_SPLIT + routeID);
        }

        editor.apply();
    }
}