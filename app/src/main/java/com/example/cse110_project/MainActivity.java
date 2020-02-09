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
import com.example.cse110_project.fitness_api.GoogleFitAdapter;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;

public class MainActivity extends AppCompatActivity {
    public static final String GOOGLE_API_KEY = "GOOGLE_FIT";
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";

    private static final String TAG = "MainActivity";

    // Text fields
    private EditText heightEditor;
    private TextView stepCount;
    private TextView milesCount;

    // Fitness service fields
    public FitnessService fitnessService;
    Handler handler;
    Runnable runnable;
    final int delay = 5*1000;

    private Button launchToRouteScreen; // = findViewById(R.id.routesButton);


    private Button mocking_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCount = findViewById(R.id.dailyStepsDisplay);
        milesCount = findViewById(R.id.dailyMilesDisplay);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity stepCountActivity) {
                return new GoogleFitAdapter(stepCountActivity);
            }
        });

        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        handler = new Handler();

        // to route screen
        launchToRouteScreen = findViewById(R.id.routesButton);
        launchToRouteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRouteActivity();
            }
        });
        // end To Route screen

        // creating MockingActivity button
        mocking_button = (Button) findViewById(R.id.mockingButton);
        mocking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMockingActivity();
            }
        });
        // end of dev button

        TextView DailySteps = findViewById(R.id.dailyStepsDisplay);
        TextView DailyMiles = findViewById(R.id.dailyMilesDisplay);

        if (UserData.retrieveHeight(MainActivity.this) == DataConstants.NO_HEIGHT_FOUND) {
            showInputDialog();
        }
        User.setHeight(UserData.retrieveHeight(MainActivity.this));
        updateDailyMiles(Integer.parseInt(stepCount.getText().toString()), milesCount);
        updateRecentRoute();

        fitnessService.setup();
    }

    // Daily steps & miles methods

    @Override
    protected void onResume() {
        //start handler as activity become visible
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                fitnessService.updateStepCount();
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

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
//        User.setSteps(steps);
//        steps = User.getSteps();
//
//        stepCount.setText(String.valueOf(steps));
//        updateDailyMiles(steps, milesCount);

        stepCount.setText(String.valueOf(steps));
        updateDailyMiles(steps, milesCount);
        // update steps from google fitness

    }

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
    }

    // Recent route update

    public void updateRecentRoute() {
        Route recent = User.getRoutes(MainActivity.this).getMostRecentRoute();
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
            timeDisplay = recent.getDuration().toString();
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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogClickValidate(alert);
            }
        });

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