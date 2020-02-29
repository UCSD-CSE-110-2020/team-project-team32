package com.example.cse110_project.user_routes;

import androidx.annotation.NonNull;

import com.example.cse110_project.util.MilesCalculator;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserRoute extends Route {
    public UserRoute(int id, String name){
        setID(id);
        setName(name);

        setStartingPoint(NO_DATA);
        setFlatVsHilly(NO_DATA);
        setLoopVsOutBack(NO_DATA);
        setStreetsVsTrail(NO_DATA);
        setEvenVsUneven(NO_DATA);
        setDifficulty(NO_DATA);
        setNotes(NO_DATA);
    }

    public UserRoute(int id, String name, int steps, LocalTime dur, LocalDateTime start) {
        this(id, name);
        setSteps(steps);
        setStartDate(start);
        setDuration(dur);
    }
}