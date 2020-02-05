package com.example.cse110_project.unit_tests;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.cse110_project.*;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MilesCalculatorTest {
    @Test
    public void testZeroSteps() {
        assertEquals(0, (int)MilesCalculator.calculateMiles(60, 0));
        assertEquals(0, (int)MilesCalculator.calculateMiles(-60, 0));
    }

    @Test
    public void testSmallHeightSteps() {
        assertEquals(0, (int)(MilesCalculator.calculateMiles(1, 1) * 10 + .5));
    }

    @Test
    public void testLargeSimilarHeightSteps() {
        assertEquals(1,
                (int)(MilesCalculator.calculateMiles(100, 100) * 10 + .5));
    }

    @Test
    public void testLargerHeight() {
        assertEquals(652,
                (int)(MilesCalculator.calculateMiles(1000000, 10) * 10 + .5));
    }

    @Test
    public void testLargerSteps() {
        assertEquals(652,
                (int)(MilesCalculator.calculateMiles(10, 1000000) * 10 + .5));
    }
}
