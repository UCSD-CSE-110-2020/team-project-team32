package com.example.cse110_project.database;


import android.util.Log;

import com.example.cse110_project.team.Invite;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.team.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void addRoute(UserRoute route) {
        userRoutes.add(route).addOnSuccessListener(doc -> route.setDocID(doc.getId()));
    }

    @Override
    public void updateRoute(UserRoute route) {
        userRoutes.document(route.getDocID()).set(route);
    }

    @Override
    public void getRoutes(List<Route> routes) {
        userRoutes.get().addOnSuccessListener(result -> {
            for (DocumentSnapshot doc : result.getDocuments()) {
                routes.add(doc.toObject(UserRoute.class));
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

    @Override
    public void removeInvite(Invite invite) {
        CollectionReference invitesCollection = FirebaseFirestore.getInstance()
                .collection(userCollectionKey)
                .document(invite.getInvitedMemberId())
                .collection(invitesKey);
        invitesCollection.document(invite.getTeamId()).delete();
    }

    @Override
    public void getInvites(String memberId, List<Invite> invites) {
        FirebaseFirestore.getInstance().collection(userCollectionKey).document(memberId)
                .collection(invitesKey).get().addOnSuccessListener(result -> {
                    for (DocumentSnapshot doc : result) {
                        invites.add(doc.toObject(Invite.class));
                    }
                });
    }

    @Override
    public void createTeam(Team team) {
        DocumentReference userTeam =
                FirebaseFirestore.getInstance().collection(teamCollectionKey).document();
        team.setId(userTeam.getId());
        userTeam.set(team);
    }

    @Override
    public void removeTeam(Team team) {
        FirebaseFirestore.getInstance().collection(teamCollectionKey).document(team.getId())
                .delete();
    }

    @Override
    public void updateTeam(Team team) {
        FirebaseFirestore.getInstance().collection(teamCollectionKey).document(team.getId())
                .set(team);
    }

    @Override
    public void getTeamMembers(Team team) {
        Log.d(TAG, "addSavedTeamMembers called on " + team.getId());
        FirebaseFirestore.getInstance().collection(teamCollectionKey).document(team.getId())
                .get().addOnSuccessListener(result -> {
                    Team storedTeam = result.toObject(Team.class);
                    Log.d(TAG, "Retrieved team " + storedTeam);
                    team.getMembers().addAll(storedTeam.getMembers());
                });
    }


    @Override
    public void getRoutesByUser(String userId, List<TeamRoute> routes) {
        FirebaseFirestore.getInstance().collection(teamCollectionKey).document(userId)
                .collection(routesKey).get().addOnSuccessListener(result -> {
            for (DocumentSnapshot doc : result.getDocuments()) {
                routes.add(new TeamRoute(doc.toObject(UserRoute.class), userId));
            }
        });
    }
}
