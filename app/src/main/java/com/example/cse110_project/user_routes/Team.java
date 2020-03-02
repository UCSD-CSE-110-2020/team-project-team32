package com.example.cse110_project.user_routes;

import android.content.Context;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.util.DataConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    public static final String TEAM_ID_KEY = "teamId";
    public static final String INVITER_KEY = "inviter";

    private String id;
    private List<TeamMember> members;

    public Team() {
        members = new ArrayList<>();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<TeamMember> getMembers() { return members; }

    public void inviteMember(Context context, TeamMember member) {
        String getTeamID = UserData.retrieveTeamID(context);
        setId(getTeamID);
        if (id == DataConstants.NO_TEAMID_FOUND && WWRApplication.hasDatabase()) {
            WWRApplication.getDatabase().createTeam(this);
            UserData.saveTeamID(context, this.getId());
            System.out.println("Team id is " + this.getId());
        }

        members.add(member);
        if (WWRApplication.hasDatabase()) {
            DatabaseService db = WWRApplication.getDatabase();
            db.updateTeam(this);

            Map<String, Object> invite = new HashMap<>();
            invite.put(TEAM_ID_KEY, id);
            invite.put(INVITER_KEY, WWRApplication.getUser().getEmail());

            WWRApplication.getDatabase().createInvite(id, member.getEmail(), invite);
        }
    }

}
