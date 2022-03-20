package com.example.thebeast.repositorys.impl;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thebeast.CurrentUser;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.repositorys.WorkoutRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.AsyncEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkoutRepositoryImpl implements WorkoutRepository {

    private static final String TAG = "WorkoutRepositoryImpl";

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference workoutRef = firebaseFirestore.collection("Workouts");
    private List<WorkoutModel> workouts = new ArrayList<>();

    private OnFirstoreTaskComplete onFirstoreTaskComplete;


    public WorkoutRepositoryImpl(OnFirstoreTaskComplete onFirstoreTaskComplete) {
        this.onFirstoreTaskComplete = onFirstoreTaskComplete;
    }

    public WorkoutRepositoryImpl() {
    }


    public void insert(WorkoutModel workout) {


        //add new Workout with timestamp as id
        Map<String, Object> data = new HashMap();
        data.put("workoutOwnerID", workout.getWorkoutOwnerID());
        data.put("beastName", workout.getBeastName());
        data.put("uebungen", workout.getUebungen());
        data.put("workoutlaenge", workout.getWorkoutlaenge());
        data.put("startzeit", workout.getStartzeit());


        firebaseFirestore.collection("Workouts")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding workout", e);
                    }
                });
    }

    public void update(WorkoutModel workout) {

    }

    public void delete(WorkoutModel workout) {

    }


    public void refreshWorkouts() {
        List<WorkoutModel> workouts = new ArrayList<>();
        for (UserModel freund : CurrentUser.getCurrentUser().getFreundeCurrentUser()) {
            workoutRef.whereEqualTo("workoutOwnerID", freund.getBeastId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<WorkoutModel> addWorkout = new ArrayList<>();
                        addWorkout = task.getResult().toObjects(WorkoutModel.class);
                        workouts.add(addWorkout.get(0));
                        onFirstoreTaskComplete.workoutModelsListAdded(workouts);
                    } else {
                        onFirstoreTaskComplete.onError(task.getException());
                        Log.i(TAG, "Workouts konnten nicht geladen werden");
                    }
                }
            });
        }
        Log.i(TAG, "Alle workouts geladen");

    }

    public void getAllWorkouts() {

        for (UserModel freund : CurrentUser.getCurrentUser().getFreundeCurrentUser()) {
            workoutRef.whereEqualTo("workoutOwnerID", freund.getBeastId()).addSnapshotListener(new EventListener<QuerySnapshot>() {

                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                    assert value != null;

                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        workouts.clear();
                    }


                    for (DocumentSnapshot ds : value.getDocuments()) {
                        WorkoutModel workoutModel = ds.toObject(WorkoutModel.class);
                        assert workoutModel != null;

                        if (workoutIsRunning(workoutModel.getStartzeit(), workoutModel.getWorkoutlaenge()) == true) {
                            workouts.add(workoutModel);
                            onFirstoreTaskComplete.workoutModelsListAdded(workouts);
                        }

                    }

                }
            });
        }
        Log.i(TAG, "Alle workouts geladen");

    }

    private boolean workoutIsRunning(String startzeit, float workoutlaenge) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        String currentJahr = currentDateandTime.substring(0, 3);
        String currentMonat = currentDateandTime.substring(5, 6);
        String currentTag = currentDateandTime.substring(8, 9);

        String workoutJahr = startzeit.substring(0, 3);
        String workoutMonat = startzeit.substring(5, 6);
        String workoutTag = startzeit.substring(8, 9);


        if (!currentJahr.equals(workoutJahr) || !currentMonat.equals(workoutMonat) || !currentTag.equals(workoutTag)) {
            return false;
        }

        String workoutStunde = startzeit.substring(11, 12);
        String workoutMinute = startzeit.substring(14, 15);

        String currentStunde = currentDateandTime.substring(11, 12);
        String currentMinute = currentDateandTime.substring(14, 15);


        //TODO: errechnen von workoutende. Hier fehlt noch die minute
        float workoutStundeFloat = Integer.parseInt(workoutStunde);
        float workoutMinuteFloat = Integer.parseInt(workoutMinute);

        float workoutEndeStunde;
        float workoutEndeMinute;
        float stundeergaenzen;



        float currentStundeFloat = Integer.parseInt(currentStunde);
        float currentMinuteFloat = Integer.parseInt(currentMinute);
        //überprüfen stunde und minute
        if (currentStundeFloat - workoutEndeStunde > 0) {
            return false;
        }
        if (currentStundeFloat - workoutEndeStunde == 0) {
            if (currentMinuteFloat - workoutEndeMinute > 0) {
                return false;
            }
        }


        return true;
    }


    public interface OnFirstoreTaskComplete {
        void workoutModelsListAdded(List<WorkoutModel> workoutModels);

        void onError(Exception e);
    }


}
