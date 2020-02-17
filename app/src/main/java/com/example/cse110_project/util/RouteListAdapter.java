package com.example.cse110_project.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;

public class RouteListAdapter extends ArrayAdapter {
    // to reference the Activity
    private final Activity context;

    private final String[] nameArray;
    private final String[] startPtArray;
    private final String[] stepsArray;
    private final String[] milesArray;
    private final String[] timeArray;
    private final String[] dateArray;
    private final String[] flatHillyArray;
    private final String[] loopOutBackArray;
    private final String[] streetsTrailArray;
    private final String[] evenUnevenArray;
    private final String[] difficultyArray;
    private final String[] favArray;

    public RouteListAdapter(Activity context, String[] nameArray, String[] startPtArray,
                            String[] stepsArray, String[] milesArray, String[] timeArray,
                            String[] dateArray, String[] flatHillyArray,
                            String[] streetsTrailArray, String[] loopOutBackArray,
                            String[] evenUnevenArray, String[] difficultyArray,
                            String[] favArray) {
        super(context, R.layout.listview_routes_row, nameArray);

        this.context = context;
        this.nameArray = nameArray;
        this.startPtArray = startPtArray;
        this.stepsArray = stepsArray;
        this.milesArray = milesArray;
        this.timeArray = timeArray;
        this.dateArray = dateArray;
        this.flatHillyArray = flatHillyArray;
        this.loopOutBackArray = loopOutBackArray;
        this.streetsTrailArray = streetsTrailArray;
        this.evenUnevenArray = evenUnevenArray;
        this.difficultyArray = difficultyArray;
        this.favArray = favArray;
    }

    @Override @NonNull
    public View getView (int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_routes_row, null,true);

        //this code gets references to objects in the listview_routes_row.xmlrow.xml file
        TextView nameTextField = rowView.findViewById(R.id.routeRowName);
        TextView startPtTextField = rowView.findViewById(R.id.routeRowStartingPoint);
        TextView stepsTextField = rowView.findViewById(R.id.routeRowSteps);
        TextView milesTextField = rowView.findViewById(R.id.routeRowMiles);
        TextView timeTextField = rowView.findViewById(R.id.routeRowTime);
        TextView dateTextField = rowView.findViewById(R.id.routeRowDate);
        TextView flatHillyTextField = rowView.findViewById(R.id.routeRowFlatHilly);
        TextView loopOutBackTextField = rowView.findViewById(R.id.routeRowLoopOutBack);
        TextView streetsTrailTextField = rowView.findViewById(R.id.routeRowStreetsTrail);
        TextView evenUnevenTextField = rowView.findViewById(R.id.routeRowEvenUneven);
        TextView difficultyTextField = rowView.findViewById(R.id.routeRowDifficulty);
        TextView favTextField = rowView.findViewById(R.id.routeRowFavorite);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        startPtTextField.setText(startPtArray[position]);
        flatHillyTextField.setText(flatHillyArray[position]);
        loopOutBackTextField.setText(loopOutBackArray[position]);
        streetsTrailTextField.setText(streetsTrailArray[position]);
        evenUnevenTextField.setText(evenUnevenArray[position]);
        difficultyTextField.setText(difficultyArray[position]);
        favTextField.setText(favArray[position]);

        // Only fill in steps, miles, etc. if route previously walked
        if (dateArray[position] != null) {
            stepsTextField.setText(stepsArray[position]);
            milesTextField.setText(milesArray[position]);
            timeTextField.setText(timeArray[position]);
            dateTextField.setText(dateArray[position]);
        }

        return rowView;
    }
}
