package com.example.thebeast.viewmodel;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.thebeast.repositorys.impl.UserRepositoryImpl;

public class EinstellungenFragmentViewModel extends ViewModel {

    private UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();

    public EinstellungenFragmentViewModel(){}

    public void updateWorkoutlaenge(float laenge){
        userRepositoryImpl.updateWorkoutLaenge(laenge);
    }

    public void updateBeastName(String neuerBeastName){
        userRepositoryImpl.updateBeastName(neuerBeastName);
    }

    public void deleteUser(){
        userRepositoryImpl.deleteUser();
    }

    public void updateAvatar(Uri avatar){
        userRepositoryImpl.updateAvatar(avatar);
    }

}
