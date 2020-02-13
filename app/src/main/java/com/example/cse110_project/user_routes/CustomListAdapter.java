package com.example.cse110_project.user_routes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cse110_project.R;

import org.w3c.dom.Text;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //private final Integer[] imageIDarray;

    private final String[] nameArray;
    private final String[] FlatVsHilly;
    private final String[] LoopVsOutBack;
    private final String[] StreetVsTrail;
    private final String[] EvenVsUneven;
    private final String[] Difficulty;

    public CustomListAdapter(Activity context, String[] nameArrayParam, String[] FlatVsHillyArrayParam,
                             String [] StreetVsTrailArrayparam,String[] LoopVsoutBackArrayparam,
                             String [] EvenVsUnevenArrayparam, String[] DifficultyArrayparam) {

        super(context, R.layout.listview_row, nameArrayParam);

        this.context = context;
        // this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.FlatVsHilly = FlatVsHillyArrayParam;
        this.LoopVsOutBack = LoopVsoutBackArrayparam;
        this.StreetVsTrail = StreetVsTrailArrayparam;
        this.EvenVsUneven = EvenVsUnevenArrayparam;
        this.Difficulty = DifficultyArrayparam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.routeNameScreen);
        TextView FlatVsHillyTextField = (TextView) rowView.findViewById(R.id.FlatVsHilly);
        TextView LoopVsOutBackTextField = (TextView) rowView.findViewById(R.id.LoopVsOutBack);
        TextView StreetVsTrailTextField = (TextView) rowView.findViewById(R.id.StreetVSTrail);
        TextView EvenVsUnevenTextField = (TextView) rowView.findViewById(R.id.EvenVsUnEven);
        TextView DifficultyTextField = (TextView) rowView.findViewById(R.id.Difficulty);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        FlatVsHillyTextField.setText(FlatVsHilly[position]);
        LoopVsOutBackTextField.setText(LoopVsOutBack[position]);
        StreetVsTrailTextField.setText(StreetVsTrail[position]);
        EvenVsUnevenTextField.setText(EvenVsUneven[position]);
        DifficultyTextField.setText(Difficulty[position]);
        // imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };

}
