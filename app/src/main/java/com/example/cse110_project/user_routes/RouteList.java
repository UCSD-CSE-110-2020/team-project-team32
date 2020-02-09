package com.example.cse110_project.user_routes;

import android.content.Context;

import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.data_access.UserData;

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

    public List<Route> getRoutes() {
        return routes;
    }

    public void createRoute(Context c, String name) {
        routeID++;
        Route r = new Route(routeID, name);
        addRoute(c, r);
    }

    public void addRoute(Context c, Route r) {
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
            if (recent.getStartDate() == null ||
                    (r.getStartDate() != null && r.getStartDate().isAfter(recent.getStartDate()))) {
                recent = r;
            }
        }

        if (recent.getStartDate() == null) {
            return null;
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
            Route r = new Route(id, name);
            r.setSteps(steps);
            routes.add(r);

            String time = RouteData.retrieveRouteTime(c, id);
            if (time != DataConstants.STR_NOT_FOUND) {
                r.setDuration(LocalTime.parse(time));
            }

            String date = RouteData.retrieveRouteDate(c, id);
            if (date != DataConstants.STR_NOT_FOUND) {
                r.setStartDate(LocalDateTime.parse(date));
            }
        }

        routeID = maxID;
    }
}