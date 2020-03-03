package com.example.cse110_project.team;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.user_routes.UserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {
    public static final String TEAM_ID_KEY = "teamId";
    public static final String INVITER_KEY = "inviter";

    private String id;
    private List<TeamMember> members;

    public Team() { members = new ArrayList<>(); }

    @Override @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(": {\n");
        for (TeamMember member : members) {
            sb.append("\t");
            sb.append(member.getEmail());
            sb.append(",\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<TeamMember> getMembers() { return members; }

    public TeamMember findMemberById(String memberId) {
        for (TeamMember member : members) {
            if (member.getEmail().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    public void removeMemberById(String memberId) {
        for (int i = members.size() - 1; i >= 0; i--) {
            if (members.get(i).getEmail().equals(memberId)) {
                members.remove(i);
                return;
            }
        }
    }
}
