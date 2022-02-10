package com.example.thebeast.entitys;

import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity (tableName = "workout_table")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int avatar;
    private String beastName;
    private String uebungen;
    private int workoutlaenge;
    //standort;


    public Workout(int avatar, String beastName, String uebungen, int workoutlaenge) {
        this.avatar = avatar;
        this.beastName = beastName;
        this.uebungen = uebungen;
        this.workoutlaenge = workoutlaenge;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getBeastName() {
        return beastName;
    }

    public String getUebungen() {
        return uebungen;
    }

    public int getWorkoutlaenge() {
        return workoutlaenge;
    }
}
