package com.example.cse110_project.dialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.ScheduledWalk;
import com.example.cse110_project.team.WalkScheduler;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import static com.example.cse110_project.RouteDetailsActivity.ROUTE_INDEX_KEY;

public class ProposeWalkDialog extends DialogFragment {

    private DatePicker datepicker;
    private TimePicker timepicker;
    private User user;
    private AppCompatActivity activity;
    private Route route; // route
    private LocalDateTime createTime;

    public ProposeWalkDialog(AppCompatActivity activity, Route route) {
        user = WWRApplication.getUser();
        this.activity = activity;
        this.route = route;
    }

    public AlertDialog launchProposeWalk(AppCompatActivity activity, Route route) {
        String nameOfRoute = route.getName();
        //System.out.println(nameOfRoute);
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View promptView = layoutInflater.inflate(R.layout.dialog_propose_walk, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setPositiveButton("set Date", null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        TextView routeName = alert.findViewById(R.id.nameOfWalkText);
        routeName.setText(nameOfRoute);

        datepicker = promptView.findViewById(R.id.datePicker);

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.dismiss();
                setTime(route, datepicker);
            }
        });

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> alert.dismiss());

        return alert;
    }

    public AlertDialog setTime(Route route, DatePicker date) {
        String nameOfRoute = route.getName();
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View promptView = layoutInflater.inflate(R.layout.propose_walk_timepicker, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setPositiveButton("set Time", null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        TextView routeName = alert.findViewById(R.id.nameOfWalkText);
        routeName.setText(nameOfRoute);

        timepicker = promptView.findViewById(R.id.timePicker);
        timepicker.setIs24HourView(true);


        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LocalDateTime dateTimePicked =
                        LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                                timepicker.getHour(), timepicker.getMinute());
                alert.dismiss();
                WalkScheduler walk = new WalkScheduler();
                walk.createScheduledWalk(route, dateTimePicked, user.getEmail(), user.getTeam());
            }
        });

        //submitButton.setId(R.id.sendInviteButton);

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> alert.dismiss());

        return alert;
    }
}
