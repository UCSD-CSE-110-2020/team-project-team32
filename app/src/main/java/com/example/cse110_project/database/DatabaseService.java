package com.example.cse110_project.database;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.team.Team;

public interface DatabaseService extends DatabaseSubject{
    public void addRoute(Route route);
    public void updateRoute(Route route);

    public void addInvite(Invite invite);
    public void declineInvite(Invite invite);
    public void acceptInvite(Invite invite);

    public void createTeam(Team team);
    public void updateTeam(Team team);

    public void addTeamListener(Team team);
    public void addInvitesListener(User listener);
    public void addTeammateRoutesListener(User listener, TeamMember teammate);
}
