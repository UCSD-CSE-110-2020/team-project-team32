package com.example.cse110_project.team;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class TeamMember {
    public static final boolean STATUS_PENDING = false;
    public static final boolean STATUS_MEMBER = true;

    private String name;
    private String email;
    private int color;
    private boolean status;

    public TeamMember() {}

    public TeamMember(String name, String email, int color) {
        this.name = name;
        this.email = email;
        this.color = color;
    }

    @Override @NonNull
    public String toString() {
        return email + ": (" + name + ", " + color + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TeamMember) {
            TeamMember tm = (TeamMember)o;
            return tm.getEmail().equals(email);
        }
        return false;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String retrieveInitials() {
        StringBuilder initials = new StringBuilder();
        String[] splitName = name.split(" ");
        for (String str : splitName) {
            if (str.length() > 0) {
                initials.append(str.charAt(0));
            }
        }
        return initials.toString();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
