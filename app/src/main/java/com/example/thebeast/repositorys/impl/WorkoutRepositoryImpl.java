package com.example.thebeast.repositorys.impl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.repositorys.WorkoutRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutRepositoryImpl implements WorkoutRepository {

    private static final String TAG = "WorkoutRepositoryImpl";

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference workoutRef = firebaseFirestore.collection("Workouts");

    private MutableLiveData<List<WorkoutModel>> allWorkoutModels;

    public void insert(WorkoutModel workout) {

        Log.i(TAG,"lily"+workout.getBeastName());

        //add new Workout with a generated id
        Map<String, Object> data = new HashMap();
        data.put("beastName",workout.getBeastName());
        data.put("uebungen",workout.getUebungen());
        data.put("workoutlaenge",workout.getWorkoutlaenge());

        firebaseFirestore.collection("Workouts")
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

    public void loadAllWorkouts() {
        List<WorkoutModel> allWorkoutModelsSample = new ArrayList<>();

        workoutRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().toObjects(WorkoutModel.class);
                    for(QueryDocumentSnapshot document: task.getResult()){
                        WorkoutModel newworkout = document.toObject(WorkoutModel.class);
                        allWorkoutModelsSample.add(newworkout);
                    }
                    Log.i(TAG,"Alle workouts geladen");
                } else {
                    Log.i(TAG,"Workouts konnten nicht geladen werden");

                }
            }

        });
        allWorkoutModels.setValue(allWorkoutModelsSample);

    }

    public LiveData<List<WorkoutModel>> getAllWorkouts(){
        if (allWorkoutModels == null){
            allWorkoutModels = new MutableLiveData<>();


            workoutRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<WorkoutModel> allWorkoutModelsSample = new ArrayList<>();

                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot document: task.getResult()){
                            WorkoutModel newworkout = document.toObject(WorkoutModel.class);
                            allWorkoutModelsSample.add(newworkout);
                        }
                        allWorkoutModels.setValue(allWorkoutModelsSample);
                        Log.i(TAG,"Alle workouts geladen");
                    } else {
                        Log.i(TAG,"Workouts konnten nicht geladen werden");

                    }
                }

            });
            return allWorkoutModels;

        }else {
            return allWorkoutModels;
        }
    }


    public void OnFirestoreTaskComplete(){
    }


}
