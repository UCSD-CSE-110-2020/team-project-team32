package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RouteDetails extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_details);

        String savedExtra = getIntent().getStringExtra("animal");
        TextView myText = (TextView) findViewById(R.id.routeNameDetail);
        myText.setText(savedExtra);

        final Button launchToRouteScreen = (Button) findViewById(R.id.button_backToRoutes);
        final ImageButton markAsFavorite = (ImageButton) findViewById(R.id.button_favorites);

        launchToRouteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchToRouteActivity();
            }
        });

        // User marks route as favorite
        markAsFavorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                launchToFavorites();
            }
        });
    }

    public void launchToRouteActivity() {
        Intent intent = new Intent(this, RouteScreen.class);
        startActivity(intent);
    }

    // This is a Toast message that displays whenever Favorites button is pressed
    public void launchToFavorites() {
        Toast.makeText(getApplicationContext(),
                "Added to Favorites!",
                Toast.LENGTH_LONG)
        .show();
    }

}
