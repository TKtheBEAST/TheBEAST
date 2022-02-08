package com.example.thebeast.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.thebeast.daos.UserDao;
import com.example.thebeast.daos.WorkoutDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.roomdatabase.TheBeastDataBase;

import java.util.List;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutRepository(Application application){
        TheBeastDataBase database = TheBeastDataBase.getInstance(application);

        workoutDao = database.workoutDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }
}
