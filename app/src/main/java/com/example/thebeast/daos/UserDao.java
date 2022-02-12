package com.example.thebeast.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thebeast.entitys.User;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface UserDao {

    @Insert
    void insertUser (User user);

    @Update
    void updateUser (User user);

    @Delete
    void deleteUser (User user);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();

    @Query("Select * from user_table where id=:id")
    User getCurrentUser(int id);



}
