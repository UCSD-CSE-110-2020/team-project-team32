
package com.example.cse110_project.data_access;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cse110_project.user_routes.Route;

public class UserData {

    public static int retrieveHeight(Context c) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, c.MODE_PRIVATE);
        return pref.getInt(DataConstants.HEIGHT_KEY, DataConstants.NO_HEIGHT_FOUND);
    }

    public static String retrieveRouteList(Context c) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, c.MODE_PRIVATE);
        return pref.getString(DataConstants.ROUTES_LIST_KEY, DataConstants.NO_ROUTES_FOUND);
    }

    public static void saveHeight(Context c, int height) {
        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, c.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(DataConstants.HEIGHT_KEY, height);
        editor.apply();
    }

    public static void saveRoute(Context c, Route route) {
        String routeList = retrieveRouteList(c);
        String routeID = Integer.toString(route.getID());

        SharedPreferences pref
                = c.getSharedPreferences(DataConstants.USER_DATA_FILE, c.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if (routeList == DataConstants.NO_ROUTES_FOUND) {
            editor.putString(DataConstants.ROUTES_LIST_KEY, routeID);
        } else {
            editor.putString(DataConstants.ROUTES_LIST_KEY,
                    routeList + DataConstants.LIST_SPLIT + routeID);
        }

        editor.apply();
    }
}