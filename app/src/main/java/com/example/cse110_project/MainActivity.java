package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private String personHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInputDialog();
        TextView DailySteps = (TextView) findViewById(R.id.NumbeOfSteps);
        TextView DailyMiles = (TextView) findViewById(R.id.miles);
        updateDailyMiles(Integer.parseInt(DailySteps.getText().toString()),DailyMiles);

    }

    public void updateDailyMiles(int steps, TextView miles){
        calculateData calculateDistance = new calculateData();
        miles.setText(String.valueOf(calculateDistance.calculateMiles("4'0", steps)));
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_height, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        personHeight = editText.getText().toString(); // fix this personHeight out of scope
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
