package com.example.cse110_project.team;


import com.example.cse110_project.user_routes.Route;

public class TeamRoute extends Route {
    private Route route;
    private String creatorEmail;
    private String memberName;

    public TeamRoute(Route route, String creatorEmail, String memberName) {
        this.route = route;
        this.creatorEmail = creatorEmail;
        this.memberName = memberName;
    }


    public Route getRoute() { return route; }
    public void setCreatorEmail(String creatorEmail) { this.creatorEmail = creatorEmail; }
    public String getCreatorEmail() { return creatorEmail; }
    public void setMemberName(String memberName) { this.memberName = memberName;}
    public String getMemberName() { return memberName;}
}
