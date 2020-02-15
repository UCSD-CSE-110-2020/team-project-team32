package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;
import com.example.cse110_project.trackers.CurrentFitnessTracker;
import com.example.cse110_project.trackers.CurrentTimeTracker;
import com.example.cse110_project.trackers.CurrentWalkTracker;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;

import java.time.LocalTime;


public class WalkActivity extends AppCompatActivity {
    public final static String SAVED_ROUTE_KEY = "SAVED_ROUTE_KEY";
    public final static String SAVED_ROUTE_ID_KEY = "SAVED_ROUTE_ID";

    private FitnessService fitnessService;
    private boolean fitnessServiceActive;
    private boolean onSavedRoute;
    private int savedRouteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        // Handle case of walking an existing route
        onSavedRoute = getIntent() != null &&
                getIntent().getBooleanExtra(SAVED_ROUTE_KEY, false);
        if (onSavedRoute) {
            savedRouteID = getIntent().getIntExtra(SAVED_ROUTE_ID_KEY, -1);
            displayRouteSummary();
        }

        Button mockingButton = findViewById(R.id.walkMockingButton);
        mockingButton.setOnClickListener(v -> launchMockingActivity());

        // Buttons to end activity
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> finish());

        Button stopButton = findViewById(R.id.stopWalkButton);
        stopButton.setOnClickListener(v -> {
            endWalkActivity(CurrentTimeTracker.getTime());
            showSaveDialog();
        });

        setUpFitnessService();
    }

    private void displayRouteSummary() {
        Route route = User.getRoutes(this).getRouteByID(savedRouteID);
        ((TextView)findViewById(R.id.walkRouteName)).setText(route.getName());
        ((TextView)findViewById(R.id.walkStartingPoint)).setText(route.getStartingPoint());
        ((TextView)findViewById(R.id.walkNotes)).setText(route.getNotes());
    }

    private void setUpFitnessService() {
        if (CurrentFitnessTracker.hasFitnessService()) {
            fitnessService = CurrentFitnessTracker.getFitnessService();
            fitnessServiceActive = true;
        } else {
            String fitnessServiceKey = getIntent().getStringExtra(MainActivity.FITNESS_SERVICE_KEY);
            System.out.println("Walk service key: " + fitnessServiceKey);
            fitnessServiceActive = (fitnessServiceKey != null);
            if (fitnessServiceActive) {
                fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
                fitnessService.setup();
            }
        }
    }


    public void launchMockingActivity() {
        Intent intent = new Intent(this, MockingActivity.class);
        startActivity(intent);
    }

    public void endWalkActivity(LocalTime finalTime) {
        System.out.println("Walk activity ended - " + fitnessServiceActive);
        if (fitnessServiceActive) {
            fitnessService.updateStepCount();
        }
        CurrentWalkTracker.setFinalSteps(User.getSteps());
        CurrentWalkTracker.setFinalTime(finalTime);
    }


    public void updateSavedRoute() {
        if (onSavedRoute) {
            User.getRoutes(this)
                    .updateRouteData(this, savedRouteID, CurrentWalkTracker.getWalkSteps(),
                    CurrentWalkTracker.getWalkTime(), CurrentWalkTracker.getWalkDate());
        }
    }


    public AlertDialog showSaveDialog () {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WalkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_save, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalkActivity.this)
                .setCancelable(false)
                .setPositiveButton(R.string.saveButton, null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);

        cancelButton.setOnClickListener(v -> alert.dismiss());
        submitButton.setOnClickListener(v -> {
            alert.dismiss();

            // Only create new SaveRoute if not on an already saved route
            if (onSavedRoute) {
                updateSavedRoute();
                finish();
            } else {
                (new SaveRoute(this, this, CurrentWalkTracker.getWalkSteps(),
                        CurrentWalkTracker.getWalkTime(), CurrentWalkTracker.getWalkDate()))
                        .inputRouteDataDialog();
            }
        });

        return alert;
    }
}
