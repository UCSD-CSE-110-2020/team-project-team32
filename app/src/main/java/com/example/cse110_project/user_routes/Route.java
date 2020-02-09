package com.example.cse110_project.user_routes;

import com.example.cse110_project.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Route {
    private int id;
    private String name;
    private int steps;
    private LocalDateTime startDate;
    private LocalTime duration;

    public Route(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Route(int id, String name, int steps, LocalTime dur, LocalDateTime start) {
        this.id = id;
        this.name = name;
        this.steps = steps;
        startDate = start;
        duration = dur;
    }

    public int getID() { return id; }

    public String getName() { return name; }

    public void setName(String n) { name = n; }

    public int getSteps() { return steps; }

    public void setSteps(int s) { steps = s; }

    public double getMiles(int height){
        return MilesCalculator.calculateMiles(height, steps);
    }

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(LocalDateTime date) { startDate = date; }

    public LocalTime getDuration() { return duration; }

    public void setDuration(LocalTime time) { duration = time; }
}