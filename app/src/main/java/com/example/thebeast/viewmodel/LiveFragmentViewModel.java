package com.example.thebeast.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.repositorys.impl.WorkoutRepositoryImpl;

import java.util.List;

public class LiveFragmentViewModel extends ViewModel implements WorkoutRepositoryImpl.OnFirstoreTaskComplete {



    private WorkoutRepositoryImpl workoutRepositoryImpl = new WorkoutRepositoryImpl(this);
    private MutableLiveData<List<WorkoutModel>> workouts = new MutableLiveData<>();

    public LiveData<List<WorkoutModel>> getWorkouts() {
        return workouts;
    }

    public LiveFragmentViewModel(){
        workoutRepositoryImpl.getAllWorkouts();
    }

    public void insertWorkout (WorkoutModel workout){
        workoutRepositoryImpl.insert(workout);
    }

    public void updateWorkout (WorkoutModel workout){
        workoutRepositoryImpl.update(workout);
    }

    public void deleteWorkout (WorkoutModel workout){
        workoutRepositoryImpl.delete(workout);
    }


    @Override
    public void workoutModelsListAdded(List<WorkoutModel> workoutModels) {
        workouts.setValue(workoutModels);
    }

    @Override
    public void onError(Exception e) {

    }
}
