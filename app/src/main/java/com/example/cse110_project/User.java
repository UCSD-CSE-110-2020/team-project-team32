package com.example.cse110_project;

import java.util.*;

public class User {
    private List<Route> routes;
    private int height;
    private int steps;

    public User(int h){
        height = h;
        routes = new ArrayList<Route>();
    }

    public int getHeight() { return height;}

    public int getSteps() { return steps;}

    public double getMiles() {
        MilesCalculator milesCalculator = new MilesCalculator();
        return milesCalculator.calculateMiles(height, steps);}

    public void resetSteps() { steps = 0; }

    public void updateSteps() {
        //Interact with API
    }

    public List<Route> getRoutes(){ return routes; }

    public Route getRecentRoute(){
        Route mostRecent = null;
        for(Route r: routes){
            if(mostRecent == null || r.getStartDate().after(mostRecent.getStartDate())){
                mostRecent = r;
            }
        }
        return mostRecent;
    }

}
