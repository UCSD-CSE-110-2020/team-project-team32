package com.example.cse110_project.user_routes;

import androidx.annotation.NonNull;

import com.example.cse110_project.util.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Route {
    public final static String EASY_D = "Easy";
    public final static String EVEN_S = "Even surface";
    public final static String NO_DATA = "";
    public final static String HILLY = "Hilly";
    public final static String FLAT = "Flat";
    public final static String LOOP = "Loop";
    public final static String OUT_BACK = "Out-and-back";
    public final static String STREETS = "Streets";
    public final static String TRAIL = "Trail";
    public final static String UNEVEN_S = "Uneven surface";
    public final static String MID_D = "Moderate";
    public final static String HARD_D = "Difficult";
    public final static String FAV = "FAV";

    private int id;
    private String docID;
    private String name;

    private int steps;
    private LocalDateTime startDate;
    private LocalTime duration;

    private String startingPoint;
    private String flatVsHilly;
    private String loopVsOutBack;
    private String streetsVsTrail;
    private String evenVsUneven;
    private String difficulty;
    private String notes;
    private boolean fav;

    // Used for logging
    @Override @NonNull
    public String toString() {
        return "" + id + ": (" + name + ", " + steps + ", " + duration + ", " + startDate + ")";
    }

    public int getID() { return id; }
    public void setID(int id) { this.id = id; }

    public String getDocID() { return docID; }
    public void setDocID(String docID) { this.docID = docID; }

    public String getName() { return name; }
    public void setName(String n) { name = n; }

    // Walk data

    public boolean hasWalkData() { return duration != null && startDate != null; }
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

    public String getLoopVsOutBack() { return loopVsOutBack; }
    public void setLoopVsOutBack(String str) { loopVsOutBack = str; }

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
