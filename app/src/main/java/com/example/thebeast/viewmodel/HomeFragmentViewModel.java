package com.example.thebeast.viewmodel;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

public class HomeFragmentViewModel extends ViewModel {

    private ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    private ArrayList<String> gewaehlteTrainingsName = new ArrayList();

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

}