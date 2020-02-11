package com.example.cse110_project.unit_tests;

import com.example.cse110_project.CurrentWalkTracker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JUnit4.class)
public class CurrentWalkTrackerTest {
    @Test
    public void testSteps() {
        CurrentWalkTracker.setInitial(0, LocalTime.of(0, 0),
                LocalDateTime.of(1, 1, 1, 1, 1));
        CurrentWalkTracker.setFinalSteps(0);
        assertEquals(0, CurrentWalkTracker.getWalkSteps());

        CurrentWalkTracker.setFinalSteps(100);
        assertEquals(100, CurrentWalkTracker.getWalkSteps());
    }

    @Test
    public void testDate() {
        CurrentWalkTracker.setInitial(0, LocalTime.of(0, 0),
                LocalDateTime.of(0, 2, 26, 23, 21));
        assertEquals(LocalDateTime.of(0, 2, 26, 23, 21).toString(),
                CurrentWalkTracker.getWalkDate().toString());
    }

    @Test
    public void testTimeNull() {
        CurrentWalkTracker.setInitial(0, LocalTime.of(0, 0),
                LocalDateTime.of(0, 2, 26, 23, 21));
        CurrentWalkTracker.setFinalTime(null);
        assertNull(CurrentWalkTracker.getWalkTime());
    }

    @Test
    public void testTimeNonNull() {
        CurrentWalkTracker.setInitial(0, LocalTime.of(1, 10),
                LocalDateTime.of(0, 2, 26, 23, 21));
        CurrentWalkTracker.setFinalTime(LocalTime.of(10, 45));
        assertEquals(LocalTime.of(9, 35).toString(),
                CurrentWalkTracker.getWalkTime().toString());
    }
}
