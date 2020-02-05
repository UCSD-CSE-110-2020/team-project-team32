package com.example.cse110_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MilesCalculator {
    private final static int INCHES_PER_FT = 12;
    private final static double INCH_PER_STRIDE = 0.413;
    private final static int FT_PER_MILE = 5280;

    public static double calculateMiles(int height, int steps){
        double strideLength = (height * INCH_PER_STRIDE) / INCHES_PER_FT;
        double stepsPerMile = FT_PER_MILE / strideLength;
        return steps / stepsPerMile;
    }


    //USAGE FROM OTHER CLASSES: MilesCalculator.retrieveHeight(currentClass.this) -> gives you saved height
    //add function to USER class
    public static String retrieveHeight(Context c){
        SharedPreferences sharedPreferences = c.getSharedPreferences("user_height", c.MODE_PRIVATE);
        String Height = sharedPreferences.getString("editHeight", "");
        return Height;
    }
}
