package com.example.cse110_project.database;


import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseFirestoreAdapter implements DatabaseService {
    private CollectionReference userRoutes;
    private CollectionReference teams;

    public FirebaseFirestoreAdapter(String userCollectionKey, String teamCollectionKey,
                                    String userId, String routesKey) {
        teams = FirebaseFirestore.getInstance().collection(teamCollectionKey);
        userRoutes = FirebaseFirestore.getInstance().collection(userCollectionKey)
                .document(userId).collection(routesKey);
        FirebaseAuth.getInstance().signInAnonymously();
    }

    @Override
    public void addRoute(Route route) {
        userRoutes.add(route).addOnSuccessListener(doc -> route.setDocID(doc.getId()));
    }

    @Override
    public void updateRoute(Route route) {
        userRoutes.document(route.getDocID()).set(route);
    }

    @Override
    public List<Route> getRoutes() {
        List<Route> routes = new ArrayList<>();
        userRoutes.get().addOnSuccessListener(result -> {
            for (DocumentSnapshot doc : result.getDocuments()) {
                routes.add(doc.toObject(Route.class));
            }
        });

        return routes;
    }

    @Override
    public void createTeam(Team team) {
        DocumentReference userTeam = teams.document();
        team.setId(userTeam.getId());
        userTeam.set(team.getMembers());
    }

    @Override
    public void updateTeam(Team team) {
        teams.document(team.getId()).set(team.getMembers());
    }

    @Override
    public List<TeamMember> getTeam() {
        return null;
    }
}
