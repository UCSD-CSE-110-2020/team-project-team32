package com.example.cse110_project.database;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamRoute;

import java.util.List;

public interface DatabaseServiceObserver {
    void updateOnTeamChange(DatabaseService db, Team team);
    void updateOnTeamRoutesChange(DatabaseService db, List<TeamRoute> teamRoutes);

    void updateOnInvitesChange(DatabaseService db, List<Invite> invite);
    void updateOnInviteAccepted(DatabaseService db, Invite invite, Team invitedTeam);
    void updateOnInviteDeclined(DatabaseService db, Invite invite, Team invitedTeam);
}
