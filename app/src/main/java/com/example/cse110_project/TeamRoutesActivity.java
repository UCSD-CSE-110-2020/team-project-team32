package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;


import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.util.RouteListAdapter;
import com.example.cse110_project.util.TeamRouteAdapter;

import java.util.List;

public class TeamRoutesActivity extends AppCompatActivity {
    private final static String TAG = "TeamRoutesActivity";

    private List<TeamRoute> getAllRoutes;
    private String[] nameArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_routes);

        // To other activities
        Button homeButton = findViewById(R.id.routesHomeButton);
        homeButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchTeamRoutesData();

        // Set up list view
        TeamRouteAdapter adapter = new TeamRouteAdapter(this, nameArray, getAllRoutes);

        ListView listView = findViewById(R.id.teamRoutesListView);
        listView.setAdapter(adapter);
    }

    public void fetchTeamRoutesData() {
        Log.d(TAG, "Fetching routes data");
        //WWRApplication.getUser().refreshTeamRoutes();
        getAllRoutes = WWRApplication.getUser().getTeamRoutes();
        int arrLen = getAllRoutes.size();
        nameArray = new String[arrLen];
        System.out.println("THISSSS" + getAllRoutes.get(0).isFavorite() + getAllRoutes.get(1).isFavorite() + getAllRoutes.get(2).isFavorite()
        +getAllRoutes.get(3).isFavorite());

        if (getAllRoutes.size() != 0) {
            for (int i = 0; i < getAllRoutes.size(); i++) {
                nameArray[i] = getAllRoutes.get(i).getName();
            }

        }
    }
}
