package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.UserData;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class MainActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private static final String TAG = "MainActivity";

    // Text fields
    private EditText heightEditor;
    private TextView stepCount;
    private TextView milesCount;

    // Fitness service fields
    private FitnessService fitnessService;
    private boolean fitnessServiceActive;
    Handler handler;
    Runnable runnable;
    final int delay = 5*1000;

    private Button launchToRouteScreen; // = findViewById(R.id.routesButton);
    private Button mocking_button;
    private Button startWalkButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepCount = findViewById(R.id.dailyStepsDisplay);
        milesCount = findViewById(R.id.dailyMilesDisplay);

        // to route screen
        launchToRouteScreen = findViewById(R.id.routesButton);
        launchToRouteScreen.setOnClickListener(view -> launchRouteActivity());

        // end To Route screen

        // creating MockingActivity button
        mocking_button = findViewById(R.id.mockingButton);
        mocking_button.setOnClickListener(v -> openMockingActivity());
        // creating mocking button
        mocking_button = findViewById(R.id.mockingButton);
        mocking_button.setOnClickListener(v -> openMockingActivity());

        //to walk screen
        startWalkButton = findViewById(R.id.startWalkButton);
        startWalkButton.setOnClickListener(v -> {
            if (fitnessServiceActive) {
                fitnessService.updateStepCount();
            }
            launchWalkActivity(User.getTotalSteps(), LocalTime.now(), LocalDateTime.now());
        });
        // end of dev button

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        System.out.println("Service key: " + fitnessServiceKey);
        fitnessServiceActive = (fitnessServiceKey != null);
        if (fitnessServiceActive) {
            fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
            handler = new Handler();
        }

        if (UserData.retrieveHeight(MainActivity.this) == DataConstants.NO_HEIGHT_FOUND) {
            showInputDialog();
        }
        User.setHeight(UserData.retrieveHeight(MainActivity.this));
        updateDailySteps(0);
        updateRecentRoute();

        if (fitnessServiceActive) {
            fitnessService.setup();
        }

    }

    // Daily steps & miles methods

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Called if authentication required during google fit setup
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    public void updateDailySteps(int steps) {
        System.out.println(TAG + "updateDailySteps called on" + steps);
        User.setFitnessSteps(steps);
        int totalSteps = User.getTotalSteps();
        stepCount.setText(String.valueOf(totalSteps));
        updateDailyMiles(totalSteps, milesCount);
        // update steps from google fitness
    }

    public void updateFromFitnessService() {
        fitnessService.updateStepCount();
    }


    // To Walk Screen

    public void launchWalkActivity(int steps, LocalTime time, LocalDateTime date) {
        Intent intent = new Intent(this, WalkActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY,
                getIntent().getStringExtra(FITNESS_SERVICE_KEY));
        CurrentWalkTracker.setInitial(steps, time, date);
        startActivity(intent);
    }

    // To other activities

    // To routes activity
    public void launchRouteActivity() {
        Intent intent = new Intent(this, RouteScreen.class);
        startActivity(intent);
    }
    // end of to route screen implementation

    //openMockingActivity method
    public void openMockingActivity(){
        Intent intent = new Intent(this, MockingActivity.class);
        startActivity(intent);
    }

    public void updateDailyMiles(int steps, TextView miles){
        double update = MilesCalculator.calculateMiles(User.getHeight(), steps);
        miles.setText(MilesCalculator.formatMiles(update));
        System.out.println(TAG + " updateDailyMiles called on " + steps + " with miles " +
                MilesCalculator.formatMiles(update) + " [" + update + "]");
    }

    // Recent route update

    public void updateRecentRoute() {
        Route recent = User.getRoutes(MainActivity.this).getMostRecentRoute();
        System.out.println("Routes: " + User.getRoutes(MainActivity.this));
        System.out.println("Recent: " + recent);
        String stepsDisplay;
        String milesDisplay;
        String timeDisplay;

        if (recent == null) {
            stepsDisplay = DataConstants.NO_RECENT_ROUTE;
            milesDisplay = DataConstants.NO_RECENT_ROUTE;
            timeDisplay = DataConstants.NO_RECENT_ROUTE;
        } else {
            stepsDisplay = Integer.toString(recent.getSteps());
            milesDisplay = MilesCalculator.formatMiles(
                    MilesCalculator.calculateMiles(User.getHeight(), recent.getSteps()));
            timeDisplay = recent.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
        }

        ((TextView)findViewById(R.id.recentStepsDisplay)).setText(stepsDisplay);
        ((TextView)findViewById(R.id.recentMilesDisplay)).setText(milesDisplay);
        ((TextView)findViewById(R.id.recentTimeDisplay)).setText(timeDisplay);
    }

    // Height input methods

    public AlertDialog showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_height, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setPositiveButton(R.string.heightButton, null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        heightEditor = promptView.findViewById(R.id.heightInput);
        heightEditor.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_VARIATION_NORMAL);
        validateHeight();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(v -> onDialogClickValidate(alert));

        return alert;
    }


    public void onDialogClickValidate(DialogInterface dialogToDismiss) {
        String inputStr = heightEditor.getText().toString();
        int input;

        try {
            input = Integer.parseInt(inputStr);
        } catch (RuntimeException e) {
            Toast.makeText(MainActivity.this, R.string.invalidHeightToast,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (input <= 0) {
            Toast.makeText(MainActivity.this, R.string.invalidHeightToast,
                    Toast.LENGTH_SHORT).show();

        } else if (inputStr.length() > 2 || inputStr.length() <= 0) {
            Toast.makeText(MainActivity.this, R.string.invalidHeightToast,
                    Toast.LENGTH_SHORT).show();

        } else {
            UserData.saveHeight(MainActivity.this, input);
            User.setHeight(input);
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
                            heightEditor.setError(getString(R.string.nonpositiveHeightError));
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

}