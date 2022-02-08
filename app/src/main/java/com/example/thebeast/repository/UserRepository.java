package com.example.thebeast.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.thebeast.daos.UserDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.roomdatabase.TheBeastDataBase;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;


    public UserRepository(Application application){
        TheBeastDataBase database = TheBeastDataBase.getInstance(application);

        userDao = database.userDao();
        allUsers = userDao.getAllUsers();

    }

    public void insert(User user){

        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user){

        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user){

        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }



    //Asynctasks
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        private InsertUserAsyncTask (UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        private UpdateUserAsyncTask (UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{

        private UserDao userDao;

        private DeleteUserAsyncTask (UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }


}
