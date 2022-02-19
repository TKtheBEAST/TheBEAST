package com.example.thebeast.businessobjects;

import androidx.room.PrimaryKey;

import com.google.firebase.firestore.DocumentId;

public class UserModel {

    @DocumentId
    private String user_id;

    private String beastName;

    private String email;

    private int workoutlaenge;

    private String spruch;

    private int avatar;

    public UserModel(){}

    public UserModel(String beastName, String email, int workoutlaenge, String spruch, int avatar) {
        this.beastName = beastName;
        this.email = email;
        this.workoutlaenge = workoutlaenge;
        this.spruch = spruch;
        this.avatar = avatar;
    }

    public void setId(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return user_id;
    }

    public String getBeastName() {
        return beastName;
    }

    public String getEmail() {
        return email;
    }

    public int getWorkoutlaenge() {
        return workoutlaenge;
    }

    public String getSpruch() {
        return spruch;
    }

    public int getAvatar() {
        return avatar;
    }
}
