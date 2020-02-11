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

public class SaveRoute {

    EditText WalkName;
    Route route;
    Toast toastmessage;

    public AlertDialog InputWalkDataDialog(Context context) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_walkdata, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(promptView);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        WalkName = promptView.findViewById(R.id.NameOfWalkInput);

        validateTextInput();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastmessage.makeText(context , R.string.cancelDialog,
                        toastmessage.LENGTH_SHORT).show();
                alert.dismiss();
                goToHomeScreen(context);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateOnClickSave(context,alert);
            }
        });

        return alert;
    }

    public void goToRouteScreen(Context context) {
        Intent  intent = new Intent(context, RouteScreen.class);
        context.startActivity(intent);
    }

    public void goToHomeScreen(Context context) {
        Intent  intent = new Intent(context, EntryActivity.class);
        context.startActivity(intent);
    }

    public void validateOnClickSave(Context context, DialogInterface dialog) {
         if(WalkName.getText().toString().length() == 0) {
             toastmessage.makeText(context, R.string.InvalidWalkName,
                     toastmessage.LENGTH_SHORT).show();
         }
         else {
             dialog.dismiss();
             goToRouteScreen(context);
             route = new Route(0, WalkName.getText().toString());
             User.getRoutes(context).createRoute(context, route);
         }
    }
    public void validateTextInput() {
        WalkName.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (WalkName.getText().toString().length() == 0) {
                WalkName.setError("please enter a walk name");
            }
            else {
                WalkName.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    });
}
}
