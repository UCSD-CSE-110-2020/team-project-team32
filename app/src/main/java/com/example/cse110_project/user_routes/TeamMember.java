package com.example.cse110_project.user_routes;

import android.graphics.Color;

public class TeamMember {
    private String name;
    private String email;
    private Color color;

    public TeamMember(String name, String email, Color color) {
        this.name = name;
        this.email = email;
        this.color = color;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }
}
