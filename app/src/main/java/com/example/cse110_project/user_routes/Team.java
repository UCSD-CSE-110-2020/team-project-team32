package com.example.cse110_project.user_routes;

import com.example.cse110_project.WWRApplication;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String id;
    private List<TeamMember> members;

    public Team() {
        members = new ArrayList<TeamMember>();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<TeamMember> getMembers() { return members; }

    public void addMember(TeamMember member) {
        members.add(member);
        if (WWRApplication.hasDatabase()) {
            WWRApplication.getDatabase().updateTeam(this);
        }
    }
}
