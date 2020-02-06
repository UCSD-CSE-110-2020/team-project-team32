package com.example.cse110_project;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Route {
    private String name;
    private int steps;
    private LocalDateTime startDate;
    private LocalTime duration;

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

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(LocalDateTime date) { startDate = date; }

    public LocalTime getDuration() { return duration; }

    public void setDuration(LocalTime time) { duration = time; }
}
