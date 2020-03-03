package com.example.cse110_project.team;

import android.graphics.Color;

import com.example.cse110_project.WWRApplication;

public class Invite {
    private String invitedMemberId;
    private String teamId;
    private String creatorId;

    public Invite() {}

    public Invite(String invitedMemberId, String teamId, String creatorId) {
        this.invitedMemberId = invitedMemberId;
        this.teamId = teamId;
        this.creatorId = creatorId;
    }

    public String getInvitedMemberId() { return invitedMemberId; }
    public String getTeamId() { return teamId; }
    public String getCreatorId() { return creatorId; }

    public void send(String name, int color) {
        WWRApplication.getDatabase().addInvite(this);
        Team team = WWRApplication.getUser().getTeam();
        team.getMembers().add(new TeamMember(name, invitedMemberId, color));
        WWRApplication.getDatabase().updateTeam(team);
    }

    public void accept() {
        WWRApplication.getDatabase().removeInvite(this);

        Team team = new Team();
        team.setId(teamId);
        WWRApplication.getDatabase().getTeamMembers(team);

        WWRApplication.getUser().setTeam(team);
        team.findMemberById(invitedMemberId).setStatus(TeamMember.STATUS_MEMBER);
        WWRApplication.getDatabase().updateTeam(team);
    }

    public void decline() {
        WWRApplication.getDatabase().removeInvite(this);
        Team team = WWRApplication.getUser().getTeam();
        team.removeMemberById(invitedMemberId);
        WWRApplication.getDatabase().updateTeam(team);
    }
}
