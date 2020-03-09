package com.example.cse110_project.team;

import com.example.cse110_project.database.RouteFirebaseAdapter;
import com.example.cse110_project.user_routes.Route;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ScheduledWalk {
    // Statuses for this scheduled walk
    public static final int PROPOSED = 0;
    public static final int SCHEDULED = 1;
    public static final int WITHDRAWN = -1;

    // Statuses for member responses
    public static final int NO_RESPONSE = 0;
    public static final int ACCEPTED = 1;
    public static final int DECLINED_BAD_TIME = -1;
    public static final int DECLINED_BAD_ROUTE = -2;

    private int status;
    private RouteFirebaseAdapter routeAdapter;
    private String dateTimeStr;
    private String creatorId;
    private Map<String, Integer> responses;

    public ScheduledWalk() {}

    public ScheduledWalk(Route route, LocalDateTime dateTime, String creatorId, Team team) {
        this.status = PROPOSED;
        this.routeAdapter = new RouteFirebaseAdapter(route);
        this.dateTimeStr = dateTime.toString();
        this.creatorId = creatorId;

        responses = new HashMap<>();
        for (TeamMember member : team.getMembers()) {
            if ( ! member.getEmail().equals(creatorId)) {
                responses.put(member.getEmail(), NO_RESPONSE);
            }
        }
    }

    public int getStatus() { return status; }
    public RouteFirebaseAdapter getRouteAdapter() { return routeAdapter; }
    public Route getRoute() { return routeAdapter.toRoute(); }

    public String getDateTimeStr() { return dateTimeStr; }
    public LocalDateTime getDateTime() { return LocalDateTime.parse(dateTimeStr); }

    public String getCreatorId() { return creatorId; }
    public Map<String, Integer> getResponses() { return responses; }

    public void scheduleWalk() { status = SCHEDULED; }
    public void withdrawWalk() { status = WITHDRAWN; }

    public int getResponse(String memberId) { return responses.get(memberId); }

    public void acceptWalk(String memberId) { responses.put(memberId, ACCEPTED); }
    public void declineWalkBadTime(String memberId) {
        responses.put(memberId, DECLINED_BAD_TIME);
    }

    public void declineWalkBadRoute(String memberId) {
        responses.put(memberId, DECLINED_BAD_ROUTE);
    }

    public String getStringStatus() {
        if (status == 0) {
            return "Proposed";
        }
        else if (status == 1) {
            return "Scheduled";
        }
        else {
            return "Withdrawn";
        }
    }
}
