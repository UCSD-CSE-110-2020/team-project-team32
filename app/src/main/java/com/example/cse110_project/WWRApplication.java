package com.example.cse110_project;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.database.DatabaseListener;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MapsIntentBuilder;
import com.example.cse110_project.util.Notifier;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class WWRApplication extends Application {
    public static final String CHANNEL_ID = "WWR_NOTIFICATION_CHANNEL";
    private static User user;
    private static MapsIntentBuilder mapsIntentBuilder;
    private static FitnessService fitnessService;

    private static DatabaseService database;
    private static DatabaseListener dbListener;

    // Used to launch push  notifications
    private static Notifier notifier;
    private static int notificationId;

    // Track current "system" time
    private static LocalTime time;
    private static LocalDateTime date;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User(getApplicationContext());
        mapsIntentBuilder = new MapsIntentBuilder();

        notifier = new Notifier(getApplicationContext());
        notificationId = 0;
        time = null;
        date = null;

        createNotificationChannel();
    }

    // Creates the notification channel needed to push notifications from the app
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

    public static User getUser() { return user; }
    public static Notifier getNotifier() { return notifier; }

    public static int getNotificationId() { return notificationId; }
    public static void incrementNotificationId() { notificationId++; }

    public static boolean hasFitnessService() { return fitnessService != null; }
    public static FitnessService getFitnessService() { return fitnessService; }
    public static void setUpFitnessService(String key, AppCompatActivity activity) {
        fitnessService = FitnessServiceFactory.create(key, activity);
        fitnessService.setup();
    }

    public static MapsIntentBuilder getMapsIntentBuilder() { return mapsIntentBuilder; }
    public static void setMapsIntentBuilder(MapsIntentBuilder med) { mapsIntentBuilder = med; }

    public static boolean hasDatabase() { return database != null; }
    public static DatabaseService getDatabase() { return database; }
    public static void setDatabase(DatabaseService dbService) {
        database = dbService;
        dbListener = new DatabaseListener(user, database);
    }

    // Date-time methods

    public static LocalTime getTime() {
        return time == null ? LocalTime.now() : time;
    }

    public static LocalDateTime getDate() {
        return date == null ? LocalDateTime.now() : date;
    }

    public static void setTime(LocalTime t) { time = t; }
    public static void setDate(LocalDateTime d) { date = d; }
}
