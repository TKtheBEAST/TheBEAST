package com.example.thebeast.repositorys;

import com.example.thebeast.businessobjects.WorkoutModel;

import java.util.List;

public interface WorkoutRepository {

    void insert(WorkoutModel workout);

    void update(WorkoutModel workout);

    void delete(WorkoutModel workout);

    List<WorkoutModel> getAllWorkouts();
}
