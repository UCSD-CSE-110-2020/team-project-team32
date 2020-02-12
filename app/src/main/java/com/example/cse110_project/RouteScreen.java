package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.cse110_project.user_routes.CustomListAdapter;

public class RouteScreen extends AppCompatActivity{

    String[] nameArray = {"Canyon Run","Blacks Beach","Torrey Pines","Hill Run","Rancho Bernardo","Miramar" };

    String[] infoArray = {
            "Rocky",
            "Flat",
            "Hilly",
            "Hilly",
            "Great for shoes",
            "Flat"
    };

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_screen);

        CustomListAdapter whatever = new CustomListAdapter(this, nameArray, infoArray);
        listView = (ListView) findViewById(R.id.listviewID);
        listView.setAdapter(whatever);


        //listView = (ListView) findViewById(R.id.route_list_view);
        // Implementation of button event to route screen
        final Button launchToHomeScreen = (Button) findViewById(R.id.button_routeToHome);

        launchToHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchToHomeActivity();
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(RouteScreen.this, RouteDetails.class);
                String message = nameArray[position];
                intent.putExtra("animal", message);
                startActivity(intent);
            }
        });



    }

    public void launchToHomeActivity() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }
}