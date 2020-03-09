package com.example.cse110_project.user_routes;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cse110_project.util.DataConstants;

/*
 * Contains methods for storing & retrieving data on specific routes to/from SharedPreferences.
 */
public class RouteData {
    public static String retrieveRouteName(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_NAME_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveRouteDocID(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_DOC_ID_KEY, routeID),
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

    public static int retrieveTeamRouteSteps(Context c, String docId) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getInt(String.format(DataConstants.TEAM_ROUTE_STEPS_KEY, docId),
                DataConstants.INT_NOT_FOUND);
    }

    public static String retrieveTeamRouteTime(Context c, String docId) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.TEAM_ROUTE_TIME_KEY, docId),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveTeamRouteDate(Context c, String docId) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.TEAM_ROUTE_DATE_KEY, docId),
                DataConstants.STR_NOT_FOUND);
    }

    public static boolean retrieveTeamRouteFavorite(Context c, String docId) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getBoolean(String.format(DataConstants.TEAM_ROUTE_FAVORITE_KEY, docId),
                DataConstants.BOOL_NOT_FOUND);
    }

    public static String retrieveStartingPoint(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.STARTING_POINT_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveFlatVsHilly(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.FLAT_VS_HILLY_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static String retrieveLoopVsOutBack(Context c, int routeID) {
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

    public static String retrieveNotes(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getString(String.format(DataConstants.ROUTE_NOTES_KEY, routeID),
                DataConstants.STR_NOT_FOUND);
    }

    public static boolean retrieveFavorite(Context c, int routeID) {
        SharedPreferences pref = retrievePrefs(c);
        return pref.getBoolean(String.format(DataConstants.ROUTE_FAVORITE_KEY, routeID),
                DataConstants.BOOL_NOT_FOUND);
    }

    // Saves all data for the given route
    public static void saveRouteData(Context c, Route route) {
        saveRouteName(c, route.getID(), route.getName());
        saveRouteDocId(c, route.getID(), route.getDocID());

        // Save steps/time/date only if route has been walked
        if (route.getStartDate() != null && route.getDuration() != null) {
            saveRouteSteps(c, route.getID(), route.getSteps());
            saveRouteTime(c, route.getID(), route.getDuration().toString());
            saveRouteDate(c, route.getID(), route.getStartDate().toString());
        }

        // Features are always initialized to empty in Route constructor
        saveStartingPoint(c, route.getID(), route.getStartingPoint());
        saveFlatVsHilly(c, route.getID(), route.getFlatVsHilly());
        saveLoopVsOutBack(c, route.getID(), route.getLoopVsOutBack());
        saveStreetsVsTrail(c, route.getID(), route.getStreetsVsTrail());
        saveEvenVsUneven(c, route.getID(), route.getEvenVsUneven());
        saveDifficulty(c, route.getID(), route.getDifficulty());
        saveNotes(c, route.getID(), route.getNotes());
        saveFavorite(c, route.getID(), route.isFavorite());
    }

    public static void saveRouteName(Context c, int routeID, String routeName) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_NAME_KEY, routeID), routeName);
        editor.apply();
    }

    public static void saveRouteDocId(Context c, int routeID, String docId) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.ROUTE_DOC_ID_KEY, routeID), docId);
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

    public static void saveTeamRouteSteps(Context c, String docId, int routeSteps) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putInt(String.format(DataConstants.TEAM_ROUTE_STEPS_KEY, docId), routeSteps);
        editor.apply();
    }

    public static void saveTeamRouteTime(Context c, String docId, String routeTime) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.TEAM_ROUTE_TIME_KEY, docId), routeTime);
        editor.apply();
    }

    public static void saveTeamRouteDate(Context c, String docId, String routeDate) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putString(String.format(DataConstants.TEAM_ROUTE_DATE_KEY, docId), routeDate);
        editor.apply();
    }

    public static void saveTeamRouteFavorite(Context c, String docId, boolean fav) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putBoolean(String.format(DataConstants.TEAM_ROUTE_FAVORITE_KEY, docId), fav);
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

    public static void saveLoopVsOutBack(Context c, int routeID, String data) {
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

    public static void saveFavorite(Context c, int routeID, boolean fav) {
        SharedPreferences.Editor editor = retrieveEditor(c);
        editor.putBoolean(String.format(DataConstants.ROUTE_FAVORITE_KEY, routeID), fav);
        editor.apply();
    }

    private static SharedPreferences retrievePrefs(Context c) {
        return c.getSharedPreferences(DataConstants.ROUTES_DATA_FILE, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor retrieveEditor(Context c) {
        return retrievePrefs(c).edit();
    }
}