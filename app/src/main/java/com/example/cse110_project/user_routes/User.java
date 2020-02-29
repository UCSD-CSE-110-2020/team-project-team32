package com.example.cse110_project.user_routes;

import android.content.Context;

import com.example.cse110_project.util.MilesCalculator;

public class User {
    private String email;
    private Context context;
    private RouteList routes;
    private Team team;

    private int height;
    private int fitnessSteps; // Used for steps provided by a FitnessService
    private int stepsOffset;  // Used for steps provided by in-app mocking

    public User(Context c) {
        context = c;
        routes = new RouteList(context);
        team = new Team();
        height = UserData.retrieveHeight(context);
    }

    public Team getTeam() { return team; }

    public void setTeam(Team team) { this.team = team; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
        UserData.saveEmail(context, email);
    }

    public int getHeight() { return height; }

    public void setHeight(int h) {
        height = h;
        UserData.saveHeight(context, height);
    }

    public int getFitnessSteps() { return fitnessSteps; }

    public int getStepsOffset() { return stepsOffset; }

    public void setFitnessSteps(int s) { fitnessSteps = s; }

    public void setStepsOffset(int s) { stepsOffset = s; }

    public int getTotalSteps() { return stepsOffset + fitnessSteps; }

    public double getMiles() {
        return MilesCalculator.calculateMiles(height, getTotalSteps());
    }

    public RouteList getRoutes(){ return routes; }
}