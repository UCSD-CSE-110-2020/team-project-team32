package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RouteDetails extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_details);

        String savedExtra = getIntent().getStringExtra("animal");
        TextView myText = (TextView) findViewById(R.id.routeNameDetail);
        myText.setText(savedExtra);

        final Button launchToRouteScreen = (Button) findViewById(R.id.button_backToRoutes);

        launchToRouteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchToRouteActivity();
            }
        });
    }

    public void launchToRouteActivity() {
        Intent intent = new Intent(this, RouteScreen.class);
        startActivity(intent);
    }

}
