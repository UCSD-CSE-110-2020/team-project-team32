package com.example.cse110_project.database;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamRoute;

import java.util.List;

public interface DatabaseServiceObserver {
    public void updateOnTeamChange(DatabaseService db, Team team);
    public void updateOnInvitesChange(DatabaseService db, List<Invite> invite);
    public void updateOnTeamRoutesChange(DatabaseService db, List<TeamRoute> teamRoutes);
}
