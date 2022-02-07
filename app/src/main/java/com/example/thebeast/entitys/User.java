package com.example.thebeast.entitys;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private String id;

    private String beastName;

    private String email;

    private int workoutlaenge;

    private String spruch;




}
