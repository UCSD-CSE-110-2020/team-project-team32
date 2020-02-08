package com.example.cse110_project;

import android.content.Context;

import java.util.*;

public class User {
    private static RouteList routes = null;
    private static int height = 0;
    private static int steps = 0;

    public static int getHeight() { return height;}

    public static void setHeight(int h) { height = h; }

    public static int getSteps() { return steps;}

    public static void setSteps(int s) { steps = s; }

    public static double getMiles() {
        return MilesCalculator.calculateMiles(height, steps);
    }

    public static void resetSteps() { steps = 0; }

    public static RouteList getRoutes(Context c){
        if (routes == null) {
            routes = new RouteList(c);
        }
        return routes;
    }
}