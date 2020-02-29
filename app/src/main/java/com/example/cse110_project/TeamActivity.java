package com.example.cse110_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_screen);

        Button backButton = findViewById(R.id.teamHomeButton);
        backButton.setOnClickListener(v -> finish());

        /*final TextView edittextDescription = (TextView) findViewById(R.id.member1);

        if (edittextDescription.getText() != null) {
            String newString = edittextDescription.getText().toString();
            // newString.split("\\s+");
            final TextView setTextDescription = findViewById(R.id.textView2);
            setTextDescription.setText(newString);
        }*/




    }
}
