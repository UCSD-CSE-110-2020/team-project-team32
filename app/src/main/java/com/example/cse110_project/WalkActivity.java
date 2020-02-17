package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class WalkActivity extends AppCompatActivity {
    public final static String SAVED_ROUTE_KEY = "SAVED_ROUTE_KEY";
    public final static String SAVED_ROUTE_ID_KEY = "SAVED_ROUTE_ID";
    private final static String TAG = "WalkActivity";

    private User user;
    private FitnessService fitnessService;
    private boolean fitnessServiceActive;
    private boolean onSavedRoute;
    private int savedRouteID;

    private int initialSteps;
    private LocalTime initialTime;
    private LocalDateTime initialDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        user = WWRApplication.getUser();

        // Setting up before getting initial steps value to ensure up-to-date data
        setUpFitnessService();

        // Set initial values
        initialSteps = user.getTotalSteps();
        initialTime = WWRApplication.getTime();
        initialDate = WWRApplication.getDate();

        // Handle case of walking an existing route
        onSavedRoute = getIntent().getBooleanExtra(SAVED_ROUTE_KEY, false);
        if (onSavedRoute) {
            savedRouteID = getIntent().getIntExtra(SAVED_ROUTE_ID_KEY, 0);
            Log.d(TAG, "Saved route ID: " + savedRouteID);
            displayRouteSummary();
        }

        Button mockingButton = findViewById(R.id.walkMockingButton);
        mockingButton.setOnClickListener(v -> launchMockingActivity());

        // Buttons to end activity
        Button cancelButton = findViewById(R.id.walkCancelButton);
        cancelButton.setOnClickListener(v -> finish());

        Button stopButton = findViewById(R.id.stopWalkButton);
        stopButton.setOnClickListener(v -> showSaveDialog());
    }

    private void displayRouteSummary() {
        Route route = user.getRoutes().getRouteByID(savedRouteID);
        Log.d(TAG, "Displaying walk summary for route " + route);
        ((TextView)findViewById(R.id.walkRouteName)).setText(route.getName());
        ((TextView)findViewById(R.id.walkStartingPoint)).setText(route.getStartingPoint());
        ((TextView)findViewById(R.id.walkNotes)).setText(route.getNotes());
    }

    private void setUpFitnessService() {
        if (WWRApplication.hasFitnessService()) {
            Log.d(TAG, "Using existing FitnessService");
            fitnessServiceActive = true;
        } else {
            String fitnessServiceKey = getIntent().getStringExtra(MainActivity.FITNESS_SERVICE_KEY);
            Log.d(TAG, "Setting up FitnessService with key: " + fitnessServiceKey);
            fitnessServiceActive = (fitnessServiceKey != null);
            if (fitnessServiceActive) {
                // Only set up if a non-null key was provided
                WWRApplication.setUpFitnessService(fitnessServiceKey, this);
            }
        }

        fitnessService = WWRApplication.getFitnessService();
        if (fitnessServiceActive) {
            fitnessService.updateStepCount();
        }
    }


    public void launchMockingActivity() {
        Intent intent = new Intent(this, MockingActivity.class);
        startActivity(intent);
    }


    public AlertDialog showSaveDialog () {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WalkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_verify_save, null);
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

            // Save route data
            if (fitnessServiceActive) {
                fitnessService.updateStepCount();
            }
            int walkSteps = user.getTotalSteps() - initialSteps;
            LocalTime walkTime = WWRApplication.getTime().minus(
                    Duration.ofNanos(initialTime.toNanoOfDay()));
            LocalDateTime walkDate = initialDate;
            Log.d(TAG, "Saving route with steps " + walkSteps + ", time " + walkTime
                    + ", date " + walkDate);

            if (onSavedRoute) {
                user.getRoutes().updateRouteData(savedRouteID, walkSteps, walkTime, walkDate);
                finish();
            } else {
                (new SaveRouteDialog(this, this, walkSteps, walkTime, walkDate))
                        .inputRouteDataDialog();
            }
        });

        return alert;
    }
}
