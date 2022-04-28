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
    private String beastEmail;
    private String standort;
    private String uebungen;
    private String startzeit;
    private double longitude;
    private double latitude;
    private float workoutlaenge;
    private boolean workoutFruehzeitigBeendet;
    //standort;


    public WorkoutModel() {
    }

    public WorkoutModel(String workoutOwnerID, String beastName, String beastEmail, String uebungen, float workoutlaenge, String startzeit, String avatar) {
        this.workoutOwnerID = workoutOwnerID;
        this.beastName = beastName;
        this.beastEmail = beastEmail;
        this.uebungen = uebungen;
        this.workoutlaenge = workoutlaenge;
        this.startzeit = startzeit;
        this.avatar = avatar;
    }


    public WorkoutModel(String workoutOwnerID, String beastName, String beastEmail, String uebungen, float workoutlaenge, String startzeit, String avatar, String standort, double longitude, double latitude) {
        this.workoutOwnerID = workoutOwnerID;
        this.beastName = beastName;
        this.uebungen = uebungen;
        this.workoutlaenge = workoutlaenge;
        this.startzeit = startzeit;
        this.avatar = avatar;
        this.beastEmail = beastEmail;
        this.standort = standort;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getBeastEmail() {
        return beastEmail;
    }

    public void setBeastEmail(String beastEmail) {
        this.beastEmail = beastEmail;
    }

    public String getStandort() {
        return standort;
    }

    public void setStandort(String standort) {
        this.standort = standort;
    }

    public boolean isWorkoutFruehzeitigBeendet() {
        return workoutFruehzeitigBeendet;
    }

    public void setWorkoutFruehzeitigBeendet(boolean workoutFruehzeitigBeendet) {
        this.workoutFruehzeitigBeendet = workoutFruehzeitigBeendet;
    }
}
