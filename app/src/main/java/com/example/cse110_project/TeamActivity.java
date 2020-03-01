package com.example.cse110_project;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;
import com.example.cse110_project.util.TeamListAdapter;


public class TeamActivity extends AppCompatActivity {
    private EditText emailEditor;
    private EditText nickNameEditor;

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

        Button AddMember = findViewById(R.id.teamAddMember);
        AddMember.setOnClickListener((v -> launchAddEntry()));

        TeamListAdapter memberAdapter = new TeamListAdapter(this, members, teamMember);

        ListView listMembers = findViewById(R.id.listviewIDMember);
        listMembers.setAdapter(memberAdapter);
    }

    public AlertDialog launchAddEntry() {
        // Get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(TeamActivity.this);
        View promptView = layoutInflater.inflate(R.layout.add_member_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TeamActivity.this)
                .setCancelable(false)
                .setPositiveButton(R.string.sendInvite, null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        emailEditor = promptView.findViewById(R.id.AddMemberEmail);
        nickNameEditor = promptView.findViewById(R.id.AddMemberNickName);

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(v -> ValidateOnSendInvite(alert));

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> alert.dismiss());
        return alert;
    }

    public void ValidateOnSendInvite(AlertDialog dialog) {
        int emailEditorLength = emailEditor.getText().toString().length();
        int NickNameEditorLength = nickNameEditor.getText().toString().length();

        if(emailEditorLength == 0 || NickNameEditorLength == 0) {
            Toast.makeText(this, R.string.InvalidInvite, Toast.LENGTH_SHORT).show();
        } else {
            TeamMember member = new TeamMember(nickNameEditor.getText().toString(),
                    emailEditor.getText().toString(), Color.YELLOW);
            WWRApplication.getUser().getTeam().inviteMember(member);
            dialog.dismiss();
        }

    }

}
