package com.example.cse110_project;

import java.util.Calendar;

public class Route {
    private String name;
    private int steps;
    private Calendar startDate;
    private Calendar duration;

    public Route(String n){
        name = n;
    }

    public String getName() { return name; }

    public void setName(String n) { name = n; }

    public int getSteps() { return steps; }

    public void setSteps(int s) { steps = s; }

    public double getMiles(int height){
        MilesCalculator milesCalculator = new MilesCalculator();
        return milesCalculator.calculateMiles(height, steps);
    }

    public Calendar getStartDate() { return startDate; }

    public void setStartDate(Calendar cal) { startDate = cal; }

    public Calendar getDuration() { return duration; }

    public void setDuration(Calendar cal) { duration = cal; }
}
