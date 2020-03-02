package com.example.cse110_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamMember;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.user_routes.UserData;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.util.TeamListAdapter;

import java.util.ArrayList;
import java.util.List;


public class TeamActivity extends AppCompatActivity {
    private EditText emailEditor;
    private EditText nickNameEditor;
    private Team getTeam;
    private List<TeamMember> members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_screen);

        Button backButton = findViewById(R.id.teamHomeButton);
        backButton.setOnClickListener(v -> finish());

        Button newMemberButton = findViewById(R.id.teamAddMember);
        newMemberButton.setOnClickListener((v -> launchAddEntry()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        createTeamView();
    }

    public AlertDialog launchAddEntry() {
        // Get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(TeamActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_invite_member, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TeamActivity.this)
                .setCancelable(false)
                .setPositiveButton(R.string.sendInvite, null)
                .setNegativeButton(R.string.cancelButton, null);
        alertDialogBuilder.setView(promptView);

        // Create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        emailEditor = promptView.findViewById(R.id.inviteEmailInput);
        nickNameEditor = promptView.findViewById(R.id.inviteNameInput);

        Button submitButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setId(R.id.sendInviteButton);
        submitButton.setOnClickListener(v -> ValidateOnSendInvite(alert));

        Button cancelButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(v -> alert.dismiss());
        return alert;
    }

    public void ValidateOnSendInvite(AlertDialog dialog) {
        int emailEditorLength = emailEditor.getText().toString().length();
        int NickNameEditorLength = nickNameEditor.getText().toString().length();

        if(emailEditorLength == 0 || NickNameEditorLength == 0) {
            Toast.makeText(this, R.string.InvalidInvite, Toast.LENGTH_SHORT)
                    .show();
        } else {
            TeamMember member = new TeamMember(nickNameEditor.getText().toString(),
                    emailEditor.getText().toString(), Color.YELLOW);
            WWRApplication.getUser().getTeam().inviteMember(this, member);
            createTeamView();
            dialog.dismiss();
        }
    }

    public void createTeamView() {
        // Retrieve team members excluding current user
        List<TeamMember> members = new ArrayList<>(WWRApplication.getUser().getTeam().getMembers());
        for (int i = members.size() - 1; i >= 0; i--) {
            if (members.get(i).getEmail().equals(WWRApplication.getUser().getEmail())) {
                members.remove(i);
            }
        }

        System.out.println("Number of members: " + members.size());

        if (members.size() != 0) {
            String[] memberNames = new String[members.size()];
            for(int i=0; i < members.size(); i++) {
                memberNames[i] = members.get(i).getName();
            }

            TeamListAdapter memberAdapter = new TeamListAdapter(this, memberNames, members);
            ListView listMembers = findViewById(R.id.listviewIDMember);
            listMembers.setAdapter(memberAdapter);
        }
    }
}
