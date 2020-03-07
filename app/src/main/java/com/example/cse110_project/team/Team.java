package com.example.cse110_project.team;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String id;
    private List<TeamMember> members;

    public Team() { members = new ArrayList<>(); }

    @Override
    public boolean equals(Object o) {
        return o instanceof Team && this.toString().equals(o.toString());
    }

    @Override @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(": {");
        for (TeamMember member : members) {
            //sb.append("\n\t");
            sb.append(member.getEmail());
            sb.append(", ");
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
