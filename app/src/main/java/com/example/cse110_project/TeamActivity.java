package com.example.cse110_project;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;
import com.example.cse110_project.util.TeamListAdapter;

public class TeamActivity extends AppCompatActivity {
    private String[] members = {"Reesha Rajen", "Noor Bdairat"};
    private TeamMember member1 = new TeamMember("Reesha Rajen", "rrajen@ucsd.edu", Color.valueOf(Color.BLACK));
    private TeamMember member2 = new TeamMember("Noor Bdairat", "nbdairat@ucsd.edu", Color.valueOf(Color.GREEN));
    private TeamMember[] teamMember = {member1, member2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_screen);

        Button backButton = findViewById(R.id.teamHomeButton);
        backButton.setOnClickListener(v -> finish());

        TeamListAdapter memberAdapter = new TeamListAdapter(this, members, teamMember);

        ListView listMembers = findViewById(R.id.listviewIDMember);
        listMembers.setAdapter(memberAdapter);

    }

}
