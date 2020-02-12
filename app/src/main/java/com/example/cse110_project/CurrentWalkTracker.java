package com.example.cse110_project;

import com.example.cse110_project.fitness_api.FitnessService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CurrentWalkTracker {
    private static int initialSteps;
    private static int finalSteps;
    private static LocalTime initialTime;
    private static LocalTime finalTime;
    private static LocalDateTime startDate;

    public static void setInitial(int steps, LocalTime time, LocalDateTime date) {
        initialSteps = steps;
        finalSteps = steps;
        initialTime = time;
        finalTime = time;
        startDate = date;
    }

    public static void setFinalSteps(int steps) { finalSteps = steps; }

    public static void setFinalTime(LocalTime time) {
        finalTime = time;
    }

    public static int getWalkSteps() {
        return finalSteps - initialSteps;
    }

    public static LocalTime getWalkTime() {
        if (initialTime == null || finalTime == null) {
            return null;
        }

        return finalTime.minus(Duration.ofNanos(initialTime.toNanoOfDay()));
    }

    public static LocalDateTime getWalkDate() {
        return startDate;
    }
}
