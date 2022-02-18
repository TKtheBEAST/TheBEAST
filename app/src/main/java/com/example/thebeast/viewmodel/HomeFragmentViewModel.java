package com.example.thebeast.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.repositorys.impl.UserRepositoryImpl;
import com.example.thebeast.repositorys.impl.WorkoutRepositoryImpl;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {

    private ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    private ArrayList<String> gewaehlteTrainingsName = new ArrayList();

    private WorkoutRepositoryImpl workoutRepositoryImpl;
    private UserRepositoryImpl userRepositoryImpl;

    private static User currentUser;


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



    public void insertWorkout (WorkoutModel workout){
        workoutRepositoryImpl.insert(workout);
    }

    public static User getCurrentUser(){return currentUser;}


}
