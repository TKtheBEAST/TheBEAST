package com.example.thebeast.entitys;

import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "workout_table")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private String id;

    private ImageView avatar;
    private String beastName;
    private List<String> uebungen;
    private int workoutlaenge;
    //standort;


    public Workout(ImageView avatar, String beastName, List<String> uebungen, int workoutlaenge) {
        this.avatar = avatar;
        this.beastName = beastName;
        this.uebungen = uebungen;
        this.workoutlaenge = workoutlaenge;
    }

    public String getId() {
        return id;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public String getBeastName() {
        return beastName;
    }

    public List<String> getUebungen() {
        return uebungen;
    }

    public int getWorkoutlaenge() {
        return workoutlaenge;
    }
}
