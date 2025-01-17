package com.example.cse110_project.fitness;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class FitnessServiceFactory {

    private static final String TAG = "[FitnessServiceFactory]";

    private static Map<String, BluePrint> blueprints = new HashMap<>();

    public static void put(String key, BluePrint bluePrint) {
        blueprints.put(key, bluePrint);
    }

    public static FitnessService create(String key, AppCompatActivity stepCountActivity) {
        Log.i(TAG, String.format("creating FitnessService with key %s", key));
        System.out.println(key);
        System.out.println(blueprints.get(key));
        return blueprints.get(key).create(stepCountActivity);
    }

    public interface BluePrint {
        FitnessService create(AppCompatActivity stepCountActivity);
    }
}