package com.example.thebeast.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.thebeast.daos.UserDao;
import com.example.thebeast.daos.WorkoutDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;

@Database(entities = {User.class, Workout.class}, version=1)

public abstract class TheBeastDataBase extends RoomDatabase {

    private static TheBeastDataBase instance;

    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();

    public static synchronized TheBeastDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TheBeastDataBase.class, "theBeast_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



}
