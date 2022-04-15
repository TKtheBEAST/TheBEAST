package com.example.thebeast.businessobjects;

import android.net.Uri;

import com.google.firebase.firestore.DocumentId;

import java.util.List;

public class UserModel {

    @DocumentId
    private String beastId;

    private String beastName;

    private String beastEmail;

    private float workoutlaenge;

    private String beastSpruch;

    private List<UserModel> freundeCurrentUser;

    private String avatar;

    private String token;

    public UserModel(){}

    public UserModel(String beastName, String beastSpruch, String beastEmail, float workoutlaenge, String token) {
        this.beastName = beastName;
        this.beastEmail = beastEmail;
        this.workoutlaenge = workoutlaenge;
        this.beastSpruch = beastSpruch;
        this.token = token;
    }

    public List<UserModel> getFreundeCurrentUser() {
        return freundeCurrentUser;
    }

    public void setFreundeCurrentUser(List<UserModel> freundeCurrentUser) {
        this.freundeCurrentUser = freundeCurrentUser;
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

    public float getWorkoutlaenge() {
        return workoutlaenge;
    }

    public void setWorkoutlaenge(float workoutlaenge) {
        this.workoutlaenge = workoutlaenge;
    }

    public String getBeastSpruch() {
        return beastSpruch;
    }

    public void setBeastSpruch(String beastSpruch) {
        this.beastSpruch = beastSpruch;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
