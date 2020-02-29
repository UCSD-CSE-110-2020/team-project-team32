package com.example.cse110_project.database;


import com.example.cse110_project.user_routes.Route;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseFirestoreAdapter implements DatabaseService {
    private CollectionReference userRoutes;

    public FirebaseFirestoreAdapter(String collectionKey, String userId, String routesKey) {
        userRoutes = FirebaseFirestore.getInstance().collection(collectionKey)
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
}
