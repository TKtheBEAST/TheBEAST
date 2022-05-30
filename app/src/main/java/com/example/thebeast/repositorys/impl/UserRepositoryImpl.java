package com.example.thebeast.repositorys.impl;

import android.app.Application;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.thebeast.CurrentUser;
import com.example.thebeast.StartActivity;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.daos.UserDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.repositorys.UserRepository;
import com.example.thebeast.roomdatabase.TheBeastDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private static final String TAG = "UserRepositoryImpl";

    private final int standardWorkoutlaenge = 2;
    private List<UserModel> allUsers;
    private List<UserModel> freundeOfCurrentUser = new ArrayList<>();


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private CollectionReference freundVonUserRef = firebaseFirestore.collection("User").document("*").collection("FreundeVonUser");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("avatare/");
    

    public void createUserWithEmailAndPassword(String beastName, String beastSpruch, String email, String password, String token){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Log.i(TAG, "User konnte mit Auth erstellt werden.");
                        UserModel user = new UserModel(beastName, beastSpruch, email, standardWorkoutlaenge, token);
                        Map<String, Object> data = new HashMap<>();
                        data.put("beastID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        data.put("beastName", user.getBeastName());
                        data.put("beastSpruch", user.getBeastSpruch());
                        data.put("beastEmail", user.getBeastEmail());
                        data.put("workoutlaenge", user.getWorkoutlaenge());
                        data.put("token", user.getToken());


                        userRef.document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, "User wurde in der Datenbank hinzugefügt.");
                                        } else {
                                            Log.e(TAG, "User konnte nicht in der Datenbank hinzugefügt werden" + task.getException().toString());
                                        }

                                    }

                                });
                    }else{
                        Log.e(TAG, "User konnte nicht erstellt werdne");
                    }
                }
            });

    }


    public void updateWorkoutLaenge(float workoutLaenge){
        if(CurrentUser.getCurrentUser() == null){
            Log.w(TAG, "Kein Current User vorhanden!");
            return;
        }

        Map<String,Object> updates = new HashMap<>();
        updates.put("workoutlaenge",workoutLaenge);

        userRef.document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            CurrentUser.getCurrentUser().setWorkoutlaenge(workoutLaenge);
                            Log.i(TAG, "Workoutlaenge von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich geupdetet.");
                        }else{
                            Log.e(TAG, "Workoutlaenge von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte nicht upgedated werden.");
                        }
                    }
                });

        if(CurrentUser.getCurrentUser().getFreundeCurrentUser() != null) {
            for (UserModel freund : CurrentUser.getCurrentUser().getFreundeCurrentUser()) {
                userRef.document(freund.getBeastId()).collection("FreundeVonUser")
                        .document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    CurrentUser.getCurrentUser().setWorkoutlaenge(workoutLaenge);
                                    Log.i(TAG, "Workoutlaenge von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich bei allen Freunden geupdetet.");
                                } else {
                                    Log.e(TAG, "Workoutlaenge von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte bei den Freunden nicht upgedated werden.");
                                }
                            }
                        });

            }
        }else{
            Log.w(TAG, "Workoutlaenge wurde nicht bei den Freunden geupdated da Freunde of CurrentUser null ist.");
        }

    }

    public void updateBeastName(String neuerBeastName){
        if(CurrentUser.getCurrentUser() == null){
            Log.w(TAG, "Kein Current User vorhanden!");
            return;
        }

        Map<String,Object> updates = new HashMap<>();
        updates.put("beastName",neuerBeastName);

        userRef.document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            CurrentUser.getCurrentUser().setBeastName(neuerBeastName);
                            Log.i(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich geupdetet.");
                        }else{
                            Log.e(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte nicht upgedated werden." + task.getException().toString());
                        }
                    }
                });

        for (UserModel freund: CurrentUser.getCurrentUser().getFreundeCurrentUser()){
            userRef.document(freund.getBeastId()).collection("FreundeVonUser")
                    .document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                    .addOnCompleteListener(new OnCompleteListener<Void>(){

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                CurrentUser.getCurrentUser().setBeastName(neuerBeastName);
                                Log.i(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich bei allen Freunden geupdetet.");
                            }else{
                                Log.e(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte bei den Freunden nicht upgedated werden." + task.getException().toString());
                            }
                        }
                    });

        }
    }

    public void updateAvatar(Uri avatar){
        if(CurrentUser.getCurrentUser() == null){
            Log.w(TAG, "Kein Current User vorhanden!");
            return;
        }

        StorageReference fileReference = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "." + avatar);
        fileReference.putFile(avatar).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("avatar", uri.toString());

                        userRef.document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                                .addOnCompleteListener(new OnCompleteListener<Void>(){

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.i(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich geupdetet.");
                                        }else{
                                            Log.e(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte nicht upgedated werden." + task.getException().toString());
                                        }
                                    }
                                });

                        for (UserModel freund: CurrentUser.getCurrentUser().getFreundeCurrentUser()){
                            userRef.document(freund.getBeastId()).collection("FreundeVonUser")
                                    .document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>(){

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Log.i(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich bei allen Freunden geupdetet.");
                                            }else{
                                                Log.e(TAG, "BeastName von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte bei den Freunden nicht upgedated werden." + task.getException().toString());
                                            }
                                        }
                                    });

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO: on Failure abgreifen
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // TODO: on Failure abgreifen
            }
        });

    }

    public void deleteUser(){
        if(CurrentUser.getCurrentUser() == null){
            Log.w(TAG, "Kein Current User vorhanden!");
            return;
        }
        userRef.document(CurrentUser.getCurrentUser().getBeastId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "User " + CurrentUser.getCurrentUser().getBeastName() + "wurde aus der Datenbank entfernt");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "User konnte nicht aus der Datenbank entfernt werden. ", e);
                    }
        });
    }

    public List<UserModel> getAllUsers(){

        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    allUsers = task.getResult().toObjects(UserModel.class);
                    Log.i(TAG, "Alle User konnten geladen werden");
                }else{
                    Log.e(TAG,"User konnten nicht geladen werden. " + task.getException().toString());
                }
            }
        });
        return allUsers;
    }

    public void setCurrentUser(String email){

        userRef.whereEqualTo("beastEmail",email).get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<UserModel> user = new ArrayList();
                        user = task.getResult().toObjects(UserModel.class);
                        if(user.size() > 0){
                            UserModel currentUser = new UserModel();
                            currentUser = user.get(0);
                            CurrentUser.setCurrentUser(currentUser);
                            getFreundeCurrentUser();
                            //  getWorkoutsCurrentUser();
                        }else{
                            Log.e(TAG, "Current User konnte nicht gesetz wurden, da kein User in der Datenbank gefunden wurde");
                        }
                    }
                });
    }

    private void getWorkoutsCurrentUser() {
        if(CurrentUser.getCurrentUser() == null){
            Log.w(TAG, "Kein Current User vorhanden!");
            return;
        }
        firebaseFirestore.collection("Workouts").whereEqualTo("workoutOwnerID", CurrentUser.getCurrentUser().getBeastId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<WorkoutModel> workoutsOfCurrentUser = task.getResult().toObjects((WorkoutModel.class));
                    CurrentUser.getCurrentUser().setWorkoutsCurrentUser(workoutsOfCurrentUser);
                    Log.i(TAG, "Die Workouts von User " + CurrentUser.getCurrentUser().getBeastName() + " konnten geladen werden.");
                }else{
                    Log.w(TAG, "Die Workouts vom User konnten nicht geladen werden.");
                }
            }
        });
    }

    public void getFreundeCurrentUser(){
        if(CurrentUser.getCurrentUser() == null){
            Log.w(TAG, "Kein Current User vorhanden!");
            return;
        }
        firebaseFirestore.collection("User")
                .document(CurrentUser.getCurrentUser().getBeastId())
                .collection("FreundeVonUser").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freundeOfCurrentUser = task.getResult().toObjects((UserModel.class));
                            CurrentUser.getCurrentUser().setFreundeCurrentUser(freundeOfCurrentUser);
                        }else{
                            Log.e(TAG, "FreundeVonUser " + CurrentUser.getCurrentUser().getBeastName() + " konnten nicht geladen werden.");
                        }
                    }
                });
    }
}
