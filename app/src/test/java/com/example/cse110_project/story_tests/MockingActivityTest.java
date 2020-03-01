package com.example.cse110_project.story_tests;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MockingActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricTestRunner.class)
public class MockingActivityTest {
    private User user;

    @Rule
    public ActivityTestRule<MockingActivity> activity =
            new ActivityTestRule<>(MockingActivity.class);

    @Before
    public void setup() {
        user = WWRApplication.getUser();
    }

    @Test
    public void testIncrementStepsOffset() {
        assertEquals(0, user.getStepsOffset());
        activity.getActivity().findViewById(R.id.mockingStepsButton).performClick();
        assertEquals(500, user.getStepsOffset());
        activity.getActivity().findViewById(R.id.mockingStepsButton).performClick();
        assertEquals(1000, user.getStepsOffset());
    }

    @Test
    public void testSetTimeValidInput() {
        EditText timeInput = activity.getActivity().findViewById(R.id.mockingTimeInput);
        Button timeButton = activity.getActivity().findViewById(R.id.mockingTimeSubmitButton);

        timeInput.setText("00:00");
        timeButton.performClick();
        assertEquals(LocalTime.parse("00:00"), WWRApplication.getTime());

        timeInput.setText("12:12");
        timeButton.performClick();
        assertEquals(LocalTime.parse("12:12"), WWRApplication.getTime());
    }

    @Test
    public void testSetTimeInvalidInput() {
        EditText timeInput = activity.getActivity().findViewById(R.id.mockingTimeInput);
        Button timeButton = activity.getActivity().findViewById(R.id.mockingTimeSubmitButton);
        String error = activity.getActivity().getResources().getString(R.string.mockingTimeError);

        timeInput.setText("abcd");
        timeButton.performClick();
        assertEquals(error, ShadowToast.getTextOfLatestToast());
    }
}
