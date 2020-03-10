package com.example.cse110_project.dialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.ScheduledWalk;
import com.example.cse110_project.team.WalkScheduler;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.cse110_project.RouteDetailsActivity.ROUTE_INDEX_KEY;

public class ProposeWalkDialog extends DialogFragment {

    //private EditText routeName;
    private User user;
    private AppCompatActivity activity;
    private Route route; // route
    private EditText time;
    private EditText date;
    private WalkScheduler walk;
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

        //fixme
        TextView routeName = activity.findViewById(R.id.nameOfWalkText);
        routeName.setText(nameOfRoute);
        System.out.print(routeName);
        //routeName.setText(nameOfRoute);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity)
                .setCancelable(false)
                .setPositiveButton("Propose", null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


        date = promptView.findViewById(R.id.enterDateInput);
        time = promptView.findViewById(R.id.enterTimeInput);

        validateTextInput();

        /** Creates intended proposed walk here!!! **/
        //walk.createScheduledWalk(route, createTime, user.getEmail(), user.getTeam());

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setId(R.id.sendInviteButton);

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> alert.dismiss());

        return alert;
    }

    private void validateTextInput() {
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (date.getText().toString().length() == 0) {
                    date.setError(activity.getResources().getString(R.string.emptyRouteName));
                    time.setError(activity.getResources().getString(R.string.emptyRouteName));
                } else {
                    date.setError(null);
                    time.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
