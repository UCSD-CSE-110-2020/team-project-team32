package com.example.cse110_project;

import android.content.Context;
import android.content.SharedPreferences;

public class RouteData {
    public static String retrieveRouteName(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static int retrieveRouteSteps(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getInt(String.format(DataConstants.ROUTE_STEPS_KEY, routeID),
                DataConstants.INT_NOT_FOUND);
    }

    public static String retrieveRouteTime(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_TIME_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveRouteDate(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_DATE_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static void saveRouteData(Context c, Route route) {
        saveRouteName(c, route.getID(), route.getName());
        saveRouteSteps(c, route.getID(), route.getSteps());
        saveRouteTime(c, route.getID(), route.getDuration().toString());
        saveRouteDate(c, route.getID(), route.getStartDate().toString());
    }

    public static void saveRouteName(Context c, int routeID, String routeName) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_NAME_KEY, routeID), routeName);
        editor.apply();
    }

    public static void saveRouteSteps(Context c, int routeID, int routeSteps) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putInt(String.format(DataConstants.ROUTE_STEPS_KEY, routeID), routeSteps);
        editor.apply();
    }

    public static void saveRouteTime(Context c, int routeID, String routeTime) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_TIME_KEY, routeID), routeTime);
        editor.apply();
    }

    public static void saveRouteDate(Context c, int routeID, String routeDate) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_DATE_KEY, routeID), routeDate);
        editor.apply();
    }

    private static SharedPreferences retrievePrefs(Context c) {
        return c.getSharedPreferences(DataConstants.ROUTES_DATA_FILE, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor retrieveEditor(Context c) {
        return retrievePrefs(c).edit();
    }
}