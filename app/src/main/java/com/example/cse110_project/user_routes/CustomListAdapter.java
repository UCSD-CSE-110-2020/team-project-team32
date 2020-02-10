package com.example.cse110_project.user_routes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cse110_project.R;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //private final Integer[] imageIDarray;

    private final String[] nameArray;

    private final String[] infoArray;

    public CustomListAdapter(Activity context, String[] nameArrayParam,
                             String[] infoArrayParam) {

        super(context, R.layout.listview_row, nameArrayParam);

        this.context = context;
        // this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.routeNameScreen);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.routeDetails);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(infoArray[position]);
        // imageView.setImageResource(imageIDarray[position]);

        return rowView;

    };

}
