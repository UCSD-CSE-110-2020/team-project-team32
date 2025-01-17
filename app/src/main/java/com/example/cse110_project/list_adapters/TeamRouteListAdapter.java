package com.example.cse110_project.list_adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class TeamRouteListAdapter extends ArrayAdapter {
    private final Activity context;

    private final String[] nameArray;
    private final List<TeamRoute> routes;
    private final static String MONTH_DAY_SEPARATOR = " ";
    private User user; // for now used to get height to calculate miles


    public TeamRouteListAdapter(Activity context, String[] nameArray, List<TeamRoute> routes) {
        super(context, R.layout.listview_teamroutes_row, nameArray);

        this.context = context;
        this.nameArray = nameArray;
        this.routes = routes;
    }

    @Override @NonNull
    public View getView (int position, View view, ViewGroup parent) {
        System.out.println("getting view");
        user = WWRApplication.getUser();
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_teamroutes_row, null,true);

        //this code gets references to objects in the listview_routes_row.xmlrow.xml file
        TextView nameTextField = rowView.findViewById(R.id.teamRoutesRowName);
        TextView startPtTextField = rowView.findViewById(R.id.teamRoutesRowStartingPoint);
        TextView stepsTextField = rowView.findViewById(R.id.teamRoutesRowSteps);
        TextView milesTextField = rowView.findViewById(R.id.teamRoutesRowMiles);
        TextView timeTextField = rowView.findViewById(R.id.teamRoutesRowTime);
        TextView dateTextField = rowView.findViewById(R.id.teamRoutesRowDate);
        TextView flatHillyTextField = rowView.findViewById(R.id.teamRoutesRowFlatHilly);
        TextView loopOutBackTextField = rowView.findViewById(R.id.teamRoutesRowLoopOutBack);
        TextView streetsTrailTextField = rowView.findViewById(R.id.teamRoutesRowStreetsTrail);
        TextView evenUnevenTextField = rowView.findViewById(R.id.teamRoutesRowEvenUneven);
        TextView difficultyTextField = rowView.findViewById(R.id.teamRoutesRowDifficulty);
        Button favButtonField = rowView.findViewById(R.id.teamRoutesRowFavorite);
        TextView teammateInitials = rowView.findViewById(R.id.teamRoutesRowInitials);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(routes.get(position).getRoute().getName());
        startPtTextField.setText(routes.get(position).getRoute().getStartingPoint());
        flatHillyTextField.setText(routes.get(position).getRoute().getFlatVsHilly());
        loopOutBackTextField.setText(routes.get(position).getRoute().getLoopVsOutBack());
        streetsTrailTextField.setText(routes.get(position).getRoute().getStreetsVsTrail());
        evenUnevenTextField.setText(routes.get(position).getRoute().getEvenVsUneven());
        difficultyTextField.setText(routes.get(position).getRoute().getDifficulty());

        teammateInitials.setText(routes.get(position).getCreator().retrieveInitials());
        teammateInitials.setBackgroundColor(routes.get(position).getCreator().getColor());

        favButtonField.setBackgroundColor(routes.get(position).isFavorite() ? Route.FAV_COLOR
                : Route.UNFAV_COLOR);

        // Icon
        ImageView walkedIcon = rowView.findViewById(R.id.teamWalkedIcon);

        if (routes.get(position).hasWalkData()) {
            walkedIcon.setVisibility(View.VISIBLE);
        } else {
            walkedIcon.setVisibility(View.INVISIBLE);
        }

        favButtonField.setOnClickListener(v -> {
            if (routes.get(position).isFavorite()){
                favButtonField.setBackgroundColor(Route.UNFAV_COLOR);
                user.setTeamRouteFavorite(routes.get(position), false);
            } else {
                favButtonField.setBackgroundColor(Route.FAV_COLOR);
                user.setTeamRouteFavorite(routes.get(position), true);
            }
        });

        // Only fill in steps, miles, etc. if route previously walked
        if (routes.get(position).getRoute().getStartDate() != null) {
            System.out.println("Filling data for team route at pos " + position);
            stepsTextField.setText(String.valueOf(routes.get(position).getRoute().getSteps()));
            milesTextField.setText(MilesCalculator.formatMiles(
                    routes.get(position).getRoute().getMiles(user.getHeight())));

            timeTextField.setText(routes.get(position).getRoute().getDuration()
                    .truncatedTo(ChronoUnit.MINUTES).toString());
            dateTextField.setText(routes.get(position).getRoute().getStartDate().getMonth()
                    + MONTH_DAY_SEPARATOR
                    + routes.get(position).getRoute().getStartDate().getDayOfMonth());
        }

        return rowView;
    }
}
