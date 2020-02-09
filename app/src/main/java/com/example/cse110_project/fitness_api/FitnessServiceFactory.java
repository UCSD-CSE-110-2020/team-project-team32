package com.example.cse110_project.fitness_api;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.example.cse110_project.MainActivity;

public class FitnessServiceFactory {

    private static final String TAG = "[FitnessServiceFactory]";

    private static Map<String, BluePrint> blueprints = new HashMap<>();

    public static void put(String key, BluePrint bluePrint) {
        blueprints.put(key, bluePrint);
    }

    public static FitnessService create(String key, MainActivity stepCountActivity) {
        Log.i(TAG, String.format("creating FitnessService with key %s", key));
        System.out.println(key);
        System.out.println(blueprints.get(key));
        return blueprints.get(key).create(stepCountActivity);
    }

    public interface BluePrint {
        FitnessService create(MainActivity stepCountActivity);
    }
}