package com.example.cse110_project.user_routes;

import androidx.annotation.NonNull;

import com.example.cse110_project.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Route {
    public final static String NO_DATA = "";
    public final static String HILLY = "Hilly";
    public final static String FLAT = "Flat";
    public final static String LOOP = "Loop";
    public final static String OAB = "Out-and-back";
    public final static String STREETS = "Streets";
    public final static String TRAIL = "Trail";
    public final static String EVEN_S = "Even surface";
    public final static String UNEVEN_S = "Uneven surface";
    public final static String EASY_D = "Easy";
    public final static String MID_D = "Medium";
    public final static String HARD_D = "Difficult";

    private int id;
    private String name;
    private int steps;
    private LocalDateTime startDate;
    private LocalTime duration;

    private String startingPoint;
    private String flatVsHilly;
    private String loopVsOAB;
    private String streetsVsTrail;
    private String evenVsUneven;
    private String difficulty;
    private String notes;
    private boolean fav;


    public Route(int id, String name){
        this.id = id;
        this.name = name;

        startingPoint = NO_DATA;
        flatVsHilly = NO_DATA;
        loopVsOAB = NO_DATA;
        streetsVsTrail = NO_DATA;
        evenVsUneven = NO_DATA;
        difficulty = NO_DATA;
        notes = NO_DATA;
    }

    public Route(int id, String name, int steps, LocalTime dur, LocalDateTime start) {
        this(id, name);
        this.steps = steps;
        startDate = start;
        duration = dur;
    }

    @Override
    @NonNull
    public String toString() {
        return "" + id + ": (" + name + ", " + steps + ", " + duration + ", " + startDate + ")";
    }

    public int getID() { return id; }

    protected void setID(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String n) { name = n; }

    // Walk data

    public int getSteps() { return steps; }

    public void setSteps(int s) { steps = s; }

    public double getMiles(int height){
        return MilesCalculator.calculateMiles(height, steps);
    }

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(LocalDateTime date) { startDate = date; }

    public LocalTime getDuration() { return duration; }

    public void setDuration(LocalTime time) { duration = time; }

    // Optional features

    public String getStartingPoint() { return startingPoint; }

    public void setStartingPoint(String str) { startingPoint = str; }

    public String getFlatVsHilly() { return flatVsHilly; }

    public void setFlatVsHilly(String str) { flatVsHilly = str; }

    public String getLoopVsOAB() { return loopVsOAB; }

    public void setLoopVsOAB(String str) { loopVsOAB = str; }

    public String getStreetsVsTrail() { return streetsVsTrail; }

    public void setStreetsVsTrail(String str) { streetsVsTrail = str; }

    public String getEvenVsUneven() { return evenVsUneven; }

    public void setEvenVsUneven(String str) { evenVsUneven = str; }

    public String getDifficulty() { return difficulty; }

    public void setDifficulty(String str) { difficulty = str; }

    public String getNotes() { return notes; }

    public void setNotes(String str) { notes = str; }

    public void setFavorite(boolean favorite) { fav = favorite; }

    public boolean isFavorite() { return fav; }
}