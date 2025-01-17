package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;


import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.list_adapters.TeamRouteListAdapter;

import java.util.List;

public class TeamRoutesActivity extends AppCompatActivity {
    private final static String TAG = "TeamRoutesActivity";

    private List<TeamRoute> teamRoutes;
    private String[] nameArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_routes);

        // To other activities
        Button homeButton = findViewById(R.id.teamRoutesBackButton);
        homeButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchTeamRoutesData();

        // Set up list view
        TeamRouteListAdapter adapter = new TeamRouteListAdapter(this, nameArray, teamRoutes);
        ListView listView = findViewById(R.id.teamRoutesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, TeamRoutesDetailsActivity.class);
            intent.putExtra(RouteDetailsActivity.ROUTE_INDEX_KEY, position);
            startActivity(intent);
        });
    }

    public void fetchTeamRoutesData() {
        teamRoutes = WWRApplication.getUser().getTeamRoutes();
        Log.d(TAG, "Fetching data for " + teamRoutes.size() + " routes: " + teamRoutes);

        int arrLen = teamRoutes.size();
        nameArray = new String[arrLen];
        if (teamRoutes.size() != 0) {
            for (int i = 0; i < teamRoutes.size(); i++) {
                nameArray[i] = teamRoutes.get(i).getName();
            }
        }
    }
}
