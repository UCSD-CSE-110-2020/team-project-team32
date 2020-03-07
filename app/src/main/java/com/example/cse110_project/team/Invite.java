package com.example.cse110_project.team;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseService;
import com.google.firebase.firestore.ListenerRegistration;

public class Invite {
    private static final String TAG = "Invite";

    private String invitedMemberId;
    private String teamId;
    private String creatorId;

    public Invite() {}

    public Invite(String invitedMemberId, String teamId, String creatorId) {
        this.invitedMemberId = invitedMemberId;
        this.teamId = teamId;
        this.creatorId = creatorId;
    }

    @Override @NonNull
    public String toString() {
        return "[" + teamId + "]: " + creatorId + " --> " + invitedMemberId;
    }

    public String getInvitedMemberId() { return invitedMemberId; }
    public String getTeamId() { return teamId; }
    public String getCreatorId() { return creatorId; }

    public void send(String name, int color) {
        Log.d(TAG, "Sending invite to " + name + " (" + invitedMemberId + ") for team " + teamId);
        WWRApplication.getDatabase().addInvite(this);
        Team team = WWRApplication.getUser().getTeam();
        TeamMember newMember = new TeamMember(name, invitedMemberId, color);
        team.getMembers().add(newMember);

        WWRApplication.getDatabase().updateTeam(team);
        WWRApplication.getDatabase().addTeammatesListener(team);
        WWRApplication.getDatabase().addTeammateRoutesListener(WWRApplication.getUser(), newMember);
    }

    public void accept() {
        DatabaseService db = WWRApplication.getDatabase();
        db.acceptInvite(this);
        Team team = new Team();
        team.setId(teamId);
        WWRApplication.getUser().setTeam(team);

        // Link team & user up to database
        db.addTeammatesListener(team);
        for (TeamMember teammate : team.getMembers()) {
            db.addTeammateRoutesListener(WWRApplication.getUser(), teammate);
        }
    }

    public void decline() {
        WWRApplication.getDatabase().declineInvite(this);
    }
}
