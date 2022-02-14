package com.example.thebeast.repositorys.impl;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.daos.UserDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.repositorys.UserRepository;
import com.example.thebeast.roomdatabase.TheBeastDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final String TAG = "UserRepositoryImpl";

    private UserModel currentUser;
    private List<UserModel> allUsers;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    

    public void insert(UserModel user){

    }

    public void update(UserModel user){

    }

    public void delete(UserModel user){

    }

    public List<UserModel> getAllUsers(){

        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    allUsers = task.getResult().toObjects(UserModel.class);
                }else{
                    Log.e(TAG,task.getException().toString());
                }
            }
        });
        return allUsers;
    }

    public UserModel getCurrentUser(int id){return currentUser;}



}
