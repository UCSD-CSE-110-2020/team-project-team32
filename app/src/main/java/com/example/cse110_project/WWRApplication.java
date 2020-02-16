package com.example.cse110_project;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class WWRApplication extends Application {
    private static User user;
    private static FitnessService fitnessService;

    // Track current "system" time (mockable)
    private static LocalTime time;
    private static LocalDateTime date;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User(getApplicationContext());
        time = null;
        date = null;
    }

    public static User getUser() { return user; }

    public static boolean hasFitnessService() { return fitnessService != null; }

    public static FitnessService getFitnessService() { return fitnessService; }

    public static void setUpFitnessService(String key, AppCompatActivity activity) {
        fitnessService = FitnessServiceFactory.create(key, activity);
        fitnessService.setup();
    }

    // Date-time methods

    public static LocalTime getTime() {
        if (time == null) {
            return LocalTime.now();
        } else {
            return time;
        }
    }

    public static LocalDateTime getDate() {
        if (date == null) {
            return LocalDateTime.now();
        } else {
            return date;
        }
    }

    public static void setTime(LocalTime t) { time = t; }

    public static void setDate(LocalDateTime d) { date = d; }

}
