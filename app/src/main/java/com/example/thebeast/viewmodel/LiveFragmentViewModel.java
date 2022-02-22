package com.example.thebeast.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.repositorys.impl.WorkoutRepositoryImpl;

import java.util.List;

public class LiveFragmentViewModel extends ViewModel {



    private WorkoutRepositoryImpl workoutRepositoryImpl = new WorkoutRepositoryImpl();


    public void insertWorkout (WorkoutModel workout){
        workoutRepositoryImpl.insert(workout);
    }

    public void updateWorkout (WorkoutModel workout){
        workoutRepositoryImpl.update(workout);
    }

    public void deleteWorkout (WorkoutModel workout){
        workoutRepositoryImpl.delete(workout);
    }

    public LiveData<List<WorkoutModel>> getAllWorkouts(){
        return workoutRepositoryImpl.getAllWorkouts();
    }


}
