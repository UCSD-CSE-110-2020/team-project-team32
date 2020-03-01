package com.example.cse110_project.user_routes;

import android.graphics.Color;

public class TeamMember {
    public static final boolean STATUS_PENDING = false;
    public static final boolean STATUS_MEMBER = true;

    private String name;
    private String email;
    private int color;
    private boolean status;

    public TeamMember() {}

    public TeamMember(String name, String email,int color) {
        this.name = name;
        this.email = email;
        this.color = color;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getColor() { return color; }

    public void setColor(int color) { this.color = color; }

    public boolean getStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }
}
