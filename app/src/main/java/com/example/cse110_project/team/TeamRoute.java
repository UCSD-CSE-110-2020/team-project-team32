package com.example.cse110_project.team;


import com.example.cse110_project.user_routes.Route;

public class TeamRoute extends Route {
    private Route route;
    private TeamMember creator;

    public TeamRoute(Route route, TeamMember creator) {
        setID(route.getID());
        setName(route.getName());
        setDocID(route.getDocID());
        setSteps(route.getSteps());
        setDuration(route.getDuration());
        setStartDate(route.getStartDate());
        setStartingPoint(route.getStartingPoint());
        setDifficulty(route.getDifficulty());
        setFlatVsHilly(route.getFlatVsHilly());
        setLoopVsOutBack(route.getLoopVsOutBack());
        setEvenVsUneven(route.getEvenVsUneven());
        setStreetsVsTrail(route.getStreetsVsTrail());
        setNotes(route.getNotes());
        setFavorite(route.isFavorite());

        this.route = route;
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TeamRoute && ((TeamRoute)o).getDocID().equals(getDocID());
    }

    public Route getRoute() { return route; }
    public TeamMember getCreator() { return creator; }
}
