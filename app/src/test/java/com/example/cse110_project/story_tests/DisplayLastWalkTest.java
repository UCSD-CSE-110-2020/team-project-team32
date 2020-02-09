package com.example.cse110_project.story_tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.MilesCalculator;
import com.example.cse110_project.R;
import com.example.cse110_project.data_access.DataConstants;
import com.example.cse110_project.data_access.RouteData;
import com.example.cse110_project.data_access.UserData;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class DisplayLastWalkTest {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
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
        pref = mainActivity.getActivity().getSharedPreferences(DataConstants.USER_DATA_FILE,
                Context.MODE_PRIVATE);
        editor = pref.edit();
        c = mainActivity.getActivity().getApplicationContext();

        walkSteps = mainActivity.getActivity().findViewById(R.id.recentStepsDisplay);
        walkMiles = mainActivity.getActivity().findViewById(R.id.recentMilesDisplay);
        walkTime = mainActivity.getActivity().findViewById(R.id.recentTimeDisplay);
        noRecent = mainActivity.getActivity().getResources().getString(R.string.noRecentRouteText);
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
        User.getRoutes(c).addRoute(c, r);
        Route r2 = new Route(15, "Name2");
        User.getRoutes(c).addRoute(c, r2);
        Route r3 = new Route(100, "Name3", 50, LocalTime.of(11,10),
                LocalDateTime.of(2, 1, 1, 1, 1));
        User.getRoutes(c).addRoute(c, r3);

        mainActivity.getActivity().updateRecentRoute();
        assertEquals(walkSteps.getText(), "50");
        assertEquals(walkMiles.getText(),
                MilesCalculator.formatMiles(
                        MilesCalculator.calculateMiles(User.getHeight(), 50)));
        assertEquals(walkTime.getText(), LocalTime.of(11,10).toString());
    }
}
