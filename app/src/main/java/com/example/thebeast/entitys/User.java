package com.example.thebeast.entitys;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String beastName;

    private String email;

    private int workoutlaenge;

    private String spruch;

    public User(String beastName, String email, int workoutlaenge, String spruch) {
        this.beastName = beastName;
        this.email = email;
        this.workoutlaenge = workoutlaenge;
        this.spruch = spruch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
}
