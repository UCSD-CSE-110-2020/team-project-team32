package com.example.cse110_project.user_routes;

import android.content.Context;

import com.example.cse110_project.MilesCalculator;

public class User {
    private static RouteList routes = null;
    private static int height = 0;
    private static int fitnessSteps = 0;
    private static int stepsOffset = 0;

    public static int getHeight() { return height;}

    public static void setHeight(int h) { height = h; }

    public static int getFitnessSteps() { return fitnessSteps;}

    public static int getStepsOffset() { return stepsOffset;}


    public static void setFitnessSteps(int s) { fitnessSteps = s; }

    public static void setStepsOffset(int s) { stepsOffset = s; }

    public static int getSteps() {
        if (stepsOffset != 0) {
            return stepsOffset;
        }
        return fitnessSteps;
    }


    public static double getMiles() {
        return MilesCalculator.calculateMiles(height, getSteps());
    }


    public static RouteList getRoutes(Context c){
        if (routes == null) {
            routes = new RouteList(c);
        }
        return routes;
    }
}