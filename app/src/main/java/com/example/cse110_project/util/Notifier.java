package com.example.cse110_project.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.cse110_project.activities.EntryActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.local_data.TeamData;
import com.example.cse110_project.team.ScheduledWalk;

import java.util.Map;

// See https://developer.android.com/training/notify-user/build-notification for code source
public class Notifier {
    private static String TAG = Notifier.class.getSimpleName();
    private Context context;

    public Notifier(Context context) {
        this.context = context;
    }

    public void notifyOnWalkProposed(ScheduledWalk walk) {
        if ( ! walkChangeKnown(walk)) {
            Log.d(TAG, "Notifying of proposed walk");
            String title = walk.getCreatorId() + " proposed a walk";
            String content = walk.retrieveRoute().getName() + " at " + walk.getDateTimeStr();
            launchNotification(title, content);
        }
    }

    public void notifyOnWalkScheduled(ScheduledWalk walk) {
        if ( ! walkChangeKnown(walk)) {
            Log.d(TAG, "Notifying of scheduled walk");
            String title = walk.getCreatorId() + " scheduled a walk";
            String content = walk.retrieveRoute().getName() + " at " + walk.getDateTimeStr();
            launchNotification(title, content);
        }
    }

    public void notifyOnWalkWithdrawn(ScheduledWalk walk) {
        if ( ! walk.getCreatorId().equals(WWRApplication.getUser().getEmail())) {
            Log.d(TAG, "Notifying of withdrawn walk");
            String title = walk.getCreatorId() + " withdrew a walk";
            String content = walk.retrieveRoute().getName() + " at " + walk.getDateTimeStr();
            launchNotification(title, content);
        }
    }

    private boolean walkChangeKnown(ScheduledWalk walk) {
        Context context = WWRApplication.getUser().getContext();
        boolean result = walk.getCreatorId().equals(WWRApplication.getUser().getEmail()) ||
                (TeamData.retrieveTeamWalkDocId(context).equals(walk.getRouteAdapter().getDocID())
                        && TeamData.retrieveTeamWalkStatus(context) == walk.getStatus());
        Log.d(TAG, "walkChangeKnown: " + result);
        return result;
    }

    public void notifyOnWalkResponseChange(ScheduledWalk prevWalk, ScheduledWalk nextWalk) {
        Log.d(TAG, "Notifying of response change for walk");
        Map.Entry<String, Integer> diff = parseResponseChange(prevWalk, nextWalk);
        if (diff == null) {
            Log.e(TAG, "No response change found");
            return;
        }

        String title = diff.getKey() + " responded: " + prevWalk.parseIntResponse(diff.getValue());
        String content = nextWalk.retrieveRoute().getName() + " at " + nextWalk.getDateTimeStr();
        launchNotification(title, content);
    }

    private void launchNotification(String title, String content) {
        Intent intent = new Intent(context, EntryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, WWRApplication.CHANNEL_ID)
                        .setSmallIcon(R.drawable.background_splash)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(WWRApplication.getNotificationId(), builder.build());
        WWRApplication.incrementNotificationId();
    }

    private Map.Entry<String, Integer> parseResponseChange(ScheduledWalk prevWalk,
                                                           ScheduledWalk nextWalk) {
        for (Map.Entry<String, Integer> entry : nextWalk.getResponses().entrySet()) {
            if (entry.getValue() != null &&
                    ! entry.getValue().equals(prevWalk.getResponses().get(entry.getKey()))) {
                return entry;
            }
        }

        return null;
    }
}
