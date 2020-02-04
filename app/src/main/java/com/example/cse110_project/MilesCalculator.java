package com.example.cse110_project;

public class MilesCalculator {
    private final static int INCHES_PER_FT = 12;
    private final static double INCH_PER_STRIDE = 0.413;
    private final static int FT_PER_MILE = 5280;

    public static double calculateMiles(int height, int steps){
        double strideLength = (height * INCH_PER_STRIDE) / INCHES_PER_FT;
        double stepsPerMile = FT_PER_MILE / strideLength;
        return steps / stepsPerMile;
    }
}
