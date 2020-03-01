package com.example.cse110_project.database;


import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.Team;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseFirestoreAdapter implements DatabaseService {
    private String userCollectionKey;
    private String teamCollectionKey;
    private String routesKey;

    private CollectionReference userRoutes;

    public FirebaseFirestoreAdapter(String userCollectionKey, String teamCollectionKey,
                                    String userId, String routesKey) {
        this.userCollectionKey = userCollectionKey;
        this.teamCollectionKey = teamCollectionKey;
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
    public List<Route> getRoutes() {
        List<Route> routes = new ArrayList<>();
        userRoutes.get().addOnSuccessListener(result -> {
            for (DocumentSnapshot doc : result.getDocuments()) {
                routes.add(doc.toObject(UserRoute.class));
            }
        });

        return routes;
    }

    @Override
    public void createTeam(Team team) {
        DocumentReference userTeam =
                FirebaseFirestore.getInstance().collection(teamCollectionKey).document();
        team.setId(userTeam.getId());
        userTeam.set(team.getMembers());
    }

    @Override
    public void updateTeam(Team team) {
        FirebaseFirestore.getInstance().collection(teamCollectionKey)
                .document(team.getId()).set(team.getMembers());
    }

    @Override
    public List<TeamRoute> getTeamRoutes(String memberId) {
        List<TeamRoute> routes = new ArrayList<>();

        FirebaseFirestore.getInstance().collection(teamCollectionKey).document(memberId)
                .collection(routesKey).get().addOnSuccessListener(result -> {
            for (DocumentSnapshot doc : result.getDocuments()) {
                routes.add(new TeamRoute(doc.toObject(UserRoute.class), memberId));
            }
        });

        return routes;
    }
}
