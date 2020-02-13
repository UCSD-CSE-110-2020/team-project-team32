package com.example.cse110_project.fitness_api;


import androidx.appcompat.app.AppCompatActivity;

public interface FitnessService {
    int getRequestCode();
    void setup();
    void updateStepCount();
}