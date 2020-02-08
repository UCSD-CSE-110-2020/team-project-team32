package com.example.cse110_project;

import android.content.Context;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RouteList {
    private static int routeID;
    private List<Route> routes;

    public RouteList(Context c) {
        routes = new ArrayList<>();
        processRoutes(c);
    }

    public void createRoute(Context c, String name) {
        routeID++;
        Route r = new Route(routeID, name);
        routes.add(r);

        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
    }

    public Route getMostRecentRoute() {
        if (routes.size() == 0) {
            return null;
        }

        Route recent = routes.get(0);
        for (Route r : routes) {
            if (r.getStartDate().isAfter(recent.getStartDate())) {
                recent = r;
            }
        }
        return recent;
    }

    private void processRoutes(Context c) {
        String routeIDList = UserData.retrieveRouteList(c);
        if (routeIDList == DataConstants.NO_ROUTES_FOUND) {
            return;
        }

        String[] routeIDs = UserData.retrieveRouteList(c).split(DataConstants.LIST_SPLIT);
        int maxID = -1;

        for (String idStr : routeIDs) {
            int id = Integer.parseInt(idStr);
            if (id > maxID) {
                maxID = id;
            }

            String name = RouteData.retrieveRouteName(c, id);
            int steps = RouteData.retrieveRouteSteps(c, id);
            LocalTime time = LocalTime.parse(RouteData.retrieveRouteTime(c, id));
            LocalDateTime date = LocalDateTime.parse(RouteData.retrieveRouteDate(c, id));

            routes.add(new Route(id, name, steps, time, date));
        }

        routeID = maxID;
    }
}