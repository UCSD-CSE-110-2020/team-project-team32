package com.example.cse110_project.database;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.team.Team;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public interface DatabaseService {
    public void addRoute(Route route);
    public void updateRoute(Route route);
    public void getRoutes(List<Route> routes);

    public void addInvite(Invite invite);
    public void removeInvite(Invite invite);
    public void getInvites(String memberId, List<Invite> invites);

    public Task<?> createTeam(Team team);
    public void removeTeam(Team team);
    public Task<?> updateTeam(Team team);
    public ListenerRegistration addTeammatesListener(Team team);
    public void removeTeammatesListener(ListenerRegistration listener);
    public void addTeammateRoutesListener(User listener, TeamMember teammate);
}
