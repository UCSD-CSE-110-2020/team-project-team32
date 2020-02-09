package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;
import com.example.cse110_project.fitness_api.GoogleFitAdapter;

public class EntryActivity extends AppCompatActivity {
    private String fitnessServiceKey = "GOOGLE_FIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(MainActivity homeActivity) {
                return new GoogleFitAdapter(homeActivity);
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY, fitnessServiceKey);
        System.out.println("Key should be added: " + MainActivity.FITNESS_SERVICE_KEY + " to " +
                fitnessServiceKey + " : " + intent.getStringExtra(MainActivity.FITNESS_SERVICE_KEY));
        startActivity(intent);
    }
}
