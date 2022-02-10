package com.example.thebeast.viewmodel;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.thebeast.entitys.Workout;
import com.example.thebeast.repository.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class LiveFragmentViewModel extends AndroidViewModel {


    private WorkoutRepository workoutRepository;
    private LiveData<List<Workout>> allWorkoutsList;

    public LiveFragmentViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        allWorkoutsList = workoutRepository.getAllWorkouts();

    }

    public void insertWorkout (Workout workout){
        workoutRepository.insert(workout);
    }

    public void updateWorkout (Workout workout){
        workoutRepository.update(workout);
    }

    public void deleteWorkout (Workout workout){
        workoutRepository.delete(workout);
    }

    public LiveData<List<Workout>> getAllWorkouts(){
        return allWorkoutsList;
    }
}
