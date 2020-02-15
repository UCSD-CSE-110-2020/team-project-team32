package com.example.cse110_project.data;

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

    // Features keys
    public final static String STARTING_POINT_KEY = "r%d_starting_point";
    public final static String FLAT_VS_HILLY_KEY = "r%d_flat_hilly";
    public final static String LOOP_VS_OUTBACK_KEY = "r%d_loop_oab";
    public final static String STREETS_VS_TRAIL_KEY = "r%d_streets_trail";
    public final static String EVEN_VS_UNEVEN_SURFACE_KEY = "r%d_even_uneven";
    public final static String ROUTE_DIFFICULTY_KEY = "r%d_difficulty";
    public final static String ROUTE_NOTES_KEY = "r%d_notes";

    // Default values
    public final static int NO_HEIGHT_FOUND = 0;
    public final static String NO_ROUTES_FOUND = "";
    public final static String STR_NOT_FOUND = "";
    public final static int INT_NOT_FOUND = 0;
    public final static String NO_RECENT_ROUTE = "N/A";

}