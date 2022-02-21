package com.example.thebeast.repositorys.impl;

import android.app.Application;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.daos.WorkoutDao;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.repositorys.WorkoutRepository;
import com.example.thebeast.roomdatabase.TheBeastDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutRepositoryImpl implements WorkoutRepository {

    private static final String TAG = "WorkoutRepositoryImpl";
    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference workoutRef = firebaseFirestore.collection("Workouts");

    public WorkoutRepositoryImpl(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void insert(WorkoutModel workout) {

        //add new Workout with a generated id
        Map<String, Object> data = new HashMap();
        data.put("beasName",workout.getBeastName());
        data.put("uebungen",workout.getUebungen());
        data.put("workoutlaenge",workout.getWorkoutlaenge());

        workoutRef
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>(){

            @Override
            public void onSuccess(DocumentReference documentReference) {
               Log.d(TAG,"DocumentSnapshot written with ID: " + documentReference.getId());
            }
        })
        .addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error adding workout",e);
            }
        });
    }

    public void update(WorkoutModel workout) {

    }

    public void delete(WorkoutModel workout) {

    }

    public void getAllWorkouts() {
        workoutRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    onFirestoreTaskComplete.workoutListDataAdded(task.getResult().toObjects(WorkoutModel.class));
                    Log.i(TAG,"Alle workouts geladen");
                } else {
                    onFirestoreTaskComplete.onError(task.getException());
                    Log.i(TAG,"Workouts konnten nicht geladen werden");

                }
            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void workoutListDataAdded(List<WorkoutModel> workoutModelList);
        void onError(Exception e);
    }

}
