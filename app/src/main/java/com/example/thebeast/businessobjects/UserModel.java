package com.example.thebeast.businessobjects;

import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentId;

public class UserModel {

    @DocumentId
    private String beastId;

    private String beastName;

    private String beastEmail;

    private int workoutlaenge;

    private String beastSpruch;

    private int avatar;

    public UserModel(){}

    public UserModel(String beastName, String beastSpruch, String beastEmail, int workoutlaenge, int avatar) {
        this.beastName = beastName;
        this.beastEmail = beastEmail;
        this.workoutlaenge = workoutlaenge;
        this.beastSpruch = beastSpruch;
        this.avatar = avatar;
    }

    public String getBeastId() {
        return beastId;
    }

    public void setBeastId(String beastId) {
        this.beastId = beastId;
    }

    public String getBeastName() {
        return beastName;
    }

    public void setBeastName(String beastName) {
        this.beastName = beastName;
    }

    public String getBeastEmail() {
        return beastEmail;
    }

    public void setBeastEmail(String beastEmail) {
        this.beastEmail = beastEmail;
    }

    public int getWorkoutlaenge() {
        return workoutlaenge;
    }

    public void setWorkoutlaenge(int workoutlaenge) {
        this.workoutlaenge = workoutlaenge;
    }

    public String getBeastSpruch() {
        return beastSpruch;
    }

    public void setBeastSpruch(String beastSpruch) {
        this.beastSpruch = beastSpruch;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
