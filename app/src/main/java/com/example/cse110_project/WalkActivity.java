package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;
import com.example.cse110_project.trackers.CurrentTimeTracker;
import com.example.cse110_project.trackers.CurrentWalkTracker;
import com.example.cse110_project.user_routes.User;

import java.time.LocalTime;


public class WalkActivity extends AppCompatActivity {
    private FitnessService fitnessService;
    private boolean fitnessServiceActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        Button mockingButton = findViewById(R.id.walkMockingButton);
        mockingButton.setOnClickListener(v -> launchMockingActivity());

        // Buttons to end activity
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> returnToHomeActivity());

        Button stopButton = findViewById(R.id.stopWalkButton);
        stopButton.setOnClickListener(v -> {
            endWalkActivity(CurrentTimeTracker.getTime());
            showSaveDialog();
        });

        // Set up fitnessService
        String fitnessServiceKey = getIntent().getStringExtra(MainActivity.FITNESS_SERVICE_KEY);
        System.out.println("Walk service key: " + fitnessServiceKey);
        fitnessServiceActive = (fitnessServiceKey != null);
        if (fitnessServiceActive) {
            fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
            fitnessService.setup();
        }
    }

    public void launchMockingActivity() {
        Intent intent = new Intent(this, MockingActivity.class);
        startActivity(intent);
    }

    public void returnToHomeActivity() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }

    public void endWalkActivity(LocalTime finalTime) {
        System.out.println("Walk activity ended - " + fitnessServiceActive);
        if (fitnessServiceActive) {
            fitnessService.updateStepCount();
        }
        CurrentWalkTracker.setFinalSteps(User.getTotalSteps());
        CurrentWalkTracker.setFinalTime(finalTime);
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
        cancelButton.setOnClickListener(v -> {
               alert.dismiss();
               validateCancel();
        });
        submitButton.setOnClickListener(v -> {
            alert.dismiss();
            (new SaveRoute(WalkActivity.this, CurrentWalkTracker.getWalkSteps(),
                    CurrentWalkTracker.getWalkTime(), CurrentWalkTracker.getWalkDate()))
                    .inputRouteDataDialog();
        });

        return alert;
    }

    private AlertDialog validateCancel() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WalkActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_sure_cancel, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WalkActivity.this)
                .setCancelable(false)
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        Button yesButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button NoButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        NoButton.setOnClickListener(v -> {
            alert.dismiss();
            (new SaveRoute(WalkActivity.this, CurrentWalkTracker.getWalkSteps(),
                    CurrentWalkTracker.getWalkTime(), CurrentWalkTracker.getWalkDate()))
                    .inputRouteDataDialog();
        });
        yesButton.setOnClickListener(v -> {
             Toast.makeText(WalkActivity.this, R.string.cancelDialog,
                     Toast.LENGTH_SHORT).show();
             alert.dismiss();
             returnToHomeActivity();
        });

        return alert;
    }
}
