package com.example.cse110_project.database;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class DatabaseServiceFactory {
    private static final String TAG = DatabaseServiceFactory.class.getSimpleName();

    private Map<String, Blueprint> blueprints = new HashMap<>();

    public void put(String key, Blueprint blueprint) {
        blueprints.put(key, blueprint);
    }

    public DatabaseService create(String key, String chatId) {
        Log.i(TAG, String.format("Creating DatabaseService with key '%s' and chatId '%s'", key, chatId));
        return blueprints.get(key).create(chatId);
    }

    public interface Blueprint {
        DatabaseService create(String chatId);
    }
}
