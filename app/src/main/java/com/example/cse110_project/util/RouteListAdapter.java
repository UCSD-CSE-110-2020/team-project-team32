package com.example.cse110_project.util;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.user_routes.UserRoute;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class RouteListAdapter extends ArrayAdapter {
    // to reference the Activity
    private final Activity context;

    private final String[] nameArray;
    private final RouteList routes;
    private final static String MONTH_DAY_SEPARATOR = " ";
    private User user;


    public RouteListAdapter(Activity context, String[] nameArray, RouteList routes) {
        super(context, R.layout.listview_routes_row, nameArray);

        this.context = context;
        this.nameArray = nameArray;
        this.routes = routes;
    }

    @Override @NonNull
    public View getView (int position, View view, ViewGroup parent) {
        user = WWRApplication.getUser();
        LayoutInflater inflater = context.getLayoutInflater();
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
        Button favButtonField = rowView.findViewById(R.id.routeRowFavorite);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(routes.getRoute(position).getName());
        startPtTextField.setText(routes.getRoute(position).getStartingPoint());
        flatHillyTextField.setText(routes.getRoute(position).getFlatVsHilly());
        loopOutBackTextField.setText(routes.getRoute(position).getLoopVsOutBack());
        streetsTrailTextField.setText(routes.getRoute(position).getStreetsVsTrail());
        evenUnevenTextField.setText(routes.getRoute(position).getEvenVsUneven());
        difficultyTextField.setText(routes.getRoute(position).getDifficulty());
        favButtonField.setBackgroundColor(routes.getRoute(position).isFavorite() ? Route.FAV_COLOR
                : Route.UNFAV_COLOR);

        favButtonField.setOnClickListener(v -> {
            if (routes.getRoute(position).isFavorite()){
                favButtonField.setBackgroundColor(Route.UNFAV_COLOR);
                routes.setRouteFavorite(routes.getRoute(position).getID(), false);
            }
            else {
                favButtonField.setBackgroundColor(Route.FAV_COLOR);
                routes.setRouteFavorite(routes.getRoute(position).getID(), true);
            }

        });

        // Only fill in steps, miles, etc. if route previously walked
        if (routes.getRoute(position).getStartDate() != null) {
            stepsTextField.setText(String.valueOf(routes.getRoute(position).getSteps()));
            milesTextField.setText(MilesCalculator.formatMiles(routes.getRoute(position).getMiles(user.getHeight())));
            timeTextField.setText(routes.getRoute(position).getDuration().truncatedTo(ChronoUnit.MINUTES).toString());
            dateTextField.setText(routes.getRoute(position).getStartDate().getMonth() + MONTH_DAY_SEPARATOR
                    + routes.getRoute(position).getStartDate().getDayOfMonth());
        }

        return rowView;
    }
}
