package com.example.cse110_project.database.data_mediators;

import android.util.Log;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseServiceObserver;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.ScheduledWalk;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.User;

import java.util.List;

public class DatabaseListener implements DatabaseServiceObserver {
    private static final String TAG = DatabaseListener.class.getSimpleName();
    private User user;

    public DatabaseListener(User user, DatabaseService db) {
        this.user = user;
        db.register(this);
    }

    @Override
    public void updateOnInvitesChange(DatabaseService db, List<Invite> invites) {
        if (invites == null) {
            Log.e(TAG, "Invites data not found");
            return;
        }

        List<Invite> userInvites = user.getInvites();
        for (Invite inv : invites) {
            if ( ! userInvites.contains(inv)) {
                userInvites.add(inv);
            }
        }
    }

    @Override
    public void updateOnTeamRoutesChange(DatabaseService db, List<TeamRoute> teamRoutes) {
        if (teamRoutes == null) {
            Log.e(TAG, "Team routes data not found");
            return;
        }

        List<TeamRoute> userTeamRoutes = user.getTeamRoutes();
        for (TeamRoute route : teamRoutes) {
            if ( ! userTeamRoutes.contains(route)) {
                userTeamRoutes.add(route);
            }
        }
    }

    @Override
    public void updateOnTeamChange(DatabaseService db, Team team) {
        if (team == null) {
            Log.e(TAG, "Team data not found");
            return;
        }

        addNewTeamMembers(db, team.getMembers());
        removeDeclinedTeamMembers(team);
        updateScheduledWalk(team);
    }

    private void addNewTeamMembers(DatabaseService db, List<TeamMember> members) {
        Team userTeam = user.getTeam();

        for (TeamMember member : members) {
            TeamMember prevMember = userTeam.findMemberById(member.getEmail());
            if (prevMember == null) {
                userTeam.getMembers().add(member);
                Log.d(TAG, "Adding new teammate with status " + member.getStatus());
                if (member.getStatus() == TeamMember.STATUS_MEMBER) {
                    db.addTeammateRoutesListener(WWRApplication.getUser(), member);
                }

            } else if (prevMember.getStatus() == TeamMember.STATUS_PENDING &&
                    member.getStatus() == TeamMember.STATUS_MEMBER) {
                prevMember.setStatus(TeamMember.STATUS_MEMBER);
                db.addTeammateRoutesListener(WWRApplication.getUser(), member);
            }
        }
    }

    private void removeDeclinedTeamMembers(Team team) {
        Team userTeam = user.getTeam();
        for (TeamMember member : userTeam.getMembers()) {
            if (team.findMemberById(member.getEmail()) == null) {
                userTeam.removeMemberById(member.getEmail());
            }
        }
    }

    private void updateScheduledWalk(Team team) {
        Team userTeam = user.getTeam();
        ScheduledWalk prevWalk = userTeam.getScheduledWalk();
        ScheduledWalk nextWalk = team.getScheduledWalk();

        if (prevWalk == null && nextWalk != null) {
            WWRApplication.getNotifier().notifyOnWalkProposed(nextWalk);

        } else if (prevWalk != null && nextWalk == null) {
            WWRApplication.getNotifier().notifyOnWalkWithdrawn(prevWalk);

        } else if (prevWalk != null) {
            if (prevWalk.getStatus() != nextWalk.getStatus()) {
                WWRApplication.getNotifier().notifyOnWalkScheduled(nextWalk);
            } else if ( ! prevWalk.getResponses().equals(nextWalk.getResponses())){
                WWRApplication.getNotifier().notifyOnWalkResponseChange(prevWalk, nextWalk);
            }
        }

        userTeam.setScheduledWalk(nextWalk);
        for (TeamMember member : userTeam.getMembers()) {
            if (userTeam.getScheduledWalk() != null &&
                    ! member.getEmail().equals(nextWalk.getCreatorId()) &&
                    ! userTeam.getScheduledWalk().getResponses().containsKey(member.getEmail())) {
                userTeam.getScheduledWalk().getResponses()
                        .put(member.getEmail(), ScheduledWalk.NO_RESPONSE);
            }
        }
    }
}
