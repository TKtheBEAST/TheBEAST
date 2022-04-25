package com.example.thebeast.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.repositorys.impl.UserRepositoryImpl;

public class LoginFragmentViewModel extends ViewModel {

    private UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl();
    public LoginFragmentViewModel(){}

    public void setCurrentUser(String email){
        userRepositoryImpl.setCurrentUser(email);
    }

    public void getFreundeCurrentUser(){
        userRepositoryImpl.getFreundeCurrentUser();
    }
}
