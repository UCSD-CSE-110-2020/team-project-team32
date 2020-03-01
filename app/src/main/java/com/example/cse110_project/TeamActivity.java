package com.example.cse110_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.user_routes.Team;

public class TeamActivity extends AppCompatActivity {
    private EditText emailEditor;
    private EditText nickNameEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_screen);

        Button backButton = findViewById(R.id.teamHomeButton);
        backButton.setOnClickListener(v -> finish());

        Button AddMember = findViewById(R.id.teamAddMember);
        AddMember.setOnClickListener((v -> launchAddEntry()));
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
        cancelButton.setOnClickListener(v -> {
            alert.dismiss();
        });
        return alert;
    }

    public void ValidateOnSendInvite(AlertDialog dialog) {
        int emailEditorLength = emailEditor.getText().toString().length();
        int NickNameEditorLength = nickNameEditor.getText().toString().length();

        if(emailEditorLength == 0 || NickNameEditorLength == 0) {
            Toast.makeText(this, R.string.InvalidInvite, Toast.LENGTH_SHORT).show();
        }
        else {
            dialog.dismiss();
        }

        /*final TextView edittextDescription = (TextView) findViewById(R.id.member1);

        if (edittextDescription.getText() != null) {
            String newString = edittextDescription.getText().toString();
            // newString.split("\\s+");
            final TextView setTextDescription = findViewById(R.id.textView2);
            setTextDescription.setText(newString);
        }*/

    }
}
