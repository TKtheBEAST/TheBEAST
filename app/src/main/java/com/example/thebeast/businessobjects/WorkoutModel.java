package com.example.thebeast.businessobjects;

import com.google.firebase.firestore.DocumentId;

public class WorkoutModel {

    @DocumentId
    private String id;

    private String avatar;
    private String beastName;
    private String uebungen;
    private int workoutlaenge;
    //standort;


    public WorkoutModel(){}

    public WorkoutModel(String beastName, String uebungen, int workoutlaenge) {
        this.beastName = beastName;
        this.uebungen = uebungen;
        this.workoutlaenge = workoutlaenge;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
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
