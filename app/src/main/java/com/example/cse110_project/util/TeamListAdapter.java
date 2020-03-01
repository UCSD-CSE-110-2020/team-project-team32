package com.example.cse110_project.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;
import com.example.cse110_project.user_routes.TeamMember;

public class TeamListAdapter extends ArrayAdapter {
    private final Activity context;

    private final String[] names;
    private final TeamMember[] memberArray;

    public TeamListAdapter(Activity context, String[] names, TeamMember[] memberArray){
        super(context, R.layout.listview_members_row, names);
        this.context = context;
        this.names = names;
        this.memberArray = memberArray;

    }

    @Override @NonNull
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_members_row, null, true);

        TextView nameTextField = rowView.findViewById(R.id.textView);
        //TextView initalsTextField = rowView.findViewById(R.id.textView2);

        nameTextField.setText(names[position]);
        System.out.println("Names at position should be" + names[position]);
        return rowView;
    }
}
