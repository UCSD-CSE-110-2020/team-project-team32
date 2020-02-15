package com.example.cse110_project.util;

import com.example.cse110_project.fitness.FitnessService;

public class CurrentFitnessTracker {
    private static FitnessService fitnessService;

    public static void setFitnessService(FitnessService fs) {
        fitnessService = fs;
    }

    public static FitnessService getFitnessService() {
        return fitnessService;
    }

    public static boolean hasFitnessService() {
        return fitnessService != null;
    }
}
