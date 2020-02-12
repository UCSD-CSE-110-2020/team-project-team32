package com.example.cse110_project.trackers;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CurrentTimeTracker {
    private static LocalTime time = null;
    private static LocalDateTime date = null;

    public static void setTime(LocalTime newTime) {
        time = newTime;
    }

    public static LocalTime getTime() {
        if (time == null) {
            return LocalTime.now();
        } else {
            return time;
        }
    }

    public static void setDate(LocalDateTime newDate) {
        date = newDate;
    }

    public static LocalDateTime getDate() {
        if (date == null) {
            return LocalDateTime.now();
        } else {
            return date;
        }
    }
}
