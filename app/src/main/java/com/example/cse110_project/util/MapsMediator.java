package com.example.cse110_project.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.cse110_project.WWRApplication;

public class MapsMediator {
    private Intent mapIntent;

    public Intent assembleIntent(String searchText) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(searchText));
        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }

    public void launchMaps(Activity activity) {
        activity.startActivity(mapIntent);
    }
}
