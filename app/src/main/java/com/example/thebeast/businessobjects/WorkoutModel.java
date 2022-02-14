package com.example.thebeast.businessobjects;

import com.google.firebase.firestore.DocumentId;

public class WorkoutModel {

    @DocumentId
    private int id;

    private int avatar;
    private String beastName;
    private String uebungen;
    private int workoutlaenge;
    //standort;


    public WorkoutModel(int avatar, String beastName, String uebungen, int workoutlaenge) {
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
