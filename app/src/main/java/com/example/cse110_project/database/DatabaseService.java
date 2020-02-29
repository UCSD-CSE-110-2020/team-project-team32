package com.example.cse110_project.database;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;

import java.util.List;

public interface DatabaseService {
    public void addRoute(UserRoute route);
    public void updateRoute(UserRoute route);
    public List<Route> getRoutes();

    public void createTeam(Team team);
    public void updateTeam(Team team);
    public List<TeamRoute> getTeamRoutes(String memberId);
}
