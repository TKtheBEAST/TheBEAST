package com.example.thebeast;

import com.example.thebeast.businessobjects.UserModel;


/*
    Durch diese KLasse wird der aktuelle User gesetzt
 */
public class CurrentUser {

    private static UserModel currentUser;



    public static UserModel getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserModel currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
