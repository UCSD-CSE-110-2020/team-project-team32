package com.example.cse110_project.user_routes;

import android.content.Context;
import android.util.SparseIntArray;

import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.data_access.UserData;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteList {
    private static int routeID = 1;
    private List<Route> routes;

    // Used for constant-time retrieval by route id
    private SparseIntArray idToIndex;

    public RouteList(Context c) {
        routes = new ArrayList<>();
        idToIndex = new SparseIntArray();
        processRoutes(c);
    }

    @Override
    public String toString() {
        String result = "[";
        for (Route r : routes) {
            result += r + "\n";
        }
        return result + "]";
    }

    public int length() {
        return routes.size();
    }

    public Route getRoute(int index) {
        return routes.get(index);
    }

    public Route getRouteByID(int routeID) {
        return routes.get(idToIndex.get(routeID));
    }

    public void createRoute(Context c, Route r) {
        routeID++;
        r.setID(routeID);
        routes.add(r);
        idToIndex.put(routeID, routes.size() - 1);

        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);
    }

    public void updateRouteData(Context c, int id, int steps, LocalTime time, LocalDateTime date) {
        // Set local values
        Route route = getRouteByID(id);
        route.setSteps(steps);
        route.setDuration(time);
        route.setStartDate(date);

        // Update saved values
        RouteData.saveRouteSteps(c, id, steps);
        RouteData.saveRouteTime(c, id, time.toString());
        RouteData.saveRouteDate(c, id, date.toString());
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
        int idx = 0;

        for (String idStr : routeIDs) {
            int id = Integer.parseInt(idStr);
            idToIndex.put(id, idx);
            idx++;
            if (id > maxID) {
                maxID = id;
            }

            int steps = RouteData.retrieveRouteSteps(c, id);
            String name = RouteData.retrieveRouteName(c, id);
            String startPt = RouteData.retrieveStartingPoint(c, id);
            String hillyVsFlat = RouteData.retrieveFlatVsHilly(c,id);
            String loopVsOAB = RouteData.retrieveLoopVsOAB(c,id);
            String streetsVsTrail = RouteData.retrieveStreetVsTrail(c,id);
            String evenVsUneven = RouteData.retrieveEvenVsUneven(c,id);
            String difficulty = RouteData.retrieveDifficulty(c,id);
            String notes = RouteData.retrieveNotes(c, id);

            Route r = new Route(id, name);
            r.setSteps(steps);
            r.setStartingPoint(startPt);
            r.setFlatVsHilly(hillyVsFlat);
            r.setLoopVsOAB(loopVsOAB);
            r.setStreetsVsTrail(streetsVsTrail);
            r.setEvenVsUneven(evenVsUneven);
            r.setDifficulty(difficulty);
            r.setNotes(notes);
            routes.add(r);

            String time = RouteData.retrieveRouteTime(c, id);
            if ( ! time.equals(DataConstants.STR_NOT_FOUND)) {
                r.setDuration(LocalTime.parse(time));
            }

            String date = RouteData.retrieveRouteDate(c, id);
            if ( ! date.equals(DataConstants.STR_NOT_FOUND)) {
                r.setStartDate(LocalDateTime.parse(date));
            }
        }

        routeID = maxID;
    }
}