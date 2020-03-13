package com.example.cse110_project.user_routes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.local_data.TeamData;
import com.example.cse110_project.local_data.UserData;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.local_data.DataConstants;
import com.example.cse110_project.util.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {
    private static final String TAG = "User";

    private String email;
    private Context context;
    private RouteList routes;

    private TeamMediator teamMediator;
    private List<Invite> invites;

    private int height;
    private int fitnessSteps; // Used for steps provided by a FitnessService
    private int stepsOffset;  // Used for steps provided by in-app mocking

    public User(Context c) {
        context = c;
        routes = new RouteList(context);
        teamMediator = new TeamMediator();
        invites = new ArrayList<>();

        email = UserData.retrieveEmail(context);
        height = UserData.retrieveHeight(context);
        email = UserData.retrieveEmail(context);
    }

    public void initFromDatabase() {
        // Init invites
        if (WWRApplication.hasDatabase()) {
            WWRApplication.getDatabase().addInvitesListener(this);
        }
        Log.d(TAG, "initFromDatabase: invites is " + invites);

        // Init team
        teamMediator.initTeam(WWRApplication.getDatabase(), this);
        Log.d(TAG, "initFromDatabase: teamId is " + getTeam().getId());
    }

    public Context getContext() { return context; }
    public Team getTeam() { return teamMediator.getTeam(); }
    public void setTeam(Team team) { teamMediator.setTeam(team, context); }

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

    public RouteList getRoutes() { return routes; }
    public List<TeamRoute> getTeamRoutes() { return teamMediator.getTeamRoutes(); }
    public List<Invite> getInvites() { return invites; }

    public TeamRoute getTeamRouteByDocId(String docId) {
        return teamMediator.getTeamRouteByDocId(docId);
    }

    public void setTeamRouteFavorite(TeamRoute route, boolean favorite) {
        route.setFavorite(favorite);
        TeamData.saveTeamRouteFavorite(context, route.getDocID(), favorite);
    }

    public void updateTeamRoute(TeamRoute route, int steps, LocalTime time, LocalDateTime date) {
        teamMediator.updateTeamRoute(context, route, steps, time, date);
    }

    public void addTeamRoute(TeamRoute route) {
        teamMediator.addTeamRoute(context, route);
    }
}