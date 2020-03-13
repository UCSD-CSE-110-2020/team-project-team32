package com.example.cse110_project.team;

import android.util.Log;

import com.example.cse110_project.user_routes.RouteFirebaseAdapter;
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

    @Override
    public boolean equals(Object o) {
        boolean equals = o instanceof ScheduledWalk &&
                ((ScheduledWalk) o).getCreatorId().equals(creatorId) &&
                ((ScheduledWalk) o).getRouteAdapter().equals(routeAdapter) &&
                ((ScheduledWalk) o).getDateTimeStr().equals(dateTimeStr) &&
                ((ScheduledWalk) o).getStatus() == status &&
                ((ScheduledWalk) o).getResponses().size() == responses.size();
        if ( ! equals) {
            return false;
        }

        for (String member : responses.keySet()) {
            Integer oResp = ((ScheduledWalk) o).getResponses().get(member);
            if (oResp == null || ! oResp.equals(responses.get(member))) {
                equals = false;
            }
        }

        return equals;
    }

    public int getStatus() { return status; }
    public RouteFirebaseAdapter getRouteAdapter() { return routeAdapter; }
    public Route retrieveRoute() { return routeAdapter.toRoute(); }
    public void setRoute(Route route) {
        routeAdapter = new RouteFirebaseAdapter(route);
    }

    public String getDateTimeStr() { return dateTimeStr; }
    public LocalDateTime retrieveScheduledDate() { return LocalDateTime.parse(dateTimeStr); }

    public String getCreatorId() { return creatorId; }
    public void setCreatorId(String creatorId) { this.creatorId = creatorId; }
    public Map<String, Integer> getResponses() { return responses; }

    public void schedule() { status = SCHEDULED; }
    public void withdraw() { status = WITHDRAWN; }

    public int retrieveResponse(String memberId) { return responses.get(memberId); }

    public void accept(String memberId) {
        responses.put(memberId, ACCEPTED);
        Log.d(TAG, "Accepted walk: " + (ACCEPTED == responses.get(memberId)));
    }
    public void declineBadTime(String memberId) {
        responses.put(memberId, DECLINED_BAD_TIME);
        Log.d(TAG, "Declined for bad time: " + (DECLINED_BAD_TIME == responses.get(memberId)));
    }
    public void declineBadRoute(String memberId) {
        responses.put(memberId, DECLINED_BAD_ROUTE);
        Log.d(TAG, "Declined for bad route: " + (DECLINED_BAD_ROUTE == responses.get(memberId)));
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

    public String parseIntResponse(int resp) {
        if (resp == ACCEPTED) {
            return "Accepted";
        } else if (resp == DECLINED_BAD_ROUTE) {
            return "Declined (not a good route for me)";
        } else if (resp == DECLINED_BAD_TIME) {
            return "Declined (not a good time for me)";
        } else {
            return "No response";
        }
    }
}
