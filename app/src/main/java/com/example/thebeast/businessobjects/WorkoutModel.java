package com.example.thebeast.businessobjects;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class WorkoutModel {

    @DocumentId
    private String id;

    private String workoutOwnerID;
    private String avatar;
    private String beastName;
    private String uebungen;
    private String startzeit;
    private float workoutlaenge;
    //standort;


    public WorkoutModel(){}

    public WorkoutModel(String workoutOwnerID, String beastName, String uebungen, float workoutlaenge, String startzeit) {
        this.workoutOwnerID = workoutOwnerID;
        this.beastName = beastName;
        this.uebungen = uebungen;
        this.workoutlaenge = workoutlaenge;
        this.startzeit = startzeit;
    }

    public String getWorkoutOwnerID() {
        return workoutOwnerID;
    }

    public void setWorkoutOwnerID(String workoutOwnerID) {
        this.workoutOwnerID = workoutOwnerID;
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


    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBeastName() {
        return beastName;
    }

    public String getUebungen() {
        return uebungen;
    }

    public float getWorkoutlaenge() {
        return workoutlaenge;
    }

    public String getStartzeit() {
        return startzeit;
    }

    public void setStartzeit(String startzeit) {
        this.startzeit = startzeit;
    }
}
