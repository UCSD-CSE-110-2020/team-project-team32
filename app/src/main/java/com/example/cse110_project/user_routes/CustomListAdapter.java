package com.example.cse110_project.user_routes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;

import org.w3c.dom.Text;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //private final Integer[] imageIDarray;

    private final String[] nameArray;
    private final String[] startPtArray;
    private final String[] stepsArray;
    private final String[] milesArray;
    private final String[] timeArray;
    private final String[] dateArray;
    private final String[] FlatVsHilly;
    private final String[] LoopVsOutBack;
    private final String[] StreetVsTrail;
    private final String[] EvenVsUneven;
    private final String[] Difficulty;

    public CustomListAdapter (Activity context, String[] nameArrayParam, String[] startPtArray,
                              String[] stepsArray, String[] milesArray, String[] timeArray,
                              String[] dateArray, String[] FlatVsHillyArrayParam,
                              String[] StreetVsTrailArrayparam, String[] LoopVsoutBackArrayparam,
                              String [] EvenVsUnevenArrayparam, String[] DifficultyArrayparam) {

        super(context, R.layout.listview_row, nameArrayParam);

        this.context = context;
        // this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.startPtArray = startPtArray;
        this.stepsArray = stepsArray;
        this.milesArray = milesArray;
        this.timeArray = timeArray;
        this.dateArray = dateArray;
        this.FlatVsHilly = FlatVsHillyArrayParam;
        this.LoopVsOutBack = LoopVsoutBackArrayparam;
        this.StreetVsTrail = StreetVsTrailArrayparam;
        this.EvenVsUneven = EvenVsUnevenArrayparam;
        this.Difficulty = DifficultyArrayparam;
    }

    @NonNull
    public View getView (int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.routeNameScreen);
        TextView startPtTextField = rowView.findViewById(R.id.routeRowStartingPoint);
        TextView stepsTextField = rowView.findViewById(R.id.routeRowSteps);
        TextView milesTextField = rowView.findViewById(R.id.routeRowMiles);
        TextView timeTextField = rowView.findViewById(R.id.routeRowTime);
        TextView dateTextField = rowView.findViewById(R.id.routeRowDate);
        TextView FlatVsHillyTextField = rowView.findViewById(R.id.FlatVsHilly);
        TextView LoopVsOutBackTextField = rowView.findViewById(R.id.LoopVsOutBack);
        TextView StreetVsTrailTextField = rowView.findViewById(R.id.StreetVSTrail);
        TextView EvenVsUnevenTextField = rowView.findViewById(R.id.EvenVsUnEven);
        TextView DifficultyTextField = rowView.findViewById(R.id.Difficulty);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        startPtTextField.setText(startPtArray[position]);
        FlatVsHillyTextField.setText(FlatVsHilly[position]);
        LoopVsOutBackTextField.setText(LoopVsOutBack[position]);
        StreetVsTrailTextField.setText(StreetVsTrail[position]);
        EvenVsUnevenTextField.setText(EvenVsUneven[position]);
        DifficultyTextField.setText(Difficulty[position]);
        // imageView.setImageResource(imageIDarray[position]);

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
