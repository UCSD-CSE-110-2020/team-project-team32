package com.example.cse110_project.dialogs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.team.Invite;

public class AcceptInviteDialog {
    private Invite invite;
    private Activity context;
    private AlertDialog alert;

    public AcceptInviteDialog(Activity activity, Invite invite) {
        this.invite = invite;
        context = activity;
    }

    public void showDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_accept_invite, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(R.string.acceptButton, null)
                .setNegativeButton(R.string.declineButton, null);
        alertDialogBuilder.setView(promptView);

        this.alert = alertDialogBuilder.create();
        alert.show();

        alert.findViewById(R.id.inviteBackButton).setOnClickListener(v -> alert.dismiss());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> acceptInvite());
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> declineInvite());
    }

    public void acceptInvite() {
        System.out.println("Accepting invite");
        alert.dismiss();
    }

    public void declineInvite() {
        System.out.println("Declining invite");
        alert.dismiss();
    }
}
