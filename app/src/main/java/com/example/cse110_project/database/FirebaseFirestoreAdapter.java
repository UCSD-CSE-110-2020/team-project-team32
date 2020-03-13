package com.example.cse110_project.database;


import android.util.Log;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.ScheduledWalk;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.RouteFirebaseAdapter;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.team.Team;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseFirestoreAdapter implements DatabaseService {
    private static final String TAG = FirebaseFirestoreAdapter.class.getSimpleName();

    private List<DatabaseServiceObserver> observers;
    private String invitesKey;
    private String routesKey;

    private CollectionReference userCollection;
    private CollectionReference teamCollection;
    private CollectionReference userRoutes;

    public FirebaseFirestoreAdapter(String userCollectionKey, String teamCollectionKey,
                                    String userId, String invitesKey, String routesKey) {
        observers = new ArrayList<>();
        this.invitesKey = invitesKey;
        this.routesKey = routesKey;

        userCollection = FirebaseFirestore.getInstance().collection(userCollectionKey);
        teamCollection = FirebaseFirestore.getInstance().collection(teamCollectionKey);
        userRoutes = userCollection.document(userId).collection(routesKey);

        FirebaseAuth.getInstance().signInAnonymously();
    }

    @Override
    public void register(DatabaseServiceObserver obs) {
        observers.add(obs);
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
    public void addInvite(Invite invite) {
        CollectionReference invitesCollection = userCollection
                .document(invite.getInvitedMemberId())
                .collection(invitesKey);
        invitesCollection.document(invite.getTeamId()).set(invite);
    }

    // Shared helper method for declineInvite, acceptInvite
    private void removeInvite(Invite invite) {
        CollectionReference invitesCollection = userCollection
                .document(invite.getInvitedMemberId())
                .collection(invitesKey);
        invitesCollection.document(invite.getTeamId()).delete();
    }

    @Override
    public void declineInvite(Invite invite) {
        removeInvite(invite);
        teamCollection.document(invite.getTeamId()).get().addOnSuccessListener(doc -> {
            Log.d(TAG, "declineInvite: removing declined member for invite " + invite);
            Team team = doc.toObject(Team.class);
            for (DatabaseServiceObserver obs : observers) {
                obs.updateOnInviteDeclined(this, invite, team);
            }
        });
    }

    @Override
    public void acceptInvite(Invite invite) {
        removeInvite(invite);
        teamCollection.document(invite.getTeamId()).get().addOnSuccessListener(doc -> {
            Log.d(TAG, "acceptInvite: updating member status for invite " + invite);
            Team team = doc.toObject(Team.class);
            for (DatabaseServiceObserver obs : observers) {
                obs.updateOnInviteAccepted(this, invite, team);
            }
        });
    }

    @Override
    public void createTeam(Team team) {
        Log.d(TAG, "Creating team " + team);
        DocumentReference userTeam = teamCollection.document();
        team.setId(userTeam.getId());
        userTeam.set(team);
    }

    @Override
    public void updateTeam(Team team) {
        Task<?> task = teamCollection.document(team.getId()).set(team);
        task.addOnSuccessListener(r -> Log.d(TAG, "updateTeam: Success"))
                .addOnFailureListener(r -> Log.d(TAG, "updateTeam: Failure"))
                .addOnCanceledListener(() -> Log.d(TAG, "updateTeam: Canceled"));
    }

    @Override
    public void addTeamListener(Team team) {
        Log.d(TAG, "addTeamListener called on " + team.getId());
        teamCollection.document(team.getId()).addSnapshotListener((snapshot, e) -> {
                if (e != null) {
                    Log.w(TAG, "Listen error in addTeamListener: " + e.getMessage());
                    addTeamListener(team);
                    return;
                }

                Team changedTeam = snapshot.toObject(Team.class);
                Log.d(TAG, "Change retrieved in " + changedTeam);
                for (DatabaseServiceObserver obs : observers) {
                    obs.updateOnTeamChange(this, changedTeam);
                }
        });
    }

    @Override
    public void addInvitesListener(User listener) {
        Log.d(TAG, "Adding invites listener " + listener.getEmail());
        userCollection.document(listener.getEmail()).collection(invitesKey)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen error in addInvitesListener: " + e.getMessage());
                        addInvitesListener(listener);
                        return;
                    }

                    Log.d(TAG, "Change retrieved in invites for " + listener.getEmail());
                    List<Invite> changedInvites = new ArrayList<>();
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        if (dc.getType().equals(DocumentChange.Type.ADDED)) {
                            changedInvites.add(dc.getDocument().toObject(Invite.class));
                        }
                    }

                    for (DatabaseServiceObserver obs : observers) {
                        obs.updateOnInvitesChange(this, changedInvites);
                    }
                    Log.d(TAG, "Updated invites is " + listener.getInvites());
                });
    }


    // https://firebase.google.com/docs/firestore/query-data/listen#view_changes_between_snapshots
    @Override
    public void addTeammateRoutesListener(User listener, TeamMember teammate) {
        Log.d(TAG, "Adding routes listener " + listener.getEmail() + " to member " + teammate);
        userCollection.document(teammate.getEmail()).collection(routesKey)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen error in addTeammateRoutesListener: "
                            + e.getMessage());
                        addTeammateRoutesListener(listener, teammate);
                        return;
                    }

                    Log.d(TAG, "Change retrieved in teammate routes for " + teammate);
                    List<TeamRoute> changedRoutes = new ArrayList<>();
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        TeamRoute changedRoute = new TeamRoute(
                                dc.getDocument().toObject(RouteFirebaseAdapter.class).toRoute(),
                                teammate);
                        changedRoutes.add(changedRoute);
                        Log.d(TAG, "Change found in route " + changedRoute.getName() +
                                " with docId " + changedRoute.getDocID());
                    }

                    for (DatabaseServiceObserver obs : observers) {
                        obs.updateOnTeamRoutesChange(this, changedRoutes);
                    }
                });
    }
}
