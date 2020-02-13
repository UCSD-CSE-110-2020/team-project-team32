package com.example.cse110_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.R;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class SaveRoute {
    private EditText walkName;
    private Route route;
    private AlertDialog alert;

    private AppCompatActivity activity;
    private Context context;
    private int steps;
    private LocalTime time;
    private LocalDateTime date;

    public SaveRoute(AppCompatActivity activity, Context context, int steps, LocalTime time,
                     LocalDateTime date) {
        this.activity = activity;
        this.context = context;
        System.out.println("Context theme: " + context.getTheme());
        this.steps = steps;
        this.time = time;
        this.date = date;
    }

    public EditText getWalkName() {
        return walkName;
    }

    public AlertDialog inputRouteDataDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_walkdata, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(R.string.saveButton, null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        alert = alertDialogBuilder.create();
        alert.show();

        walkName = promptView.findViewById(R.id.NameOfWalkInput);

        validateTextInput();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> {
            Toast.makeText(context, R.string.cancelDialog, Toast.LENGTH_SHORT).show();
            alert.dismiss();
        });
        submitButton.setOnClickListener(v -> validateOnClickSave(alert));

        return alert;
    }

    public void goToRouteScreen() {
        context.startActivity(new Intent(context, RouteScreen.class));
    }

    public void validateOnClickSave(DialogInterface dialog) {
         if (walkName.getText().toString().length() == 0) {
             Toast.makeText(context, R.string.invalidWalkName, Toast.LENGTH_SHORT).show();
         } else {
             saveRoute();
             dialog.dismiss();
             goToRouteScreen();
             activity.finish();
         }
    }

    public void saveRoute() {
        route = new Route(0, walkName.getText().toString());
        route.setSteps(steps);
        route.setDuration(time);
        route.setStartDate(date);

        User.getRoutes(context).createRoute(context, route);
    }

    public void validateTextInput() {
        walkName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (walkName.getText().toString().length() == 0) {
                    walkName.setError(context.getResources().getString(R.string.emptyWalkName));
                }
                else {
                    walkName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
