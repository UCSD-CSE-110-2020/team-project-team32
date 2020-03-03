package com.example.cse110_project.team;


import com.example.cse110_project.user_routes.Route;

public class TeamRoute extends Route {
    private Route route;
    private String creatorEmail;

    public TeamRoute(Route route, String creatorEmail) {
        this.route = route;
        this.creatorEmail = creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) { this.creatorEmail = creatorEmail; }
    public String getCreatorEmail() { return creatorEmail; }
}
