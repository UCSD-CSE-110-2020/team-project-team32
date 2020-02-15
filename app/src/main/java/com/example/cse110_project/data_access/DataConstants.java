package com.example.cse110_project.data_access;

public class DataConstants {
    // Keys & file names
    public final static String USER_DATA_FILE = "user_data";
    public final static String ROUTES_DATA_FILE = "routes_data";
    public final static String HEIGHT_KEY = "user_height";
    public final static String ROUTES_LIST_KEY = "user_routes";
    public final static String LIST_SPLIT = "\t";

    public final static String ROUTE_NAME_KEY = "r%d_name";
    public final static String ROUTE_STEPS_KEY = "r%d_steps";
    public final static String ROUTE_TIME_KEY = "r%d_time";
    public final static String ROUTE_DATE_KEY = "r%d_date";
public final static String ROUTE_FAVORITE_KEY = "r%d_favorite";

    // Default values
    public final static int NO_HEIGHT_FOUND = 0;
    public final static String NO_ROUTES_FOUND = "";
    public final static String STR_NOT_FOUND = "";
    public final static int INT_NOT_FOUND = 0;
    public final static boolean BOOL_NOT_FOUND = false;
    public final static String NO_RECENT_ROUTE = "N/A";

    //Route Type
    public final static String FLAT_VS_HILLY_KEY = "r%d_flatVsHilly";
    public final static String LOOP_VS_OUTBACK_KEY = "r%d_loopVsOutBack";
    public final static String STREETS_VS_TRAIL_KEY = "r%d_StreetVsTrail";
    public final static String EVEN_VS_UNEVEN_SURFACE_KEY = "r%d_EvenVsUneven";
    public final static String ROUTE_DIFFICULTY_KEY = "r%d_routeDifficulty";

}