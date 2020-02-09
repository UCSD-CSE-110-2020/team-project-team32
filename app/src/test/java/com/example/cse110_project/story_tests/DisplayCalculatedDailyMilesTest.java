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
public class DisplayCalculatedDailyMilesTest {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context c;

    private TextView milesDisplay;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        pref = mainActivity.getActivity().getSharedPreferences(DataConstants.USER_DATA_FILE,
                Context.MODE_PRIVATE);
        editor = pref.edit();
        c = mainActivity.getActivity().getApplicationContext();
        milesDisplay = mainActivity.getActivity().findViewById(R.id.dailyMilesDisplay);
    }

    @Test
    public void testInitialMiles() {
        User.setHeight(60);
        assertEquals("0.0", milesDisplay.getText());
    }

    @Test
    public void testMilesUpdate() {
        User.setHeight(60);
        mainActivity.getActivity().updateDailySteps(5000);
        assertEquals("2.0", milesDisplay.getText());
    }

    @Test
    public void testMilesUpdateVerySmall() {
        User.setHeight(60);
        mainActivity.getActivity().updateDailySteps(20);
        assertEquals("0.0", milesDisplay.getText());
    }

    @Test
    public void testMilesUpdateSmall() {
        User.setHeight(60);
        mainActivity.getActivity().updateDailySteps(250);
        assertEquals("0.1", milesDisplay.getText());
    }

    @Test
    public void testMilesUpdateLarge() {
        User.setHeight(60);
        mainActivity.getActivity().updateDailySteps(1000000);
        assertEquals("391.1", milesDisplay.getText());
    }

    @Test
    public void testMilesZeroHeight() {
        mainActivity.getActivity().updateDailySteps(1010010);
        assertEquals("0.0", milesDisplay.getText());
    }

    @Test
    public void testMilesDifferentHeightSameSteps() {
        User.setHeight(72);
        mainActivity.getActivity().updateDailySteps(1000000);
        assertEquals("469.3", milesDisplay.getText());
    }
}
