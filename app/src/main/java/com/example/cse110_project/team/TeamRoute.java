package com.example.cse110_project.team;


import android.content.Context;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.local_data.TeamData;
import com.example.cse110_project.local_data.DataConstants;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TeamRoute extends Route {
    private Route route;
    private TeamMember creator;

    public TeamRoute(Route route, TeamMember creator) {
        this.route = route;
        this.creator = creator;

        setID(route.getID());
        setName(route.getName());
        setDocID(route.getDocID());
        setSteps(route.getSteps());
        setDuration(route.getDuration());
        setStartDate(route.getStartDate());
        setStartingPoint(route.getStartingPoint());
        setDifficulty(route.getDifficulty());
        setFlatVsHilly(route.getFlatVsHilly());
        setLoopVsOutBack(route.getLoopVsOutBack());
        setEvenVsUneven(route.getEvenVsUneven());
        setStreetsVsTrail(route.getStreetsVsTrail());
        setNotes(route.getNotes());
        setFavorite(route.isFavorite());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TeamRoute && ((TeamRoute)o).getDocID() != null &&
        ((TeamRoute) o).getDocID().equals(getDocID());
    }

    public Route getRoute() { return route; }

    @Override
    public boolean hasWalkData() {
        Context c = WWRApplication.getUser().getContext();
        return TeamData.retrieveTeamRouteSteps(c, this.getDocID()) != DataConstants.INT_NOT_FOUND;
    }

    public TeamMember getCreator() { return creator; }

    @Override
    public void setID(int id) {
        super.setID(id);
        route.setID(id);
    }

    @Override
    public void setName(String n) {
        super.setName(n);
        route.setName(n);
    }

    @Override
    public void setDocID(String docID) {
        super.setDocID(docID);
        route.setDocID(docID);
    }

    @Override
    public void setSteps(int s) {
        super.setSteps(s);
        route.setSteps(s);
    }

    @Override
    public void setDuration(LocalTime time) {
        super.setDuration(time);
        route.setDuration(time);
    }

    @Override
    public void setStartDate(LocalDateTime date) {
        super.setStartDate(date);
        route.setStartDate(date);
    }

    @Override
    public void setStartingPoint(String str) {
        super.setStartingPoint(str);
        route.setStartingPoint(str);
    }

    @Override
    public void setDifficulty(String str) {
        super.setDifficulty(str);
        route.setDifficulty(str);
    }

    @Override
    public void setFlatVsHilly(String str) {
        super.setFlatVsHilly(str);
        route.setFlatVsHilly(str);
    }

    @Override
    public void setLoopVsOutBack(String str) {
        super.setLoopVsOutBack(str);
        route.setLoopVsOutBack(str);
    }

    @Override
    public void setEvenVsUneven(String str) {
        super.setEvenVsUneven(str);
        route.setEvenVsUneven(str);
    }

    @Override
    public void setStreetsVsTrail(String str) {
        super.setStreetsVsTrail(str);
        route.setStreetsVsTrail(str);
    }

    @Override
    public void setNotes(String str) {
        super.setNotes(str);
        route.setNotes(str);
    }

    @Override
    public void setFavorite(boolean favorite) {
        super.setFavorite(favorite);
        route.setFavorite(favorite);
    }
}
