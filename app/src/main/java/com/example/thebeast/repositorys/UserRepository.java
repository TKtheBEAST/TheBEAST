package com.example.thebeast.repositorys;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.daos.UserDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.repositorys.impl.UserRepositoryImpl;
import com.example.thebeast.roomdatabase.TheBeastDataBase;

import java.util.List;

public interface UserRepository {




    void deleteUser();

    List<UserModel> getAllUsers();


}
