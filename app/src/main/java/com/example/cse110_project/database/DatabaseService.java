package com.example.cse110_project.database;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.team.Team;

import java.util.List;
import java.util.Map;

public interface DatabaseService {
    public void addRoute(UserRoute route);
    public void updateRoute(UserRoute route);
    public void getRoutes(List<Route> routes);

    public void addInvite(Invite invite);
    public void removeInvite(Invite invite);
    public void getInvites(String memberId, List<Invite> invites);

    public void createTeam(Team team);
    public void removeTeam(Team team);
    public void updateTeam(Team team);
    public void getTeamMembers(Team team);
    public void getRoutesByUser(String userId, List<TeamRoute> routes);
}
