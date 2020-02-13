package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cse110_project.user_routes.CustomListAdapter;

public class RouteScreen extends AppCompatActivity{

    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };

    String[] infoArray = {
            "8 tentacled monster",
            "Delicious in rolls",
            "Great for jumpers",
            "Nice in a stew",
            "Great for shoes",
            "Scary."
    };

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_screen);

        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray);
        listView = findViewById(R.id.listviewID);
        listView.setAdapter(whatever);


        //listView = (ListView) findViewById(R.id.route_list_view);
        // Implementation of button event to route screen
        final Button launchToHomeScreen = findViewById(R.id.button_routeToHome);

        launchToHomeScreen.setOnClickListener(view -> finish());

    }
}