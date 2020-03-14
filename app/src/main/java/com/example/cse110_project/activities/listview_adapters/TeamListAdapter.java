package com.example.cse110_project.activities.listview_adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.cse110_project.R;
import com.example.cse110_project.team.TeamMember;

import java.util.List;

public class TeamListAdapter extends ArrayAdapter {
    private final static String TAG = TeamListAdapter.class.getSimpleName();

    private final Activity context;
    private final String[] names;
    private final List<TeamMember> members;

    public TeamListAdapter(Activity context, String[] names, List<TeamMember> members){
        super(context, R.layout.listview_members_row, names);
        this.context = context;
        this.names = names;
        this.members = members;
    }

    @Override @NonNull
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_members_row, null, true);
        Log.d(TAG, "Displaying member at position " + position + " with name " + names[position]);

        TextView nameTextField = rowView.findViewById(R.id.teamRowName);
        TextView initialsTextField = rowView.findViewById(R.id.teamRowInitials);

        nameTextField.setText(names[position]);
        initialsTextField.setBackgroundColor(members.get(position).getColor());
        initialsTextField.setText(members.get(position).retrieveInitials());

        // Account for pending team member
        if (members.get(position).getStatus() == TeamMember.STATUS_PENDING) {
            nameTextField.setTextAppearance(R.style.pendingText);
        }

        return rowView;
    }
}
