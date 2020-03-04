package com.example.cse110_project.database;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.TeamMember;
import com.example.cse110_project.user_routes.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.Team;

import java.util.List;
import java.util.Map;

public interface DatabaseService {
    public void addRoute(UserRoute route);
    public void updateRoute(UserRoute route);
    public void getRoutes(List<Route> routes);

    public void createInvite(String teamId, String memberId, Map<String, Object> content);
    public void removeInvite(String teamId, String memberId);
    public List<Map<String, Object>> getInvites(String memberId);

    public void createTeam(Team team);
    public void updateTeam(Team team);
    public void getTeamMembers(Team team);
    public void getRoutesByUser(String userId, List<TeamRoute> routes);
}
