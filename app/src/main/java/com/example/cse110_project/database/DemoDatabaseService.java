package com.example.cse110_project.database;

import android.graphics.Color;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.util.ArrayList;
import java.util.List;

public class DemoDatabaseService implements DatabaseService {
    private int uniqueId;
    private List<Invite> sentInvites;
    private Team invitedTeam;

    public DemoDatabaseService() {
        uniqueId = -1;
        sentInvites = new ArrayList<>();
    }

    public int getUniqueId() {
        uniqueId++;
        return uniqueId;
    }

    public List<Invite> getSentInvites() { return sentInvites; }

    @Override
    public void addRoute(Route route) {

    }

    @Override
    public void updateRoute(Route route) {

    }

    @Override
    public void addInvite(Invite invite) {
        sentInvites.add(invite);
    }

    @Override
    public void declineInvite(Invite invite) {

    }

    @Override
    public void acceptInvite(Invite invite) {
        invitedTeam = new Team();
        invitedTeam.setId(invite.getTeamId());

        TeamMember creator = new TeamMember("Team Creator", invite.getCreatorId(), Color.YELLOW);
        creator.setStatus(TeamMember.STATUS_MEMBER);
        TeamMember invitee = new TeamMember(WWRApplication.getUser().getEmail(),
                WWRApplication.getUser().getEmail(), Color.CYAN);
        invitee.setStatus(TeamMember.STATUS_MEMBER);

        invitedTeam.getMembers().add(creator);
        invitedTeam.getMembers().add(invitee);
    }

    @Override
    public void createTeam(Team team) {

    }

    @Override
    public void updateTeam(Team team) {

    }

    @Override
    public void addTeamListener(Team team) {
        if (invitedTeam != null && team.getId().equals(invitedTeam.getId())) {
            team.getMembers().clear();
            team.getMembers().addAll(invitedTeam.getMembers());
            invitedTeam = null;
        }
    }

    @Override
    public void addInvitesListener(User listener) {

    }

    @Override
    public void addTeammateRoutesListener(User listener, TeamMember teammate) {

    }

    @Override
    public void register(DatabaseServiceObserver obs) {

    }
}
