package com.example.cse110_project.data_access;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cse110_project.user_routes.Route;

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

    public static String retrieveFlatVsHilly(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.FLAT_VS_HILLY_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveLoopVsOAB(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.LOOP_VS_OUTBACK_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveStreetVsTrail(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.STREETS_VS_TRAIL_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveEvenVsUneven(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.EVEN_VS_UNEVEN_SURFACE_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveDifficulty(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_DIFFICULTY_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    // route notes
    public static String retrieveNotes(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_NOTES_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static void saveRouteData(Context c, Route route) {
        saveRouteName(c, route.getID(), route.getName());

        if (route.getSteps() >= 0) {
            saveRouteSteps(c, route.getID(), route.getSteps());
        }

        if (route.getDuration() != null) {
            saveRouteTime(c, route.getID(), route.getDuration().toString());
        }

        if (route.getStartDate() != null) {
            saveRouteDate(c, route.getID(), route.getStartDate().toString());
        }

        if (route.getFlatVsHilly() != Route.NO_DATA){
            saveFlatVsHilly(c, route.getID(), route.getFlatVsHilly());
        }

        if (route.getLoopVsOAB() != Route.NO_DATA) {
            saveLoopVsOAB(c, route.getID(), route.getLoopVsOAB());
        }

        if (route.getStreetsVsTrail() != Route.NO_DATA){
            saveStreetsVsTrail(c, route.getID(), route.getStreetsVsTrail());
        }

        if (route.getEvenVsUneven() != Route.NO_DATA) {
            saveEvenVsUneven(c, route.getID(), route.getEvenVsUneven());
        }

        if (route.getDifficulty() != Route.NO_DATA) {
            saveDifficulty(c, route.getID(), route.getDifficulty());
        }

        if (route.getNotes() != Route.NO_DATA) {
            saveNotes(c, route.getID(), route.getNotes());
        }
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

    public static void saveStartingPoint(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.STARTING_POINT_KEY, routeID), data);
        editor.apply();
    }

    public static void saveFlatVsHilly(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.FLAT_VS_HILLY_KEY, routeID), data);
        editor.apply();
    }

    public static void saveLoopVsOAB(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.LOOP_VS_OUTBACK_KEY, routeID), data);
        editor.apply();
    }

    public static void saveStreetsVsTrail(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.STREETS_VS_TRAIL_KEY, routeID), data);
        editor.apply();
    }

    public static void saveEvenVsUneven(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.EVEN_VS_UNEVEN_SURFACE_KEY, routeID), data);
        editor.apply();
    }

    public static void saveDifficulty(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_DIFFICULTY_KEY, routeID), data);
        editor.apply();
    }

    public static void saveNotes(Context c, int routeID, String data) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_NOTES_KEY, routeID), data);
        editor.apply();
    }

    private static SharedPreferences retrievePrefs(Context c) {
        return c.getSharedPreferences(DataConstants.ROUTES_DATA_FILE, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor retrieveEditor(Context c) {
        return retrievePrefs(c).edit();
    }
}