package com.example.cse110_project.team;

import com.example.cse110_project.database.RouteFirebaseAdapter;
import com.example.cse110_project.user_routes.Route;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ScheduledWalk {
    private static final String TAG = ScheduledWalk.class.getSimpleName();

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
    public Route retrieveRoute() { return routeAdapter.toRoute(); }

    public String getDateTimeStr() { return dateTimeStr; }
    public LocalDateTime retrieveScheduledDate() { return LocalDateTime.parse(dateTimeStr); }

    public String getCreatorId() { return creatorId; }
    public Map<String, Integer> getResponses() { return responses; }

    public void schedule() { status = SCHEDULED; }
    public void withdraw() { status = WITHDRAWN; }

    public int retrieveResponse(String memberId) { return responses.get(memberId); }

    public void accept(String memberId) { responses.put(memberId, ACCEPTED); }
    public void declineBadTime(String memberId) {
        responses.put(memberId, DECLINED_BAD_TIME);
    }
    public void declineBadRoute(String memberId) {
        responses.put(memberId, DECLINED_BAD_ROUTE);
    }

    public String retrieveStringStatus() {
        if (status == 0) {
            return "Proposed";
        } else if (status == 1) {
            return "Scheduled";
        } else {
            return "Withdrawn";
        }
    }
}
