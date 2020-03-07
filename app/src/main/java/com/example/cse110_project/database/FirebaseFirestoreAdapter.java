package com.example.cse110_project.database;


import android.util.Log;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.team.Team;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class FirebaseFirestoreAdapter implements DatabaseService {
    private static final String TAG = "FirebaseFirestoreAdapter";

    private String userCollectionKey;
    private String teamCollectionKey;
    private String invitesKey;
    private String routesKey;

    private CollectionReference userRoutes;

    public FirebaseFirestoreAdapter(String userCollectionKey, String teamCollectionKey,
                                    String userId, String invitesKey, String routesKey) {
        this.userCollectionKey = userCollectionKey;
        this.teamCollectionKey = teamCollectionKey;
        this.invitesKey = invitesKey;
        this.routesKey = routesKey;

        userRoutes = FirebaseFirestore.getInstance().collection(userCollectionKey)
                .document(userId).collection(routesKey);
        FirebaseAuth.getInstance().signInAnonymously();
    }

    @Override
    public void addRoute(Route route) {
        Log.d(TAG, "Adding route " + route.getName());
        userRoutes.add(new RouteFirebaseAdapter(route))
                .addOnSuccessListener(doc -> {
                    route.setDocID(doc.getId());
                    userRoutes.document(doc.getId()).update("docID", route.getDocID());
                    RouteData.saveRouteDocId(WWRApplication.getUser().getContext(), route.getID(),
                                             route.getDocID());
                    Log.d(TAG, route.getName() + " given doc id " + route.getDocID());
                });
    }

    @Override
    public void updateRoute(Route route) {
        Log.d(TAG, "Updating route " + route.getName() + " at " + route.getDocID());
        userRoutes.document(route.getDocID()).set(new RouteFirebaseAdapter(route));
    }

    @Override
    public void getRoutes(List<Route> routes) {
        userRoutes.get().addOnSuccessListener(result -> {
            for (DocumentSnapshot doc : result.getDocuments()) {
                routes.add(doc.toObject(RouteFirebaseAdapter.class).toRoute());
            }
        });
    }

    @Override
    public void addInvite(Invite invite) {
        CollectionReference invitesCollection = FirebaseFirestore.getInstance()
                .collection(userCollectionKey)
                .document(invite.getInvitedMemberId())
                .collection(invitesKey);
        invitesCollection.document(invite.getTeamId()).set(invite);
    }

    // Shared helper method for declineInvite, acceptInvite
    private void removeInvite(Invite invite) {
        CollectionReference invitesCollection = FirebaseFirestore.getInstance()
                .collection(userCollectionKey)
                .document(invite.getInvitedMemberId())
                .collection(invitesKey);
        invitesCollection.document(invite.getTeamId()).delete();
    }

    @Override
    public void declineInvite(Invite invite) {
        removeInvite(invite);

        // Remove invited member from team
        FirebaseFirestore.getInstance().collection(teamCollectionKey)
                .document(invite.getTeamId()).get().addOnSuccessListener(doc -> {
            Log.d(TAG, "declineInvite: removing declined member for invite " + invite);
            Team team = doc.toObject(Team.class);
            if (team != null) {
                team.removeMemberById(invite.getInvitedMemberId());
                updateTeam(team);
            }
        });
    }

    @Override
    public void acceptInvite(Invite invite) {
        removeInvite(invite);

        // Update invited member's status
        FirebaseFirestore.getInstance().collection(teamCollectionKey)
                .document(invite.getTeamId()).get().addOnSuccessListener(doc -> {
            Log.d(TAG, "acceptInvite: updating member status for invite " + invite);
            Team team = doc.toObject(Team.class);
            if (team != null) {
                team.findMemberById(invite.getInvitedMemberId())
                        .setStatus(TeamMember.STATUS_MEMBER);
                updateTeam(team);
            }
        });
    }

    @Override
    public Task<?> createTeam(Team team) {
        Log.d(TAG, "Creating team " + team);
        DocumentReference userTeam =
                FirebaseFirestore.getInstance().collection(teamCollectionKey).document();
        team.setId(userTeam.getId());
        return userTeam.set(team);
    }

    @Override
    public void removeTeam(Team team) {
        FirebaseFirestore.getInstance().collection(teamCollectionKey).document(team.getId())
                .delete();
    }

    @Override
    public Task<?> updateTeam(Team team) {
        return FirebaseFirestore.getInstance().collection(teamCollectionKey)
                .document(team.getId()).set(team);
    }

    @Override
    public ListenerRegistration addTeamListener(Team team) {
        Log.d(TAG, "addTeamListener called on " + team.getId());
        return FirebaseFirestore.getInstance().collection(teamCollectionKey)
                .document(team.getId())
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen error in addTeamListener: " + e.getMessage());
                        addTeamListener(team);
                        return;
                    }

                    Team changedTeam = snapshot.toObject(Team.class);
                    Log.d(TAG, "Change retrieved in " + changedTeam);

                    if (changedTeam != null) {
                        addNewTeammates(team, changedTeam);
                        removeDeclinedTeammates(team, changedTeam);
                        team.setScheduledWalk(changedTeam.getScheduledWalk());
                    }
                });
    }

    private void addNewTeammates(Team prev, Team next) {
        for (TeamMember member : next.getMembers()) {
            if (prev.findMemberById(member.getEmail()) == null) {
                prev.getMembers().add(member);
                addTeammateRoutesListener(WWRApplication.getUser(), member);
            }
        }
    }

    private void removeDeclinedTeammates(Team prev, Team next) {
        for (TeamMember member : prev.getMembers()) {
            if (next.findMemberById(member.getEmail()) == null) {
                prev.removeMemberById(member.getEmail());
            }
        }
    }

    @Override
    public void removeTeammatesListener(ListenerRegistration listener) {
        listener.remove();
    }

    @Override
    public void addInvitesListener(User listener) {
        Log.d(TAG, "Adding invites listener " + listener.getEmail());
        FirebaseFirestore.getInstance().collection(userCollectionKey)
                .document(listener.getEmail())
                .collection(invitesKey)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen error in addInvitesListener: " + e.getMessage());
                        addInvitesListener(listener);
                        return;
                    }

                    Log.d(TAG, "Change retrieved in invites for " + listener.getEmail());
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        if (dc.getType().equals(DocumentChange.Type.ADDED)) {
                            listener.getInvites().add(dc.getDocument().toObject(Invite.class));
                        }
                    }
                    Log.d(TAG, "Updated invites is " + listener.getInvites());
                });
    }

    // Code based off of
    // https://firebase.google.com/docs/firestore/query-data/listen#view_changes_between_snapshots
    @Override
    public void addTeammateRoutesListener(User listener, TeamMember teammate) {
        Log.d(TAG, "Adding routes listener " + listener.getEmail() + " to member " + teammate);
        FirebaseFirestore.getInstance().collection(userCollectionKey)
                .document(teammate.getEmail())
                .collection(routesKey)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen error in addTeammateRoutesListener: "
                            + e.getMessage());
                        addTeammateRoutesListener(listener, teammate);
                        return;
                    }

                    Log.d(TAG, "Change retrieved in teammate routes for " + teammate);

                    // Update any changed routes
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        TeamRoute changedRoute = new TeamRoute(
                                dc.getDocument().toObject(RouteFirebaseAdapter.class).toRoute(),
                                teammate);
                        Log.d(TAG, "Change found in route " + changedRoute.getName() +
                                " with docId " + changedRoute.getDocID());

                        List<TeamRoute> teamRoutes = listener.getTeamRoutes();
                        if (changedRoute.getDocID() != null && ! teamRoutes.contains(changedRoute)) {
                            teamRoutes.add(changedRoute);
                        }
                    }
                });
    }
}
