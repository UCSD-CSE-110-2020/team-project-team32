package com.example.cse110_project.user_routes;

import android.content.Context;
import android.util.SparseIntArray;

import androidx.annotation.NonNull;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.util.DataConstants;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteList {
    // Stores max route id currently used
    private static int routeID = 0;
    private List<Route> routes;
    private Context context;

    // Used for constant-time retrieval by route id
    private SparseIntArray idToIndex;

    public RouteList(Context c) {
        context = c;
        routes = new ArrayList<>();
        idToIndex = new SparseIntArray();
        processRoutes();
    }

    // Used in logging
    @Override @NonNull
    public String toString() {
        String result = "[";
        for (Route r : routes) {
            result += "\t" + r + "\n";
        }
        return result + "]";
    }

    public int length() {
        return routes.size();
    }
    public Route getRoute(int index) {
        return routes.get(index);
    }
    public Route getRouteByID(int routeID) { return routes.get(idToIndex.get(routeID)); }

    public void clear() { routes.clear(); }

    // Overwrites given route's id to ensure uniqueness
    public void createRoute(Route r) {
        routeID++;
        r.setID(routeID);
        routes.add(r);
        idToIndex.put(routeID, routes.size() - 1);

        if (WWRApplication.hasDatabase()) {
            WWRApplication.getDatabase().addRoute(r);
        }

        UserData.saveRoute(context, r);
        RouteData.saveRouteData(context, r);
    }

    public void setRouteFavorite(int id, boolean fav) {
        Route route = getRouteByID(id);
        route.setFavorite(fav);
        RouteData.saveFavorite(context, id, fav);
    }

    // Updates route with new walk data
    public void updateRouteData(int id, int steps, LocalTime time, LocalDateTime date) {
        // Set local values
        Route route = getRouteByID(id);
        route.setSteps(steps);
        route.setDuration(time);
        route.setStartDate(date);

        // Update saved values
        if (WWRApplication.hasDatabase()) {
            WWRApplication.getDatabase().updateRoute(route);
        }

        RouteData.saveRouteSteps(context, id, steps);
        RouteData.saveRouteTime(context, id, time.toString());
        RouteData.saveRouteDate(context, id, date.toString());
    }

    public void sortByName() {
        Collections.sort(routes, (r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
        for (int i = 0; i < routes.size(); i++) {
            idToIndex.put(routes.get(i).getID(), i);
        }
    }

    // Returns null if no route has been walked
    public Route getMostRecentRoute() {
        if (routes.size() == 0) {
            return null;
        }

        Route recent = routes.get(0);
        for (Route r : routes) {
            if ( ! recent.hasWalkData() ||
                    (r.hasWalkData() && r.getStartDate().isAfter(recent.getStartDate()))) {
                recent = r;
            }
        }

        return recent.hasWalkData() ? recent : null;
    }

    // Processes list of saved route IDs and retrieves route-specific data
    private void processRoutes() {
        String routeIDList = UserData.retrieveRouteList(context);
        if (routeIDList.equals(DataConstants.NO_ROUTES_FOUND)) {
            return;
        }

        String[] routeIDs = UserData.retrieveRouteList(context).split(DataConstants.LIST_SPLIT);
        routeID = 0;
        int index = 0;

        for (String idStr : routeIDs) {
            int id = Integer.parseInt(idStr);
            idToIndex.put(id, index);
            index++;
            if (id > routeID) {
                routeID = id;
            }

            int steps = RouteData.retrieveRouteSteps(context, id);
            String docId = RouteData.retrieveRouteDocID(context, id);
            String name = RouteData.retrieveRouteName(context, id);
            String startPt = RouteData.retrieveStartingPoint(context, id);
            String hillyVsFlat = RouteData.retrieveFlatVsHilly(context,id);
            String loopVsOAB = RouteData.retrieveLoopVsOutBack(context,id);
            String streetsVsTrail = RouteData.retrieveStreetVsTrail(context,id);
            String evenVsUneven = RouteData.retrieveEvenVsUneven(context,id);
            String difficulty = RouteData.retrieveDifficulty(context,id);
            String notes = RouteData.retrieveNotes(context, id);
            boolean fav = RouteData.retrieveFavorite(context, id);

            UserRoute r = new UserRoute(id, name);
            r.setSteps(steps);
            r.setDocID(docId);
            r.setStartingPoint(startPt);
            r.setFlatVsHilly(hillyVsFlat);
            r.setLoopVsOutBack(loopVsOAB);
            r.setStreetsVsTrail(streetsVsTrail);
            r.setEvenVsUneven(evenVsUneven);
            r.setDifficulty(difficulty);
            r.setNotes(notes);
            r.setFavorite(fav);
            routes.add(r);

            String time = RouteData.retrieveRouteTime(context, id);
            if ( ! time.equals(DataConstants.STR_NOT_FOUND)) {
                r.setDuration(LocalTime.parse(time));
            }

            String date = RouteData.retrieveRouteDate(context, id);
            if ( ! date.equals(DataConstants.STR_NOT_FOUND)) {
                r.setStartDate(LocalDateTime.parse(date));
            }
        }
    }
}