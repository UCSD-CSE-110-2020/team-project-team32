package com.example.cse110_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
    private EditText routeNotes; // notes
    private Route route;
    private AlertDialog alert;

    private AppCompatActivity activity;
    private Context context;
    private int steps;
    private LocalTime time;
    private LocalDateTime date;

    private Button loopPick;
    private Button outAndBack;
    private Button FlatPick;
    private Button HillyPick;
    private Button streetPick;
    private Button trailPick;
    private Button EvenPick;
    private Button UnevenPick;
    private Button EasyPick;
    private Button ModeratePick;
    private Button DifficultPick;
    private boolean pickedLoop = false;
    private boolean pickedoutAndBack = false;
    private boolean pickedFlat = false;
    private boolean pickedHilly = false;
    private boolean pickedStreets = false;
    private boolean pickedTrail = false;
    private boolean pickedEven = false;
    private boolean pickedUneven = false;
    private boolean pickedEasy = false;
    private boolean pickedModerate = false;
    private boolean pickedDifficult = false;

    private static final String LOOP = "loop";
    private static final String OUT_AND_BACK = "out and back";
    private static final String FLAT = "Flat";
    private static final String HILLY = "Hilly";
    private static final String STREETS = "Streets";
    private static final String TRAIL = "Trail";
    private static final String EVEN_SURFACE = "Even";
    private static final String UNEVEN_SURFACE = "Uneven";
    private static final String EASY_ROUTE = "Easy";
    private static final String MODERATE_ROUTE = "Moderate";
    private static final String HARD_ROUTE = "Difficult";

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
    public EditText getWalkNotes() { return routeNotes;}

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

        //

        walkName = promptView.findViewById(R.id.NameOfWalkInput);
        routeNotes = promptView.findViewById(R.id.NotesOfWalkInput); // notes
        loopPick = promptView.findViewById(R.id.loopButton);
        outAndBack = promptView.findViewById(R.id.outAndBackButton);
        FlatPick = promptView.findViewById(R.id.FlatButton);
        HillyPick = promptView.findViewById(R.id.HillyButton);
        streetPick = promptView.findViewById(R.id.streetButton);
        trailPick = promptView.findViewById(R.id.trailButton);
        EvenPick = promptView.findViewById(R.id.evenSurfaceButton);
        UnevenPick = promptView.findViewById(R.id.unevenSurfaceButton);
        EasyPick = promptView.findViewById(R.id.EasyButton);
        ModeratePick = promptView.findViewById(R.id.ModerateButton);
        DifficultPick = promptView.findViewById(R.id.DifficultButton);

        validateTextInput();
        validateButtons();

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
        // Only name is required to save; don't check anything else!
        if (walkName.getText().toString().length() == 0) {
             Toast.makeText(context, R.string.invalidWalkName, Toast.LENGTH_SHORT).show();
        } else {
             saveRoute();
             dialog.dismiss();
             goToRouteScreen();
             activity.finish();
        }
        // route notes

    }

    public void saveRoute() {
        route = new Route(0, walkName.getText().toString());
        route.setSteps(steps);
        route.setDuration(time);
        route.setStartDate(date);
        route.setRouteNotes(routeNotes.getText().toString());


        if(pickedLoop) { route.setLoopVSOutBack(LOOP); }
        else if (pickedoutAndBack){ route.setLoopVSOutBack(OUT_AND_BACK);}

        if(pickedFlat){ route.setFlatVSHilly(FLAT);}
        else if(pickedHilly){route.setFlatVSHilly(HILLY);}

        if(pickedStreets) { route.setStreetsVSTrail(STREETS); }
        else if(pickedTrail) { route.setStreetsVSTrail(TRAIL);}

        if(pickedEven) {route.setEvenVsUnevenSurface(EVEN_SURFACE); }
        else if(pickedUneven) { route.setEvenVsUnevenSurface(UNEVEN_SURFACE);}

        if(pickedEasy) {route.setRouteDifficulty(EASY_ROUTE);}
        else if(pickedModerate) {route.setRouteDifficulty(MODERATE_ROUTE); }
        else if(pickedDifficult){route.setRouteDifficulty(HARD_ROUTE); }

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
                } else {
                    walkName.setError(null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    // route notes
        routeNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if (routeNotes.getText().toString().length() == 0) {
                    routeNotes.setError(context.getResources().getString(R.string.emptyWalkName));
                } else {
                    routeNotes.setError(null);
                }*/
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    public void validateButtons() {
        loopPick.setOnClickListener(v -> {
            loopPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            outAndBack.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedoutAndBack = false;
            pickedLoop = true;
        });

        outAndBack.setOnClickListener(v -> {
            outAndBack.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            loopPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedoutAndBack = true;
            pickedLoop = false;
        });

        FlatPick.setOnClickListener(v -> {
            FlatPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            HillyPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedFlat = true;
            pickedHilly = false;
        });

        HillyPick.setOnClickListener(v -> {
            HillyPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            FlatPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedFlat = false;
            pickedHilly = true;
        });

        streetPick.setOnClickListener(v -> {
            streetPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            trailPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedStreets = true;
            pickedTrail = false;
        });

        trailPick.setOnClickListener(v -> {
            trailPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            streetPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedStreets = false;
            pickedTrail = true;
        });

        EvenPick.setOnClickListener(v -> {
            EvenPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            UnevenPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedEven = true;
            pickedUneven = false;
        });

        UnevenPick.setOnClickListener(v -> {
            UnevenPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            EvenPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedEven = false;
            pickedUneven = true;
        });

        EasyPick.setOnClickListener(v -> {
            EasyPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            ModeratePick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            DifficultPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedEasy = true;
            pickedModerate = false;
            pickedDifficult = false;
        });

        ModeratePick.setOnClickListener(v -> {
            ModeratePick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            EasyPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            DifficultPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedEasy = false;
            pickedModerate = true;
            pickedDifficult = false;
        });

        DifficultPick.setOnClickListener(v -> {
            DifficultPick.getBackground().setColorFilter(Color.parseColor("#00bfff"), PorterDuff.Mode.MULTIPLY);
            EasyPick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            ModeratePick.getBackground().setColorFilter(Color.parseColor("#D3D3D3"), PorterDuff.Mode.MULTIPLY);
            pickedEasy = false;
            pickedModerate = false;
            pickedDifficult = true;
        });

    }
}
