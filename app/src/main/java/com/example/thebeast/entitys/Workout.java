package com.example.thebeast.entitys;

import androidx.room.Entity;

import java.util.List;

@Entity (tableName = "workout_table")
public class Workout {

    private List<String> uebungen;
    private int workoutlaenge;
    //standort;

}
