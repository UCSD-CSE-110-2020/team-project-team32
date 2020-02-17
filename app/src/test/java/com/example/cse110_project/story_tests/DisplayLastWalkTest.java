package com.example.cse110_project.story_tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.util.MilesCalculator;
import com.example.cse110_project.R;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.UserData;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class DisplayLastWalkTest {
    private User user;
    private Context c;

    private TextView walkSteps;
    private TextView walkMiles;
    private TextView walkTime;
    private String noRecent;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        user = WWRApplication.getUser();
        c = mainActivity.getActivity().getApplicationContext();

        walkSteps = mainActivity.getActivity().findViewById(R.id.recentSteps);
        walkMiles = mainActivity.getActivity().findViewById(R.id.recentMiles);
        walkTime = mainActivity.getActivity().findViewById(R.id.recentTime);
        noRecent = mainActivity.getActivity().getResources().getString(R.string.noDataText);
    }

    @Test
    public void testDisplayNoWalks() {
        mainActivity.getActivity().updateRecentRoute();
        assertEquals(walkSteps.getText(), noRecent);
        assertEquals(walkMiles.getText(), noRecent);
        assertEquals(walkTime.getText(), noRecent);
    }

    @Test
    public void testDisplayWalksNoDate() {
        Route r = new Route(10, "Name");
        UserData.saveRoute(c, r);
        RouteData.saveRouteData(c, r);

        mainActivity.getActivity().updateRecentRoute();
        assertEquals(walkSteps.getText(), noRecent);
        assertEquals(walkMiles.getText(), noRecent);
        assertEquals(walkTime.getText(), noRecent);
    }

    @Test
    public void testDisplayWalksWithDates() {
        Route r = new Route(10, "Name", 25, LocalTime.of(10,10),
                LocalDateTime.of(1, 1, 1, 1, 1));
        user.getRoutes().createRoute(r);
        Route r2 = new Route(15, "Name2");
        user.getRoutes().createRoute(r2);
        Route r3 = new Route(100, "Name3", 50, LocalTime.of(11,10),
                LocalDateTime.of(2, 1, 1, 1, 1));
        user.getRoutes().createRoute(r3);

        mainActivity.getActivity().updateRecentRoute();
        assertEquals(walkSteps.getText(), "50");
        assertEquals(walkMiles.getText(),
                MilesCalculator.formatMiles(
                        MilesCalculator.calculateMiles(user.getHeight(), 50)));
        assertEquals(walkTime.getText(), LocalTime.of(11,10).toString());
    }
}