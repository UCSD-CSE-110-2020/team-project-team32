package com.example.cse110_project.database;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.util.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class RouteFirebaseAdapter {
    private int id;
    private String docID;
    private String name;

    private int steps;
    private String startDate;
    private String duration;

    private String startingPoint;
    private String flatVsHilly;
    private String loopVsOutBack;
    private String streetsVsTrail;
    private String evenVsUneven;
    private String difficulty;
    private String notes;
    private boolean favorite;

    public RouteFirebaseAdapter() {}

    public RouteFirebaseAdapter(Route route) {
        id = route.getID();
        docID = route.getDocID();
        name = route.getName();
        steps = route.getSteps();
        startDate = route.getStartDate() == null ? null : route.getStartDate().toString();
        duration = route.getDuration() == null ? null : route.getDuration().toString();
        startingPoint = route.getStartingPoint();
        flatVsHilly = route.getFlatVsHilly();
        loopVsOutBack = route.getLoopVsOutBack();
        streetsVsTrail = route.getStreetsVsTrail();
        evenVsUneven = route.getEvenVsUneven();
        difficulty = route.getDifficulty();
        notes = route.getNotes();
        favorite = route.isFavorite();
    }

    public Route toRoute() {
        Route route = new UserRoute(id, name);
        route.setDocID(docID);
        route.setSteps(steps);
        route.setStartDate(startDate == null ? null : LocalDateTime.parse(startDate));
        route.setDuration(duration == null ? null : LocalTime.parse(duration));
        route.setStartingPoint(startingPoint);
        route.setFlatVsHilly(flatVsHilly);
        route.setLoopVsOutBack(loopVsOutBack);
        route.setStreetsVsTrail(streetsVsTrail);
        route.setEvenVsUneven(evenVsUneven);
        route.setDifficulty(difficulty);
        route.setNotes(notes);
        route.setFavorite(favorite);

        return route;
    }

    public int getID() { return id; }
    public String getDocID() { return docID; }
    public String getName() { return name; }
    public int getSteps() { return steps; }
    public String getStartDate() { return startDate; }
    public String getDuration() { return duration; }
    public String getStartingPoint() { return startingPoint; }
    public String getFlatVsHilly() { return flatVsHilly; }
    public String getLoopVsOutBack() { return loopVsOutBack; }
    public String getStreetsVsTrail() { return streetsVsTrail; }
    public String getEvenVsUneven() { return evenVsUneven; }
    public String getDifficulty() { return difficulty; }
    public String getNotes() { return notes; }
    public boolean isFavorite() { return favorite; }
}
