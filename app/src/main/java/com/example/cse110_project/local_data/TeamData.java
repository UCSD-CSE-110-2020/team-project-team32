package com.example.cse110_project.local_data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cse110_project.local_data.DataConstants;
import com.example.cse110_project.local_data.RouteData;

public class TeamData {
    public static String retrieveTeamWalkDocId(Context c) {
        SharedPreferences pref = RouteData.retrievePrefs(c);
        return pref.getString(DataConstants.TEAM_WALK_DOC_ID_KEY, DataConstants.STR_NOT_FOUND);
    }

    public static int retrieveTeamWalkStatus(Context c) {
        SharedPreferences pref = RouteData.retrievePrefs(c);
        return pref.getInt(DataConstants.TEAM_WALK_STATUS_KEY, DataConstants.INT_NOT_FOUND);
    }

    public static int retrieveTeamRouteSteps(Context c, String docId) {
        SharedPreferences pref = RouteData.retrievePrefs(c);
        return pref.getInt(String.format(DataConstants.TEAM_ROUTE_STEPS_KEY, docId),
                DataConstants.INT_NOT_FOUND);
    }

    public static String retrieveTeamRouteTime(Context c, String docId) {
        SharedPreferences pref = RouteData.retrievePrefs(c);
        return pref.getString(String.format(DataConstants.TEAM_ROUTE_TIME_KEY, docId),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveTeamRouteDate(Context c, String docId) {
        SharedPreferences pref = RouteData.retrievePrefs(c);
        return pref.getString(String.format(DataConstants.TEAM_ROUTE_DATE_KEY, docId),
                DataConstants.STR_NOT_FOUND);
    }

    public static boolean retrieveTeamRouteFavorite(Context c, String docId) {
        SharedPreferences pref = RouteData.retrievePrefs(c);
        return pref.getBoolean(String.format(DataConstants.TEAM_ROUTE_FAVORITE_KEY, docId),
                DataConstants.BOOL_NOT_FOUND);
    }

    public static void saveTeamWalkDocId(Context c, String docId) {
        SharedPreferences.Editor editor = RouteData.retrieveEditor(c);
        editor.putString(DataConstants.TEAM_WALK_DOC_ID_KEY, docId);
        editor.apply();
    }

    public static void saveTeamWalkStatus(Context c, int status) {
        SharedPreferences.Editor editor = RouteData.retrieveEditor(c);
        editor.putInt(DataConstants.TEAM_WALK_STATUS_KEY, status);
        editor.apply();
    }

    public static void saveTeamRouteSteps(Context c, String docId, int routeSteps) {
        SharedPreferences.Editor editor = RouteData.retrieveEditor(c);
        editor.putInt(String.format(DataConstants.TEAM_ROUTE_STEPS_KEY, docId), routeSteps);
        editor.apply();
    }

    public static void saveTeamRouteTime(Context c, String docId, String routeTime) {
        SharedPreferences.Editor editor = RouteData.retrieveEditor(c);
        editor.putString(String.format(DataConstants.TEAM_ROUTE_TIME_KEY, docId), routeTime);
        editor.apply();
    }

    public static void saveTeamRouteDate(Context c, String docId, String routeDate) {
        SharedPreferences.Editor editor = RouteData.retrieveEditor(c);
        editor.putString(String.format(DataConstants.TEAM_ROUTE_DATE_KEY, docId), routeDate);
        editor.apply();
    }

    public static void saveTeamRouteFavorite(Context c, String docId, boolean fav) {
        SharedPreferences.Editor editor = RouteData.retrieveEditor(c);
        editor.putBoolean(String.format(DataConstants.TEAM_ROUTE_FAVORITE_KEY, docId), fav);
        editor.apply();
    }
}
