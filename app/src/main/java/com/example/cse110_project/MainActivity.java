package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cse110_project.database.FirebaseFirestoreAdapter;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.User;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.util.MilesCalculator;

import java.time.temporal.ChronoUnit;

public class MainActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final String MAX_UPDATES_KEY = "MAX_UPDATES_KEY";
    public static final String DELAY_KEY = "DELAY_KEY";
    public static final String USER_COLLECTIONS_KEY = "user_data";
    public static final String TEAM_COLLECTIONS_KEY = "team_data";
    public static final String INVITES_KEY = "invites";
    public static final String ROUTES_KEY = "routes";
    private static final int DEFAULT_DELAY = 5;
    private static final String TAG = "MainActivity";

    // Text fields
    private EditText emailEditor;
    private EditText heightEditor;
    private TextView stepsDisplay;
    private TextView milesDisplay;

    // Fitness service fields
    private User user;
    private FitnessService fitnessService;
    private boolean fitnessServiceActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepsDisplay = findViewById(R.id.dailySteps);
        milesDisplay = findViewById(R.id.dailyMiles);

        // Set up user & fitness service
        user = WWRApplication.getUser();
        if (user.getHeight() == DataConstants.NO_HEIGHT_FOUND || user.getEmail() == null) {
            showInputDialog();
        } else {
            if (WWRApplication.getDatabase() == null) {
                WWRApplication.setDatabase(new FirebaseFirestoreAdapter(USER_COLLECTIONS_KEY,
                        TEAM_COLLECTIONS_KEY, user.getEmail(), INVITES_KEY, ROUTES_KEY));
                WWRApplication.getUser().initTeam();
            }
        }

        setUpFitnessService();

        // To other activities
        Button routesBtn = findViewById(R.id.routesButton);
        routesBtn.setOnClickListener(v -> launchRoutesActivity());

        Button mockingBtn = findViewById(R.id.mockingButton);
        mockingBtn.setOnClickListener(v -> launchMockingActivity());

        Button walkBtn = findViewById(R.id.startWalkButton);
        walkBtn.setOnClickListener(v -> launchWalkActivity());

        Button teamBtn = findViewById(R.id.teamButton);
        teamBtn.setOnClickListener(v -> launchTeamActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (fitnessServiceActive) {
            int maxStepUpdates = getIntent().getIntExtra(MAX_UPDATES_KEY, Integer.MAX_VALUE);
            int updateDelay = getIntent().getIntExtra(DELAY_KEY, DEFAULT_DELAY);
            StepsTrackerAsyncTask async = new StepsTrackerAsyncTask();
            async.execute(String.valueOf(updateDelay), String.valueOf(maxStepUpdates));
        }

        updateDailySteps(user.getFitnessSteps());
        updateRecentRoute();
    }


    private void setUpFitnessService() {
        if (WWRApplication.hasFitnessService()) {
            Log.d(TAG, "Using existing FitnessService");
            fitnessServiceActive = true;
        } else {
            String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
            Log.d(TAG, "Setting up FitnessService with key: " + fitnessServiceKey);
            fitnessServiceActive = (fitnessServiceKey != null);
            if (fitnessServiceActive) {
                // Only set up if a non-null key was provided
                WWRApplication.setUpFitnessService(fitnessServiceKey, this);
            }
        }

        fitnessService = WWRApplication.getFitnessService();
    }


    // Steps & miles display methods

    public void updateDailySteps(int steps) {
        user.setFitnessSteps(steps);
        int totalSteps = user.getTotalSteps();
        Log.d(TAG, "updateDailySteps called on " + steps + " for total of "
                + totalSteps);
        stepsDisplay.setText(String.valueOf(totalSteps));
        updateDailyMiles(totalSteps);
    }

    public void updateDailyMiles(int steps) {
        double miles = user.getMiles();
        milesDisplay.setText(MilesCalculator.formatMiles(miles));
        Log.d(TAG, "updateDailyMiles called on " + steps + " with miles " +
                MilesCalculator.formatMiles(miles) + " [" + miles + "]");
    }

    public void updateRecentRoute() {
        Route recent = user.getRoutes().getMostRecentRoute();
        Log.d(TAG, "Current routes: " + user.getRoutes());
        Log.d(TAG, "Recent route: " + recent);
        String stepsDisplay;
        String milesDisplay;
        String timeDisplay;

        if (recent == null) {
            stepsDisplay = DataConstants.NO_RECENT_ROUTE;
            milesDisplay = DataConstants.NO_RECENT_ROUTE;
            timeDisplay = DataConstants.NO_RECENT_ROUTE;
        } else {
            stepsDisplay = Integer.toString(recent.getSteps());
            milesDisplay = MilesCalculator.formatMiles(recent.getMiles(user.getHeight()));
            timeDisplay = recent.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
        }

        ((TextView)findViewById(R.id.recentSteps)).setText(stepsDisplay);
        ((TextView)findViewById(R.id.recentMiles)).setText(milesDisplay);
        ((TextView)findViewById(R.id.recentTime)).setText(timeDisplay);
    }


    // To other activities

    public void launchWalkActivity() {Intent intent = new Intent(this, WalkActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY,
                getIntent().getStringExtra(FITNESS_SERVICE_KEY));
        startActivity(intent);
    }

    public void launchRoutesActivity() {
        Intent intent = new Intent(this, RoutesActivity.class);
        startActivity(intent);
    }

    public void launchMockingActivity(){
        Intent intent = new Intent(this, MockingActivity.class);
        startActivity(intent);
    }

    public void launchTeamActivity(){
        Intent intent = new Intent(this, TeamActivity.class);
        startActivity(intent);
    }


    // Height input methods

    public AlertDialog showInputDialog() {
        // Get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_launch_data, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setPositiveButton(R.string.heightButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        emailEditor = promptView.findViewById(R.id.emailInput);

        heightEditor = promptView.findViewById(R.id.heightInput);
        heightEditor.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_NORMAL);
        validateHeight();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(v -> onDialogClickValidate(alert));

        return alert;
    }

    // Validate submitted height input
    public void onDialogClickValidate(DialogInterface dialogToDismiss) {
        String emailInput = emailEditor.getText().toString();
        user.setEmail(emailInput);
        if (WWRApplication.getDatabase() == null) {
            WWRApplication.setDatabase(new FirebaseFirestoreAdapter(USER_COLLECTIONS_KEY,
                    TEAM_COLLECTIONS_KEY, user.getEmail(), INVITES_KEY, ROUTES_KEY));
            WWRApplication.getUser().initTeam();
        }

        String heightInput = heightEditor.getText().toString();
        int input;

        try {
            input = Integer.parseInt(heightInput);
        } catch (RuntimeException e) {
            Toast.makeText(MainActivity.this, R.string.invalidHeightToast,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (input <= 0) {
            Toast.makeText(MainActivity.this, R.string.invalidHeightToast,
                    Toast.LENGTH_SHORT).show();
        } else if (heightInput.length() > 2 || heightInput.length() <= 0) {
            Toast.makeText(MainActivity.this, R.string.invalidHeightToast,
                    Toast.LENGTH_SHORT).show();
        } else {
            user.setHeight(input);
            dialogToDismiss.dismiss();
        }
    }

    public void validateHeight() {
        heightEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textHeight = heightEditor.getText().toString();
                if (textHeight.length() > 2) {
                    heightEditor.setError(getString(R.string.longHeightError));
                } else {
                    try {
                        int input = Integer.parseInt(textHeight);
                        if (input <= 0) {
                            heightEditor.setError(getString(R.string.nonPositiveHeightError));
                        } else {
                            heightEditor.setError(null);
                        }
                    } catch (RuntimeException e) {
                        heightEditor.setError(getString(R.string.invalidHeightError));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    // Private class used to run continuous display updates
    private class StepsTrackerAsyncTask extends AsyncTask<String, String, String> {
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            int maxStepUpdates = Integer.parseInt(params[1]);
            int delay = Integer.parseInt(params[0]) * 1000;
            int count = 0;

            while (count < maxStepUpdates) {
                Log.d(TAG, "Updating steps with max updates = " + maxStepUpdates);
                try {
                    Thread.sleep(delay);
                    fitnessService.updateStepCount();
                    count++;
                    resp = "Updated step count " + count + " times";

                } catch (Exception e) {
                    e.printStackTrace();
                    resp = e.getMessage();
                }
            }

            return resp;
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... text) {}
    }

}