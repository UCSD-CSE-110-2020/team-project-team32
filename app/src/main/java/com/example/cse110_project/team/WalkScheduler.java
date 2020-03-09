package com.example.cse110_project.team;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.Route;

import java.time.LocalDateTime;

public class WalkScheduler {
    public void createScheduledWalk(Route route, LocalDateTime dateTime, String creatorId,
                                    Team team) {
        ScheduledWalk walk = new ScheduledWalk(route, dateTime, creatorId, team);
        team.setScheduledWalk(walk);
        WWRApplication.getDatabase().updateTeam(team);
    }

    public void updateScheduledWalk(Team team) {
        WWRApplication.getDatabase().updateTeam(team);
    }

    // withdraw Scheduled walk
    public void removeScheduledWalk(Team team) {
        team.setScheduledWalk(null);
        WWRApplication.getDatabase().updateTeam(team);
    }
}
