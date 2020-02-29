package com.example.cse110_project.database;

import com.example.cse110_project.user_routes.Route;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Map;

public interface DatabaseService {
    public void addRoute(Route route);

    public void updateRoute(Route route);

    public List<Route> getRoutes();
}
