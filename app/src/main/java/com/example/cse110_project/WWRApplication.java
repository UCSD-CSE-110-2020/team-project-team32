package com.example.cse110_project;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.Notifier;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class WWRApplication extends Application {
    public static final String CHANNEL_ID = "WWR_NOTIFICATION_CHANNEL";
    private static User user;
    private static FitnessService fitnessService;
    private static DatabaseService database;

    private static Notifier notifier;
    private static int notificationId;

    // Track current "system" time (mockable)
    private static LocalTime time;
    private static LocalDateTime date;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User(getApplicationContext());
        notifier = new Notifier(getApplicationContext());
        notificationId = 0;
        time = null;
        date = null;

        createNotificationChannel();
    }

    // https://developer.android.com/training/notify-user/build-notification
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static int getNotificationId() {
        return notificationId;
    }

    public static void incrementNotificationId() {
        notificationId++;
    }

    public static User getUser() { return user; }
    public static Notifier getNotifier() { return notifier; }

    public static boolean hasDatabase() { return database != null; }
    public static DatabaseService getDatabase() { return database; }
    public static void setDatabase(DatabaseService dbService) { database = dbService; }

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
