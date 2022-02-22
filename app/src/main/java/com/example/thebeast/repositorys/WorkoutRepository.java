package com.example.thebeast.repositorys;

import androidx.lifecycle.LiveData;

import com.example.thebeast.businessobjects.WorkoutModel;

import java.util.List;

public interface WorkoutRepository {

    void insert(WorkoutModel workout);

    void update(WorkoutModel workout);

    void delete(WorkoutModel workout);

    LiveData<List<WorkoutModel>> getAllWorkouts();
}
