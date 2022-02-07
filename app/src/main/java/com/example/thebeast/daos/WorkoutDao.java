package com.example.thebeast.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    void insertWorkout (Workout workout);

    @Update
    void updateWorkout (Workout workout);

    @Delete
    void deleteWorkout (Workout workout);

    @Query("SELECT * FROM workout_table")
    LiveData<List<User>> getAllUsers();
}
