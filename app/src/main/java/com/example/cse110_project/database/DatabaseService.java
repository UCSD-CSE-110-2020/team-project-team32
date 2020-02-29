package com.example.cse110_project.database;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Map;

public interface DatabaseService {
    public void addRoute(Route route);
    public void updateRoute(Route route);
    public List<Route> getRoutes();

    public void createTeam(Team team);
    public void updateTeam(Team team);
    public List<TeamMember> getTeam();
}
