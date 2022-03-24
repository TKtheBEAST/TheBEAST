package com.example.thebeast;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;

public interface MainActivitySelectionListener {

    void onWorkoutSelected(WorkoutModel workout);
    void onFreundSelected(UserModel freund);

}
