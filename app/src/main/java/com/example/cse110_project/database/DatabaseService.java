package com.example.cse110_project.database;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.team.Team;

public interface DatabaseService extends DatabaseSubject{
    void addRoute(Route route);
    void updateRoute(Route route);

    void addInvite(Invite invite);
    void declineInvite(Invite invite);
    void acceptInvite(Invite invite);

    void createTeam(Team team);
    void updateTeam(Team team);

    void addTeamListener(Team team);
    void addInvitesListener(User listener);
    void addTeammateRoutesListener(User listener, TeamMember teammate);
}
