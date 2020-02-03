package com.example.cse110_project;

import android.widget.TextView;

public class calculateData {
    public double calculateMiles(String height, int steps) {
        int HeightToInches;
        double averageStrideLength;
        double ftPerStride;
        double stepsPerMile;
        double result;

        //extract data from height string
        int feet = Integer.parseInt(height.substring( 0, height.indexOf("'")));
        int inches = Integer.parseInt(height.substring(height.indexOf("'")+1, height.length()));

        // calculate final result miles travelled
        HeightToInches = (feet * 12 + inches); // converting height to inches formula
        averageStrideLength = HeightToInches * 0.413; // 0.413 is predetermined # to find avrg stride
        ftPerStride = averageStrideLength/12;
        stepsPerMile = (5280 / ftPerStride);
        result = steps/stepsPerMile;
        return result;
    }
}
