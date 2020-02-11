package com.example.cse110_project.story_tests;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.example.cse110_project.CurrentWalkTracker;
import com.example.cse110_project.MainActivity;
import com.example.cse110_project.WalkActivity;
import com.example.cse110_project.fitness_api.FitnessService;
import com.example.cse110_project.fitness_api.FitnessServiceFactory;

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
            assertEquals(CurrentWalkTracker.getWalkSteps(), 0);
            assertNull(CurrentWalkTracker.getWalkTime());
            assertEquals(CurrentWalkTracker.getWalkDate().toString(),
                    LocalDateTime.of(1,1,1,1,1).toString());

            activity.endWalkActivity(LocalTime.of(10, 30));

            assertEquals(CurrentWalkTracker.getWalkSteps(), 1337);
            assertEquals(CurrentWalkTracker.getWalkTime().toString(),
                    LocalTime.of(0, 30).toString());
            assertEquals(CurrentWalkTracker.getWalkDate().toString(),
                    LocalDateTime.of(1,1,1,1,1).toString());
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
        }
    }
}