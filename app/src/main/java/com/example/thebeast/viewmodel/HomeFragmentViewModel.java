package com.example.thebeast.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.repository.UserRepository;
import com.example.thebeast.repository.WorkoutRepository;

import java.util.ArrayList;

public class HomeFragmentViewModel extends AndroidViewModel {

    private ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    private ArrayList<String> gewaehlteTrainingsName = new ArrayList();

    private WorkoutRepository workoutRepository;
    private UserRepository userRepository;

    private static User currentUser;

    public HomeFragmentViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        userRepository = new UserRepository(application);

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
        workoutRepository.insert(workout);
    }

    public static User getCurrentUser(){return currentUser;}


}
