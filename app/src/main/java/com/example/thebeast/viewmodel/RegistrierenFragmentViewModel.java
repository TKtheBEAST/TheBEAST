package com.example.thebeast.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.thebeast.repositorys.impl.UserRepositoryImpl;

public class RegistrierenFragmentViewModel extends ViewModel {

    private UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();

    public RegistrierenFragmentViewModel(){}

    public void createUserWithEmailAndPassword(String beastName, String beastSpruch, String email, String passwort, String token){
        userRepositoryImpl.createUserWithEmailAndPassword(beastName, beastSpruch, email, passwort, token);

    }
}
