package com.example.cse110_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DemoDatabaseService;
import com.example.cse110_project.local_data.DataConstants;
import com.example.cse110_project.local_data.TeamData;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.ScheduledWalk;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.user_routes.UserRoute;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamMockingActivity extends AppCompatActivity {
    private static final String TAG = TeamMockingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_mocking);

        findViewById(R.id.teamMockingBackButton).setOnClickListener(v -> finish());

        findViewById(R.id.teamMockingModeButton).setOnClickListener(v -> launchMockingMode());
        findViewById(R.id.teamMockingAddTeamRoute).setOnClickListener(v -> addTeamRoute());
        findViewById(R.id.teamMockingReceiveInviteButton).setOnClickListener(v -> receiveInvite());
        findViewById(R.id.teamMockingAcceptSentInvites).setOnClickListener(v -> acceptSentInvites());
        findViewById(R.id.teamMockingDeclineSentInvites).setOnClickListener(v -> declineSentInvites());

        findViewById(R.id.teamMockingProposeWalk).setOnClickListener(v -> proposeWalk());
        findViewById(R.id.teamMockingScheduleWalk).setOnClickListener(v -> scheduleWalk());
        findViewById(R.id.teamMockingWithdrawWalk).setOnClickListener(v -> withdrawWalk());
        findViewById(R.id.teamMockingAcceptWalkOne).setOnClickListener(v -> acceptWalkOne());
        findViewById(R.id.teamMockingDeclineWalkTimeOne).setOnClickListener(v -> declineWalkTimeOne());
        findViewById(R.id.teamMockingDeclineWalkRouteAll).setOnClickListener(v -> declineWalkRouteAll());
    }

    public void launchMockingMode() {
        Log.d(TAG, "Switching to mocking mode");

        // Clear existing data
        WWRApplication.getUser().getTeam().setScheduledWalk(null);
        WWRApplication.getUser().getTeam().getMembers().clear();
        WWRApplication.getUser().getTeamRoutes().clear();
        WWRApplication.getUser().getInvites().clear();
        WWRApplication.setDatabase(new DemoDatabaseService());
        TeamData.saveTeamWalkDocId(this, DataConstants.STR_NOT_FOUND);

        Toast.makeText(this, "Now in mocking mode", Toast.LENGTH_SHORT).show();
    }

    public boolean inMockingMode() {
        return WWRApplication.getDatabase() instanceof DemoDatabaseService;
    }

    public void addTeamRoute() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        } else if (WWRApplication.getUser().getTeam().getMembers().size() == 0) {
            Toast.makeText(this, "Please add a team member", Toast.LENGTH_SHORT).show();
            return;
        }

        DemoDatabaseService db = (DemoDatabaseService)WWRApplication.getDatabase();

        // Create route
        int routeId = db.getUniqueId();
        Route route = new UserRoute(routeId, "TeamRoute" + routeId);
        route.setDocID("docId" + routeId);
        route.setStartingPoint("Start" + routeId);
        route.setSteps(100);
        route.setDuration(LocalTime.of(10, 10, 10));
        route.setStartDate(LocalDateTime.of(10, 10, 10, 10, 10));

        // Choose team member
        List<TeamMember> members = WWRApplication.getUser().getTeam().getMembers();
        int memberSeed = (int)(Math.random() * members.size());
        TeamMember member = members.get(memberSeed);
        if (member.getEmail().equals(WWRApplication.getUser().getEmail())) {
            member = members.get((memberSeed + 1) % members.size());
        }

        // Add in route
        TeamRoute changedRoute = new TeamRoute(route, member);
        WWRApplication.getUser().getTeamRoutes().add(changedRoute);

        Toast.makeText(this, "Added route " + route.getName() + " to a random team member",
                Toast.LENGTH_SHORT).show();
    }

    public void receiveInvite() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        }

        TeamMember inviter = new TeamMember("Team Creator", "wwrleader@gmail.com", Color.YELLOW);
        WWRApplication.getUser().getInvites().add(
                new Invite(WWRApplication.getUser().getEmail(), "createdTeam", inviter.getEmail()));
        Toast.makeText(this, "Invite received from wwrleader@gmail.com", Toast.LENGTH_SHORT).show();
    }

    public void acceptSentInvites() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Invite> sentInvites =
                ((DemoDatabaseService)WWRApplication.getDatabase()).getSentInvites();
        Team team = WWRApplication.getUser().getTeam();
        for (Invite inv : sentInvites) {
            team.findMemberById(inv.getInvitedMemberId()).setStatus(TeamMember.STATUS_MEMBER);
        }

        sentInvites.clear();
        Toast.makeText(this, "Accepted all sent invites", Toast.LENGTH_SHORT).show();
    }

    public void declineSentInvites() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Invite> sentInvites =
                ((DemoDatabaseService)WWRApplication.getDatabase()).getSentInvites();
        Team team = WWRApplication.getUser().getTeam();
        for (Invite inv : sentInvites) {
            team.removeMemberById(inv.getInvitedMemberId());
        }

        sentInvites.clear();
        Toast.makeText(this, "Declined all sent invites", Toast.LENGTH_SHORT).show();
    }


    public void proposeWalk() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = WWRApplication.getUser();

        if (user.getRoutes().length() == 0 && user.getTeamRoutes().size() == 0) {
            Toast.makeText(this, "Please create a route", Toast.LENGTH_SHORT).show();
            return;
        } else if (user.getTeam().getScheduledWalk() != null) {
            Toast.makeText(this, "Please remove the existing scheduled walk",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (user.getTeam().getMembers().size() == 0) {
            Toast.makeText(this, "Please add a team member", Toast.LENGTH_SHORT).show();
            return;
        }

        // Choose random route
        Route route;
        if ((int)(Math.random() * 2) == 1 && user.getRoutes().length() > 0) {
            route = user.getRoutes().getRoute((int)(Math.random() * user.getRoutes().length()));
        } else {
            route = user.getTeamRoutes().get((int)(Math.random() * user.getTeamRoutes().size()));
        }

        // Choose random walk creator
        List<TeamMember> members = WWRApplication.getUser().getTeam().getMembers();
        int memberSeed = (int)(Math.random() * members.size());
        TeamMember member = members.get(memberSeed);
        if (member.getEmail().equals(WWRApplication.getUser().getEmail())) {
            member = members.get((memberSeed + 1) % members.size());
        }

        // Create "updated" team
        Team changedTeam = new Team();
        changedTeam.setId(user.getTeam().getId());
        changedTeam.getMembers().addAll(user.getTeam().getMembers());
        changedTeam.setScheduledWalk(
                new ScheduledWalk(route, LocalDateTime.now(), member.getEmail(), changedTeam));

        // Update team
        WWRApplication.getDatabaseListener()
                .updateOnTeamChange(WWRApplication.getDatabase(), changedTeam);
        Toast.makeText(this, "Proposed walk of random route & creator", Toast.LENGTH_SHORT)
                .show();
    }

    public void scheduleWalk() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = WWRApplication.getUser();
        Team team = user.getTeam();
        if (team.getScheduledWalk() == null ||
                team.getScheduledWalk().getStatus() != ScheduledWalk.PROPOSED) {
            Toast.makeText(this, "Please propose a walk", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create "updated" team
        Team changedTeam = new Team();
        changedTeam.setId(team.getId());
        changedTeam.getMembers().addAll(team.getMembers());
        changedTeam.setScheduledWalk(
                new ScheduledWalk(team.getScheduledWalk().getRouteAdapter().toRoute(),
                        team.getScheduledWalk().retrieveScheduledDate(),
                        team.getScheduledWalk().getCreatorId(), changedTeam));
        changedTeam.getScheduledWalk().schedule();

        // Update team
        WWRApplication.getDatabaseListener()
                .updateOnTeamChange(WWRApplication.getDatabase(), changedTeam);
        Toast.makeText(this, "Scheduled the proposed walk", Toast.LENGTH_SHORT).show();
    }

    public void withdrawWalk() {
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        }

        Team team = WWRApplication.getUser().getTeam();
        if (team.getScheduledWalk() == null) {
            Toast.makeText(this, "Please propose or schedule a walk", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create "updated" team
        Team changedTeam = new Team();
        changedTeam.setId(team.getId());
        changedTeam.getMembers().addAll(team.getMembers());
        changedTeam.setScheduledWalk(null);

        // Update team
        WWRApplication.getDatabaseListener()
                .updateOnTeamChange(WWRApplication.getDatabase(), changedTeam);
        Toast.makeText(this, "Withdrew the scheduled walk", Toast.LENGTH_SHORT).show();
    }

    public void acceptWalkOne() {
        Team team = WWRApplication.getUser().getTeam();
        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        } else if (team.getScheduledWalk() == null) {
            Toast.makeText(this, "Please schedule a walk", Toast.LENGTH_SHORT).show();
            return;
        } else if (team.getMembers().size() == 0) {
            Toast.makeText(this, "Please add a team member", Toast.LENGTH_SHORT).show();
            return;
        }

        ScheduledWalk walk = team.getScheduledWalk();
        String memberId = null;

        // Accept walk for some valid member
        for (String key : walk.getResponses().keySet()) {
            if ( ! key.equals(WWRApplication.getUser().getEmail()) &&
                walk.getResponses().get(key) != ScheduledWalk.ACCEPTED) {
                memberId = key;
            }
        }

        // Check for out of bounds
        if (memberId == null) {
            Toast.makeText(this, "All team members have accepted the walk", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Create "updated" team
        Team changedTeam = new Team();
        changedTeam.setId(team.getId());
        changedTeam.getMembers().addAll(team.getMembers());
        changedTeam.setScheduledWalk(
                new ScheduledWalk(team.getScheduledWalk().getRouteAdapter().toRoute(),
                        team.getScheduledWalk().retrieveScheduledDate(),
                        team.getScheduledWalk().getCreatorId(), changedTeam));

        for (Map.Entry<String, Integer> entry : walk.getResponses().entrySet()) {
            changedTeam.getScheduledWalk().getResponses().put(entry.getKey(), entry.getValue());
        }
        changedTeam.getScheduledWalk().accept(memberId);

        // Update team
        WWRApplication.getDatabaseListener()
                .updateOnTeamChange(WWRApplication.getDatabase(), changedTeam);
        Toast.makeText(this, "Accepted walk (" + memberId + ")", Toast.LENGTH_SHORT)
                .show();
    }

    public void declineWalkTimeOne() {
        Team team = WWRApplication.getUser().getTeam();

        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        } else if (WWRApplication.getUser().getTeam().getScheduledWalk() == null) {
            Toast.makeText(this, "Please schedule a walk", Toast.LENGTH_SHORT).show();
            return;
        } else if (WWRApplication.getUser().getTeam().getMembers().size() == 0) {
            Toast.makeText(this, "Please add a team member", Toast.LENGTH_SHORT).show();
            return;
        }

        ScheduledWalk walk = team.getScheduledWalk();
        String memberId = null;

        // Accept walk for some valid member
        for (String key : walk.getResponses().keySet()) {
            if ( ! key.equals(WWRApplication.getUser().getEmail()) &&
                    walk.getResponses().get(key) != ScheduledWalk.DECLINED_BAD_TIME) {
                memberId = key;
            }
        }

        // Check for out of bounds
        if (memberId == null) {
            Toast.makeText(this, "All team members have declined the walk", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Create "updated" team
        Team changedTeam = new Team();
        changedTeam.setId(team.getId());
        changedTeam.getMembers().addAll(team.getMembers());
        changedTeam.setScheduledWalk(
                new ScheduledWalk(team.getScheduledWalk().getRouteAdapter().toRoute(),
                        team.getScheduledWalk().retrieveScheduledDate(),
                        team.getScheduledWalk().getCreatorId(), changedTeam));

        for (Map.Entry<String, Integer> entry : walk.getResponses().entrySet()) {
            changedTeam.getScheduledWalk().getResponses().put(entry.getKey(), entry.getValue());
        }
        changedTeam.getScheduledWalk().declineBadTime(memberId);

        // Update team
        WWRApplication.getDatabaseListener()
                .updateOnTeamChange(WWRApplication.getDatabase(), changedTeam);
        Toast.makeText(this, "Declined walk for bad time (" + memberId + ")",
                Toast.LENGTH_SHORT).show();
    }

    public void declineWalkRouteAll() {
        Team team = WWRApplication.getUser().getTeam();

        if ( ! inMockingMode()) {
            Toast.makeText(this, "Please enter mocking mode", Toast.LENGTH_SHORT).show();
            return;
        } else if (WWRApplication.getUser().getTeam().getScheduledWalk() == null) {
            Toast.makeText(this, "Please schedule a walk", Toast.LENGTH_SHORT).show();
            return;
        } else if (WWRApplication.getUser().getTeam().getMembers().size() == 0) {
            Toast.makeText(this, "Please add a team member", Toast.LENGTH_SHORT).show();
            return;
        }

        ScheduledWalk walk = team.getScheduledWalk();

        // Create "updated" team
        Team changedTeam = new Team();
        changedTeam.setId(team.getId());
        changedTeam.getMembers().addAll(team.getMembers());
        changedTeam.setScheduledWalk(
                new ScheduledWalk(walk.getRouteAdapter().toRoute(), walk.retrieveScheduledDate(),
                        walk.getCreatorId(), changedTeam));

        for (Map.Entry<String, Integer> entry : walk.getResponses().entrySet()) {
            changedTeam.getScheduledWalk().getResponses().put(entry.getKey(), entry.getValue());
        }

        for (TeamMember member : changedTeam.getMembers()) {
            if ( ! member.getEmail().equals(walk.getCreatorId()) &&
                 ! member.getEmail().equals(WWRApplication.getUser().getEmail())) {
                changedTeam.getScheduledWalk().declineBadRoute(member.getEmail());
            }
        }

        // Update team
        WWRApplication.getDatabaseListener()
                .updateOnTeamChange(WWRApplication.getDatabase(), changedTeam);
        Toast.makeText(this, "Declined walk for bad route (all)", Toast.LENGTH_SHORT).show();
    }
}
