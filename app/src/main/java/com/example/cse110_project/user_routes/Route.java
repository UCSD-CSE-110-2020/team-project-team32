package com.example.cse110_project.user_routes;

import com.example.cse110_project.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Route {
    private int id;
    private String name;
    private String FlatVSHilly;
    private String LoopVSOutBack;
    private String StreetsVSTrail;
    private String EvenVsUnevenSurface;
    private String RouteDifficulty;
    private int steps;
    private LocalDateTime startDate;
    private LocalTime duration;

    public Route(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Route(int id, String name, int steps, LocalTime dur, LocalDateTime start,String FlatVSHilly
                 ,String LoopVSOutBack, String StreetsVSTrail ,String EvenVsUnevenSurface, String RouteDifficulty) {
        this.id = id;
        this.name = name;
        this.FlatVSHilly = FlatVSHilly;
        this.LoopVSOutBack = LoopVSOutBack;
        this.StreetsVSTrail = StreetsVSTrail;
        this.EvenVsUnevenSurface = EvenVsUnevenSurface;
        this.RouteDifficulty = RouteDifficulty;
        this.steps = steps;
        startDate = start;
        duration = dur;
    }

    @Override
    public String toString() {
        return "" + id + ": (" + name + ", " + steps + ", " + duration + ", " + startDate + ")";
    }

    public int getID() { return id; }

    public void setID(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String n) { name = n; }

    public String getFlatVSHilly() { return FlatVSHilly; }

    public void setFlatVSHilly(String n) {FlatVSHilly = n ;}

    public String getLoopVSOutBack() { return LoopVSOutBack; }

    public void setLoopVSOutBack(String n) {LoopVSOutBack = n; }

    public String getStreetsVSTrail() { return StreetsVSTrail; }

    public void setStreetsVSTrail(String n) {StreetsVSTrail = n; }

    public String getEvenVsUnevenSurface() { return EvenVsUnevenSurface;}

    public void setEvenVsUnevenSurface(String n) {EvenVsUnevenSurface = n; }

    public String getRouteDifficulty() { return RouteDifficulty;}

    public void setRouteDifficulty(String n) {RouteDifficulty = n;}

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