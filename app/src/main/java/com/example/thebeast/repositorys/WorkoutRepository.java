package com.example.thebeast.repositorys;

import com.example.thebeast.businessobjects.WorkoutModel;

public interface WorkoutRepository {

    void insert(WorkoutModel workout);

    void update(WorkoutModel workout);

    void delete(WorkoutModel workout);

    void getAllWorkouts();
}
