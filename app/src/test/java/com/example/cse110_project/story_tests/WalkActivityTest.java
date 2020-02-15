package com.example.cse110_project.story_tests;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.example.cse110_project.util.CurrentWalkTracker;
import com.example.cse110_project.MainActivity;
import com.example.cse110_project.WalkActivity;
import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;
import com.example.cse110_project.user_routes.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class WalkActivityTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    private Intent intent;
    private long nextStepCount;
    private TextView textSteps;

    @Before
    public void setUp() {
        FitnessServiceFactory.put(TEST_SERVICE, WalkActivityTest.TestFitnessService::new);
        intent = new Intent(ApplicationProvider.getApplicationContext(), WalkActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY, TEST_SERVICE);
    }

    @Test
    public void testUpdateSteps() {
        nextStepCount = 1337;

        CurrentWalkTracker.setInitial(0, LocalTime.of(10, 0),
                LocalDateTime.of(1, 1, 1, 1, 1));
        ActivityScenario<WalkActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            assertEquals(0, CurrentWalkTracker.getWalkSteps());
            assertEquals(LocalTime.of(0, 0).toString(),
                    CurrentWalkTracker.getWalkTime().toString());
            assertEquals(LocalDateTime.of(1,1,1,1,1).toString(),
                    CurrentWalkTracker.getWalkDate().toString());

            activity.endWalkActivity(LocalTime.of(10, 30));

            assertEquals(1337, CurrentWalkTracker.getWalkSteps());
            assertEquals(LocalTime.of(0, 30).toString(),
                    CurrentWalkTracker.getWalkTime().toString());
            assertEquals(LocalDateTime.of(1,1,1,1,1).toString(),
                    CurrentWalkTracker.getWalkDate().toString());
        });
    }

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private AppCompatActivity walkActivity;

        public TestFitnessService(AppCompatActivity walkActivity) {
            this.walkActivity = walkActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            CurrentWalkTracker.setFinalSteps((int)nextStepCount);
            User.setFitnessSteps((int)nextStepCount);
        }
    }
}