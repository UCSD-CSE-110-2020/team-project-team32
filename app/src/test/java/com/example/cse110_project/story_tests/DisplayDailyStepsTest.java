package com.example.cse110_project.story_tests;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.user_routes.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class DisplayDailyStepsTest {
    private User user;
    private TextView stepsDisplay;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        user = WWRApplication.getUser();
        stepsDisplay = mainActivity.getActivity().findViewById(R.id.dailySteps);
    }

    @Test
    public void testInitialSteps() {
        assertEquals(String.valueOf(user.getTotalSteps()), stepsDisplay.getText());
    }

    @Test
    public void testStepsUpdate() {
        mainActivity.getActivity().updateDailySteps(500);
        assertEquals("500", stepsDisplay.getText());
        mainActivity.getActivity().updateDailySteps(20);
        assertEquals("20", stepsDisplay.getText());
    }

    @Test
    public void testLargeSteps() {
        mainActivity.getActivity().updateDailySteps(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), stepsDisplay.getText());
    }

    @Test
    public void testStepsWithOffset() {
        mainActivity.getActivity().updateDailySteps(20);
        assertEquals("20", stepsDisplay.getText());
        user.setStepsOffset(100);
        mainActivity.getActivity().updateDailySteps(20);
        assertEquals("120", stepsDisplay.getText());
    }
}
