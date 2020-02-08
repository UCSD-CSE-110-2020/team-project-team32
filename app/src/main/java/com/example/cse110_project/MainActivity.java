package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
public class MainActivity extends AppCompatActivity {
    private EditText heightEditor;

    private Button mocking_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating mocking button
        mocking_button = (Button) findViewById(R.id.mockingButton);
        mocking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMockingActivity();
            }
        });

        TextView DailySteps = findViewById(R.id.dailyStepsDisplay);
        TextView DailyMiles = findViewById(R.id.dailyMilesDisplay);

        if (UserData.retrieveHeight(MainActivity.this) == DataConstants.NO_HEIGHT_FOUND) {
            showInputDialog();
        }
        User.setHeight(UserData.retrieveHeight(MainActivity.this));
        updateDailyMiles(Integer.parseInt(DailySteps.getText().toString()),DailyMiles);
        updateRecentRoute();
    }

    //openMockingActivity method
    public void openMockingActivity(){
        Intent intent = new Intent(this, mocking.class);
        startActivity(intent);
    }

    public void updateDailyMiles(int steps, TextView miles){
        double update = MilesCalculator.calculateMiles(User.getHeight(), steps);
        miles.setText(MilesCalculator.formatMiles(update));
    }

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