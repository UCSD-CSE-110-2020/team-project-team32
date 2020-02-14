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
    // route notes
    public static String retrieveRouteNotes(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_NOTES_KEY, routeID),
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

    public static String retrieveFlatVSHilly(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.FLAT_VS_HILLY_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveLoopVsOutBack(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.LOOP_VS_OUTBACK_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveStreetVSTrail(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.STREETS_VS_TRAIL_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveEvenVSUneven(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.EVEN_VS_UNEVEN_SURFACE_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveRouteDifficulty(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_DIFFICULTY_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static void saveRouteData(Context c, Route route) {
        saveRouteName(c, route.getID(), route.getName());
        saveRouteNotes(c, route.getID(), route.getRouteNotes());

        if (route.getSteps() >= 0) {
            saveRouteSteps(c, route.getID(), route.getSteps());
        }

        if (route.getDuration() != null) {
            saveRouteTime(c, route.getID(), route.getDuration().toString());
        }

        if (route.getStartDate() != null) {
            saveRouteDate(c, route.getID(), route.getStartDate().toString());
        }

        if(route.getFlatVSHilly() != null){
            saveFlatVSHilly(c, route.getID(), route.getFlatVSHilly());
        }

        if(route.getLoopVSOutBack() != null) {
            saveLoopVsOutBack(c, route.getID(), route.getLoopVSOutBack());
        }

        if(route.getStreetsVSTrail() != null){
            saveStreetVSTrail(c, route.getID(), route.getStreetsVSTrail());
        }

        if(route.getEvenVsUnevenSurface() != null) {
            saveEvenVSUneven(c, route.getID(), route.getEvenVsUnevenSurface());
        }

        if(route.getRouteDifficulty() != null) {
            saveRouteDifficulty(c, route.getID(), route.getRouteDifficulty());
        }

    }

    public static void saveRouteName(Context c, int routeID, String routeName) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_NAME_KEY, routeID), routeName);
        editor.apply();
    }
    // route notes
    public static void saveRouteNotes(Context c, int routeID, String routeNotes) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_NOTES_KEY, routeID), routeNotes);
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

    public static void saveFlatVSHilly(Context c, int routeID, String FlatVsHilly) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.FLAT_VS_HILLY_KEY, routeID), FlatVsHilly);
        editor.apply();
    }

    public static void saveLoopVsOutBack(Context c, int routeID, String LoopVsoutBack) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.LOOP_VS_OUTBACK_KEY, routeID), LoopVsoutBack);
        editor.apply();
    }

    public static void saveStreetVSTrail(Context c, int routeID, String StreetVsTrail) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.STREETS_VS_TRAIL_KEY, routeID), StreetVsTrail);
        editor.apply();
    }

    public static void saveEvenVSUneven(Context c, int routeID, String EvenvsUneven) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.EVEN_VS_UNEVEN_SURFACE_KEY, routeID), EvenvsUneven);
        editor.apply();
    }

    public static void saveRouteDifficulty(Context c, int routeID, String routeDifficulty) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_DIFFICULTY_KEY, routeID), routeDifficulty);
        editor.apply();
    }

    private static SharedPreferences retrievePrefs(Context c) {
        return c.getSharedPreferences(DataConstants.ROUTES_DATA_FILE, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor retrieveEditor(Context c) {
        return retrievePrefs(c).edit();
    }
}