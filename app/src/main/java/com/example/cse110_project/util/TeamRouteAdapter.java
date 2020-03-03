package com.example.cse110_project.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class TeamRouteAdapter extends ArrayAdapter {

    private final Activity context;

    private final String[] nameArray;
    private final List<TeamRoute> routes;
    private final static String MONTH_DAY_SEPARATOR = " ";
    private User user; // for now used to get height to calculate miles


    public TeamRouteAdapter(Activity context, String[] nameArray, List<TeamRoute> routes) {
        super(context, R.layout.listview_teamroutes_row, nameArray);

        this.context = context;
        this.nameArray = nameArray;
        this.routes = routes;
    }

    @Override @NonNull
    public View getView (int position, View view, ViewGroup parent) {
        user = WWRApplication.getUser();
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_teamroutes_row, null,true);

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
        TextView teammateInitials = rowView.findViewById(R.id.memberInitial);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(routes.get(position).getRoute().getName());
        startPtTextField.setText(routes.get(position).getRoute().getStartingPoint());
        flatHillyTextField.setText(routes.get(position).getRoute().getFlatVsHilly());
        loopOutBackTextField.setText(routes.get(position).getRoute().getLoopVsOutBack());
        streetsTrailTextField.setText(routes.get(position).getRoute().getStreetsVsTrail());
        evenUnevenTextField.setText(routes.get(position).getRoute().getEvenVsUneven());
        difficultyTextField.setText(routes.get(position).getRoute().getDifficulty());
        favTextField.setText(routes.get(position).getRoute().isFavorite() ? Route.FAV : Route.NO_DATA);

        String[] initialsArr = routes.get(position).getMemberName().split(" ");
        StringBuilder initials = new StringBuilder();
        for (String initial : initialsArr) {
            if (initial.length() > 0) {
                initials.append(initial.charAt(0));
            }
        }
        teammateInitials.setText(initials.toString());

        // Only fill in steps, miles, etc. if route previously walked
        if (routes.get(position).getRoute().getStartDate() != null) {
            stepsTextField.setText(String.valueOf(routes.get(position).getRoute().getSteps()));
            milesTextField.setText(MilesCalculator.formatMiles(routes.get(position).getRoute().getMiles(user.getHeight())));
            timeTextField.setText(routes.get(position).getRoute().getDuration().truncatedTo(ChronoUnit.MINUTES).toString());
            dateTextField.setText(routes.get(position).getRoute().getStartDate().getMonth() + MONTH_DAY_SEPARATOR
                    + routes.get(position).getRoute().getStartDate().getDayOfMonth());
        }

        return rowView;
    }
}
