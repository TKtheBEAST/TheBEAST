package com.example.thebeast.viewmodel;

import android.app.Application;
import android.util.Log;

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

    private static final String TAG = "HomeFragmentViewModel";
    private ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    private ArrayList<String> gewaehlteTrainingsName = new ArrayList();
    private boolean isWorkoutRunning;
    private WorkoutModel aktuellesWorkout;


    private WorkoutRepositoryImpl workoutRepositoryImpl = new WorkoutRepositoryImpl();

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

    public boolean isWorkoutRunning() {
        return isWorkoutRunning;
    }

    public void setWorkoutRunning(boolean workoutRunning) {
        isWorkoutRunning = workoutRunning;
    }

    public void insertWorkout (WorkoutModel workout){
        try {
            workoutRepositoryImpl.insert(workout);
        }catch (NullPointerException e){
            System.err.println(TAG+" Nullpointer Exception");
        }
    }


    public void setAktuellesWorkout(WorkoutModel checkWorkout) {
        aktuellesWorkout = checkWorkout;
    }

    public WorkoutModel getAktuellesWorkout(){
        return aktuellesWorkout;
    }
}
