package com.example.cse110_project.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.example.cse110_project.RoutesActivity;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class SaveRouteDialog {
    private static final String DESELECT_COL = "#D3D3D3";
    private static final String SELECT_COL = "#00bfff";
    private User user;

    private EditText routeName;
    private EditText routeNotes;
    private EditText routeStartPt;
    private AlertDialog alert;

    private AppCompatActivity activity;
    private Context context;
    private int steps;
    private LocalTime time;
    private LocalDateTime date;

    private Button loopPick;
    private Button outAndBack;
    private Button flatPick;
    private Button hillyPick;
    private Button streetPick;
    private Button trailPick;
    private Button evenPick;
    private Button unevenPick;
    private Button easyPick;
    private Button moderatePick;
    private Button difficultPick;
    private Button favoritePick;

    private boolean pickedLoop;
    private boolean pickedOutAndBack;
    private boolean pickedFlat;
    private boolean pickedHilly;
    private boolean pickedStreets;
    private boolean pickedTrail;
    private boolean pickedEven;
    private boolean pickedUneven;
    private boolean pickedEasy;
    private boolean pickedModerate;
    private boolean pickedDifficult;
    private boolean pickedFavorite;

    public SaveRouteDialog(AppCompatActivity activity, Context context, int steps, LocalTime time,
                           LocalDateTime date) {
        user = WWRApplication.getUser();
        this.activity = activity;
        this.context = context;
        System.out.println("Context theme: " + context.getTheme());
        this.steps = steps;
        this.time = time;
        this.date = date;
    }

    public EditText getWalkName() {
        return routeName;
    }
    public EditText getWalkNotes() { return routeNotes;}

    public AlertDialog inputRouteDataDialog() {
        // Get xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.dialog_new_route, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setPositiveButton(R.string.saveButton, null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        alert = alertDialogBuilder.create();
        alert.show();

        // Identify input views
        routeName = promptView.findViewById(R.id.routeNameInput);
        routeStartPt = promptView.findViewById(R.id.startingPointInput);
        routeNotes = promptView.findViewById(R.id.routeNotesInput);
        loopPick = promptView.findViewById(R.id.loopButton);
        outAndBack = promptView.findViewById(R.id.outBackButton);
        flatPick = promptView.findViewById(R.id.flatButton);
        hillyPick = promptView.findViewById(R.id.hillyButton);
        streetPick = promptView.findViewById(R.id.streetsButton);
        trailPick = promptView.findViewById(R.id.trailButton);
        evenPick = promptView.findViewById(R.id.evenSurfaceButton);
        unevenPick = promptView.findViewById(R.id.unevenSurfaceButton);
        easyPick = promptView.findViewById(R.id.easyDifficultyButton);
        moderatePick = promptView.findViewById(R.id.midDifficultyButton);
        difficultPick = promptView.findViewById(R.id.hardDifficultyButton);
        favoritePick = promptView.findViewById(R.id.favButton);

        validateTextInput();
        validateButtons();

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(v -> validateOnClickSave(alert));

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> {
            Toast.makeText(context, R.string.canceledToast, Toast.LENGTH_SHORT).show();
            alert.dismiss();
        });

        return alert;
    }

    private void launchRoutesActivity() {
        context.startActivity(new Intent(context, RoutesActivity.class));
    }

    // Check valid route input
    private void validateOnClickSave(DialogInterface dialog) {
        // Only name is required to save
        if (routeName.getText().toString().length() == 0) {
             Toast.makeText(context, R.string.emptyRouteName, Toast.LENGTH_SHORT).show();
        } else {
             saveRoute();
             dialog.dismiss();
             launchRoutesActivity();
             activity.finish(); // Finish prior Walk activity
        }
    }

    public void saveRoute() {
        UserRoute route = new UserRoute(0, routeName.getText().toString());

        if (date != null) {
            route.setSteps(steps);
            route.setDuration(time);
            route.setStartDate(date);
        }

        route.setStartingPoint(routeStartPt.getText().toString());
        route.setNotes(routeNotes.getText().toString());

        if (pickedFavorite) {
            route.setFavorite(true);
        }

        if (pickedLoop) { route.setLoopVsOutBack(Route.LOOP); }
        else if (pickedOutAndBack) { route.setLoopVsOutBack(Route.OUT_BACK); }

        if (pickedFlat) { route.setFlatVsHilly(Route.FLAT); }
        else if (pickedHilly) {route.setFlatVsHilly(Route.HILLY); }

        if (pickedStreets) { route.setStreetsVsTrail(Route.STREETS); }
        else if (pickedTrail) { route.setStreetsVsTrail(Route.TRAIL); }

        if (pickedEven) { route.setEvenVsUneven(Route.EVEN_S); }
        else if (pickedUneven) { route.setEvenVsUneven(Route.UNEVEN_S); }

        if (pickedEasy) { route.setDifficulty(Route.EASY_D); }
        else if (pickedModerate) { route.setDifficulty(Route.MID_D); }
        else if (pickedDifficult) { route.setDifficulty(Route.HARD_D); }

        user.getRoutes().createRoute(route);
    }

    private void validateTextInput() {
        routeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (routeName.getText().toString().length() == 0) {
                    routeName.setError(context.getResources().getString(R.string.emptyRouteName));
                } else {
                    routeName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void validateButtons() {
        loopPick.setOnClickListener(v -> {
            loopPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            outAndBack.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedOutAndBack = false;
            pickedLoop = true;
        });

        outAndBack.setOnClickListener(v -> {
            outAndBack.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            loopPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedOutAndBack = true;
            pickedLoop = false;
        });

        flatPick.setOnClickListener(v -> {
            flatPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            hillyPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedFlat = true;
            pickedHilly = false;
        });

        hillyPick.setOnClickListener(v -> {
            hillyPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            flatPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedFlat = false;
            pickedHilly = true;
        });

        streetPick.setOnClickListener(v -> {
            streetPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            trailPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedStreets = true;
            pickedTrail = false;
        });

        trailPick.setOnClickListener(v -> {
            trailPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            streetPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedStreets = false;
            pickedTrail = true;
        });

        evenPick.setOnClickListener(v -> {
            evenPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            unevenPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedEven = true;
            pickedUneven = false;
        });

        unevenPick.setOnClickListener(v -> {
            unevenPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            evenPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedEven = false;
            pickedUneven = true;
        });

        easyPick.setOnClickListener(v -> {
            easyPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            moderatePick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            difficultPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedEasy = true;
            pickedModerate = false;
            pickedDifficult = false;
        });

        moderatePick.setOnClickListener(v -> {
            moderatePick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            easyPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            difficultPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedEasy = false;
            pickedModerate = true;
            pickedDifficult = false;
        });

        difficultPick.setOnClickListener(v -> {
            difficultPick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            easyPick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            moderatePick.getBackground().setColorFilter(
                    Color.parseColor(DESELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedEasy = false;
            pickedModerate = false;
            pickedDifficult = true;
        });


        favoritePick.setOnClickListener(v -> {
            favoritePick.getBackground().setColorFilter(
                    Color.parseColor(SELECT_COL), PorterDuff.Mode.MULTIPLY);
            pickedFavorite = true;
        });

    }
}
