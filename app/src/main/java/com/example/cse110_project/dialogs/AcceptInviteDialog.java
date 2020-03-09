package com.example.cse110_project.dialogs;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.Invite;

public class AcceptInviteDialog {
    private static final String TAG = AcceptInviteDialog.class.getSimpleName();
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
        setUpDialogContent();
    }

    private void setUpDialogContent() {
        ((TextView)alert.findViewById(R.id.inviteText)).setText("You've been invited to join " +
                invite.getCreatorId() + "'s team! Accept or decline?");
        alert.findViewById(R.id.inviteBackButton).setOnClickListener(v -> alert.dismiss());

        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> acceptInvite());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setId(R.id.acceptInviteButton);
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> declineInvite());
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setId(R.id.declineInviteButton);
    }

    public void acceptInvite() {
        Log.d(TAG, "Accepting invite " + invite);
        invite.accept();
        clearInvites();
        alert.dismiss();
    }

    public void declineInvite() {
        Log.d(TAG, "Declining invite " + invite);
        invite.decline();
        clearInvites();
        alert.dismiss();
    }

    private void clearInvites() {
        for (Invite inv : WWRApplication.getUser().getInvites()) {
            if (inv != invite) {
                WWRApplication.getDatabase().declineInvite(inv);
            }
        }

        WWRApplication.getUser().getInvites().clear();
        context.findViewById(R.id.inviteButton).setVisibility(View.INVISIBLE);
    }
}
