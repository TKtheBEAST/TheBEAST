package com.example.thebeast.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.repositorys.impl.UserRepositoryImpl;
import com.example.thebeast.repositorys.impl.WorkoutRepositoryImpl;

import java.util.ArrayList;

public class HomeFragmentViewModel extends AndroidViewModel {

    private ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    private ArrayList<String> gewaehlteTrainingsName = new ArrayList();

    private WorkoutRepositoryImpl workoutRepositoryImpl;
    private UserRepositoryImpl userRepositoryImpl;

    private static User currentUser;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        workoutRepositoryImpl = new WorkoutRepositoryImpl(application);
        userRepositoryImpl = new UserRepositoryImpl(application);

    }

    public ArrayList<Integer> getGewaehlteTrainingsList() {
        return gewaehlteTrainingsList;
    }

    public void addTraining(Integer training) {
        this.gewaehlteTrainingsList.add(training);
    }

    public ArrayList<String> getGewaehlteTrainingsName() {
        return gewaehlteTrainingsName;
    }

    public void addTrainingsName(String name) {
        this.gewaehlteTrainingsName.add(name);
    }



    public void insertWorkout (Workout workout){
        workoutRepositoryImpl.insert(workout);
    }

    public static User getCurrentUser(){return currentUser;}


}
