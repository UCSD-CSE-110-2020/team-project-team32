package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
public class MainActivity extends AppCompatActivity {
    private int userHeight;
    private EditText heightEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView DailySteps = findViewById(R.id.dailyStepsDisplay);
        TextView DailyMiles = findViewById(R.id.dailyMilesDisplay);

        if (UserData.retrieveHeight(MainActivity.this) == UserData.NO_HEIGHT_FOUND) {
            showInputDialog();
        }
        userHeight = UserData.retrieveHeight(MainActivity.this);
        updateDailyMiles(Integer.parseInt(DailySteps.getText().toString()),DailyMiles);
        updateRecentRoute();
    }

    public void updateDailyMiles(int steps, TextView miles){
        double update = MilesCalculator.calculateMiles(userHeight, steps);
        miles.setText(MilesCalculator.formatMiles(update));
    }

    public void updateRecentRoute() {
        Route recent = UserData.retrieveRecentRoute();
        int steps = recent.getSteps();

        ((TextView)findViewById(R.id.recentStepsDisplay)).setText(Integer.toString(steps));
        ((TextView)findViewById(R.id.recentMilesDisplay))
                .setText(MilesCalculator.formatMiles(
                         MilesCalculator.calculateMiles(userHeight, steps)));
        ((TextView)findViewById(R.id.recentTimeDisplay)).setText(recent.getDuration().toString());
    }

    protected AlertDialog showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_height, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        heightEditor = promptView.findViewById(R.id.heightInput);
        heightEditor.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        validateHeight();

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int getInputLength = heightEditor.getText().toString().length();
                        if(getInputLength > 2 || getInputLength <= 0 ) {
                            showInputDialog();
                        }
                        else {
                            UserData.saveHeight(MainActivity.this,
                                    Integer.parseInt(heightEditor.getText().toString()));
                        }
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return alert;
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
