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
    private String personHeight;
    private EditText editHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView DailySteps = (TextView) findViewById(R.id.NumbeOfSteps);
        TextView DailyMiles = (TextView) findViewById(R.id.miles);

        // height is already saved in system showInputDialog() shouldn't show up for now
        if(MilesCalculator.retrieveHeight(MainActivity.this) == "") {
            showInputDialog();
            personHeight = MilesCalculator.retrieveHeight(MainActivity.this);
        }
        else {
            // should go directly to retrieve height after asking once
            personHeight = MilesCalculator.retrieveHeight(MainActivity.this);
            updateDailyMiles(Integer.parseInt(DailySteps.getText().toString()),DailyMiles);
        }

    }

    public void updateDailyMiles(int steps, TextView miles){
        double update;
        update = (MilesCalculator.calculateMiles(Integer.parseInt(personHeight), steps));
        miles.setText((int)update + "." + (int)(((update + 0.5) * 10) % 10));

    }

    protected AlertDialog showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_height, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        editHeight = (EditText) promptView.findViewById(R.id.HeightInput);
        editHeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        validateHeight();
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int getInputLength = editHeight.getText().toString().length();
                        if(getInputLength > 2 || getInputLength <= 0 ) {
                            showInputDialog();
                        }
                        else {
                            saveheightInput();
                        }
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return alert;
    }

    public void saveheightInput() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_height", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("editHeight", editHeight.getText().toString());
        editor.apply();
        Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
    }

    public void validateHeight() {
        editHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editHeight.getText().toString().length() > 2) {
                    editHeight.setError("enter at most 2 digit number");
                } else {
                    editHeight.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
