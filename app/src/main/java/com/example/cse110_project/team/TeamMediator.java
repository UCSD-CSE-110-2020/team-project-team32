package com.example.cse110_project.team;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.local_data.DataConstants;
import com.example.cse110_project.local_data.TeamData;
import com.example.cse110_project.local_data.UserData;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamMediator {
    private static final String TAG = TeamMediator.class.getSimpleName();

    private Team team;
    private List<TeamRoute> teamRoutes;

    public TeamMediator() {
        team = new Team();
        teamRoutes = new ArrayList<>();
    }

    public Team getTeam() {
        sortTeamMembersByName();
        return team;
    }

    public void setTeam(Team team, Context context) {
        this.team = team;
        UserData.saveTeamID(context, team.getId());
    }

    public void initTeam(DatabaseService db, User user) {
        Context context = user.getContext();
        String email = user.getEmail();
        team.setId(UserData.retrieveTeamID(context));

        if (db != null) {
            if (team.getId().equals(DataConstants.NO_TEAMID_FOUND)) {
                Log.d(TAG, "Creating new team");
                addTeamMember(email, email, TeamMember.STATUS_MEMBER);
                db.createTeam(team);
                UserData.saveTeamID(context, team.getId());

            } else {
                Log.d(TAG, "Retrieving existing team");
                db.addTeamListener(team);
            }
        }
    }

    public void addTeamMember(String name, String email, boolean status) {
        Random rand = new Random();
        int low = 1;
        int high = 255;
        int r = rand.nextInt(high-low)+low;
        int g = rand.nextInt(high-low)+low;
        int b = rand.nextInt(high-low)+low;
        int randomColor = Color.rgb(r,g,b);

        TeamMember member = new TeamMember(email, email, randomColor);
        member.setStatus(status);
        team.getMembers().add(member);
    }

    public List<TeamRoute> getTeamRoutes() {
        sortTeamRouteByName();
        return teamRoutes;
    }

    public TeamRoute getTeamRouteByDocId(String docId) {
        for (TeamRoute route : teamRoutes) {
            if (route.getDocID().equals(docId)) {
                return route;
            }
        }
        return null;
    }

    public void updateTeamRoute(Context context, TeamRoute route, int steps, LocalTime time,
                                LocalDateTime date) {
        Log.d(TAG, "Updating team route " + route + " with docId " + route.getDocID() +
                " to (" + steps + ", " + time + ", " + date + ")");
        String docID = route.getDocID();

        route.setSteps(steps);
        TeamData.saveTeamRouteSteps(context, docID, steps);
        route.setDuration(time);
        TeamData.saveTeamRouteTime(context, docID, time.toString());
        route.setStartDate(date);
        TeamData.saveTeamRouteDate(context, docID, date.toString());
    }

    public void addTeamRoute(Context context, TeamRoute route) {
        String docId = route.getDocID();

        int steps = TeamData.retrieveTeamRouteSteps(context, docId);
        if (steps != DataConstants.INT_NOT_FOUND) {
            Log.d(TAG, "Storing retrieved team route steps");
            route.getRoute().setSteps(steps);
        }

        String time = TeamData.retrieveTeamRouteTime(context, docId);
        if ( ! DataConstants.STR_NOT_FOUND.equals(time)) {
            Log.d(TAG, "Storing retrieved team route time");
            route.getRoute().setDuration(LocalTime.parse(time));
        }

        String date = TeamData.retrieveTeamRouteDate(context, docId);
        if ( ! DataConstants.STR_NOT_FOUND.equals(date)) {
            Log.d(TAG, "Storing retrieved team route date");
            route.getRoute().setStartDate(LocalDateTime.parse(date));
        }

        route.getRoute().setFavorite(TeamData.retrieveTeamRouteFavorite(context, docId));
        route.setFavorite(TeamData.retrieveTeamRouteFavorite(context, docId));

        teamRoutes.add(route);
    }

    private void sortTeamMembersByName() {
        team.getMembers().sort((t1, t2) ->
                t1.getName().compareToIgnoreCase(t2.getName()));
    }

    private void sortTeamRouteByName() {
        teamRoutes.sort((r1, r2) ->
                r1.getRoute().getName().compareToIgnoreCase(r2.getRoute().getName()));
    }
}
