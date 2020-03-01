package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;
import com.example.cse110_project.user_routes.TeamRoute;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;
import com.example.cse110_project.util.RouteListAdapter;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class TeamRoutesActivity extends AppCompatActivity {
    private final static String TAG = "TeamRoutesActivity";
    private final static String MONTH_DAY_SEPARATOR = " ";
    private User user;

    private List<TeamRoute> currentList = null;
    private List<TeamMember> memberList;

    private String[] nameArray;
    private String[] startPtArray;
    private String[] stepsArray;
    private String[] milesArray;
    private String[] timeArray;
    private String[] dateArray;
    private String[] flatHillyArray;
    private String[] loopOutBackArray;
    private String[] streetsTrailArray;
    private String[] evenUnevenArray;
    private String[] difficultyArray;
    private String[] favArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_routes);
        //int arrLen = 0;
        user = WWRApplication.getUser();
        memberList = user.getTeam().getMembers(); // list of TeamMember objects


        //teamList = WWRApplication.getDatabase().getTeamRoutes(thisTeam.get(0).getEmail());
        /*for (int i = 0; i < memberList.size(); i++ ) {
            List<TeamRoute> tempList;
            tempList = WWRApplication.getDatabase().getTeamRoutes(memberList.get(i).getEmail());

            for (int j = 0; j < tempList.size(); j++) {
                currentList.add(tempList.get(i));
            }
        }*/

        int arrLen = user.getRoutes().length();
        //int arrLen = currentList.size();
        nameArray = new String[arrLen];
        startPtArray = new String[arrLen];
        stepsArray = new String[arrLen];
        milesArray = new String[arrLen];
        timeArray = new String[arrLen];
        dateArray = new String[arrLen];
        flatHillyArray = new String[arrLen];
        loopOutBackArray = new String[arrLen];
        streetsTrailArray = new String[arrLen];
        evenUnevenArray = new String[arrLen];
        difficultyArray = new String[arrLen];
        favArray = new String[arrLen];

        // To other activities
        Button homeButton = findViewById(R.id.routesHomeButton);
        homeButton.setOnClickListener(v -> finish());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchRoutesData();

        // Set up list view
        RouteListAdapter adapter = new RouteListAdapter(this, nameArray, startPtArray,
                stepsArray, milesArray, timeArray, dateArray, flatHillyArray, streetsTrailArray,
                loopOutBackArray, evenUnevenArray, difficultyArray, favArray);

        ListView listView = findViewById(R.id.listviewID);
        listView.setAdapter(adapter);
    }

    public void fetchTeamRoutesData() {
        Log.d(TAG, "Fetching routes data");

        //RouteList routesInTeam = teamList
        for (int i = 0; i < currentList.size(); i++) {
            Route r = currentList.get(i).getRoute();

            nameArray[i] = r.getName();
            startPtArray[i] = r.getStartingPoint();
            stepsArray[i] = String.valueOf(r.getSteps());
            milesArray[i] = MilesCalculator.formatMiles(r.getMiles(user.getHeight()));
            flatHillyArray[i] = r.getFlatVsHilly();
            loopOutBackArray[i] = r.getLoopVsOutBack();
            streetsTrailArray[i] = r.getStreetsVsTrail();
            evenUnevenArray[i] = r.getEvenVsUneven();
            difficultyArray[i] = r.getDifficulty();
            favArray[i] = r.isFavorite() ? Route.FAV : Route.NO_DATA;

            if (r.getStartDate() != null) {
                timeArray[i] = r.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
                dateArray[i] = r.getStartDate().getMonth() + MONTH_DAY_SEPARATOR
                        + r.getStartDate().getDayOfMonth();
            }
        }
    }

    public void fetchRoutesData(){
        Log.d(TAG, "Fetching routes data");

        RouteList routes = user.getRoutes();

        routes.sortByName();
        for (int i = 0; i < routes.length(); i++) {
            Route r = routes.getRoute(i);
            nameArray[i] = r.getName();
            startPtArray[i] = r.getStartingPoint();
            stepsArray[i] = String.valueOf(r.getSteps());
            milesArray[i] = MilesCalculator.formatMiles(r.getMiles(user.getHeight()));
            flatHillyArray[i] = r.getFlatVsHilly();
            loopOutBackArray[i] = r.getLoopVsOutBack();
            streetsTrailArray[i] = r.getStreetsVsTrail();
            evenUnevenArray[i] = r.getEvenVsUneven();
            difficultyArray[i] = r.getDifficulty();
            favArray[i] = r.isFavorite() ? Route.FAV : Route.NO_DATA;

            if (r.getStartDate() != null) {
                timeArray[i] = r.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
                dateArray[i] = r.getStartDate().getMonth() + MONTH_DAY_SEPARATOR
                        + r.getStartDate().getDayOfMonth();
            }
        }
    }
}
