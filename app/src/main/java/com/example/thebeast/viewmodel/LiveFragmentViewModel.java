package com.example.thebeast.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.repositorys.impl.WorkoutRepositoryImpl;

import java.util.List;

public class LiveFragmentViewModel extends ViewModel implements WorkoutRepositoryImpl.OnFirestoreTaskComplete {


    private MutableLiveData<List<WorkoutModel>> workoutListModelData = new MutableLiveData<>();

    public LiveData<List<WorkoutModel>> getWorkoutListModelData() {
        return workoutListModelData;
    }

    private WorkoutRepositoryImpl workoutRepositoryImpl = new WorkoutRepositoryImpl(this);


    public LiveFragmentViewModel() {
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
    public void workoutListDataAdded(List<WorkoutModel> workoutModelList) {
        workoutListModelData.setValue(workoutModelList);
    }

    @Override
    public void onError(Exception e) {

    }
}
