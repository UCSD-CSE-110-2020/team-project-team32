package com.example.cse110_project.user_routes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.util.MilesCalculator;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final String TAG = "User";

    private String email;
    private Context context;
    private RouteList routes;

    private Team team;
    private List<TeamRoute> teamRoutes;
    private List<Invite> invites;

    private int height;
    private int fitnessSteps; // Used for steps provided by a FitnessService
    private int stepsOffset;  // Used for steps provided by in-app mocking

    public User(Context c) {
        context = c;
        routes = new RouteList(context);
        teamRoutes = new ArrayList<>();
        team = new Team();
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
        team.setId(UserData.retrieveTeamID(context));
        if (WWRApplication.hasDatabase()) {
            if (team.getId().equals(DataConstants.NO_TEAMID_FOUND)) {
                Log.d(TAG, "Creating new team");
                TeamMember self = new TeamMember(email, email, Color.YELLOW);
                self.setStatus(TeamMember.STATUS_MEMBER);
                team.getMembers().add(self);

                WWRApplication.getDatabase().createTeam(team);
                UserData.saveTeamID(context, team.getId());
            } else {
                Log.d(TAG, "Retrieving existing team");
                WWRApplication.getDatabase().addTeamListener(team);
                for (TeamMember teammate : team.getMembers()) {
                    if ( ! teammate.getEmail().equals(email)) {
                        WWRApplication.getDatabase()
                                .addTeammateRoutesListener(this, teammate);
                    }
                }
            }
        }

        Log.d(TAG, "initFromDatabase: teamId is " + team.getId());
    }

    public Context getContext() { return context; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) {
        this.team = team;
        UserData.saveTeamID(context, team.getId());
    }

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
    public List<TeamRoute> getTeamRoutes() { return teamRoutes; }
    public List<Invite> getInvites() { return invites; }
}