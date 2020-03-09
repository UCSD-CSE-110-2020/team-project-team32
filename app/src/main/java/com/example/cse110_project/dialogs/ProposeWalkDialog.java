package com.example.cse110_project.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ProposeWalkDialog {

    private Context context;
    private User user;
    private AppCompatActivity activity;
    private LocalDateTime date;

    public ProposeWalkDialog(AppCompatActivity activity, Context context, int steps, LocalTime time,
                           LocalDateTime date) {
        user = WWRApplication.getUser();
        this.activity = activity;
        this.context = context;
        this.date = date;
    }

    public AlertDialog launchProposeWalk() {
        // Get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_propose_walk, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(R.string.sendInvite, null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setId(R.id.sendInviteButton);

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> alert.dismiss());
        return alert;
    }
}
