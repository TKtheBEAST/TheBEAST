package com.example.thebeast.repositorys.impl;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.thebeast.CurrentUser;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.daos.UserDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.repositorys.UserRepository;
import com.example.thebeast.roomdatabase.TheBeastDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    

    public void createUserWithEmailAndPassword(String beastName, String beastSpruch, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    UserModel user = new UserModel(beastName, beastSpruch, email, standardWorkoutlaenge, 0);
                    Map<String,Object> data = new HashMap<>();
                    data.put("beastID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                    data.put("beastName", user.getBeastName());
                    data.put("beastSpruch", user.getBeastSpruch());
                    data.put("beastEmail", user.getBeastEmail());
                    data.put("workoutlaenge", user.getWorkoutlaenge());



                    userRef.add(data)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG,"User wurde hinzugefügt ");
                                    }else{
                                        Log.d(TAG,"User konnte nicht hinzugefuegt werden");
                                    }

                                }

                    });
                }
            });
    }

    public void insert(UserModel user){

    }

    @Override
    public void update(UserModel user) {

    }

    public void updateWorkoutLaenge(float workoutLaenge){
        Map<String,Object> updates = new HashMap<>();
        updates.put("workoutlaenge",workoutLaenge);

        userRef.document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            CurrentUser.getCurrentUser().setWorkoutlaenge(workoutLaenge);
                        }
                    }
                });

    }

    public void updateBeastName(String neuerBeastName){
        Map<String,Object> updates = new HashMap<>();
        updates.put("beastName",neuerBeastName);

        userRef.document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            CurrentUser.getCurrentUser().setBeastName(neuerBeastName);
                        }
                    }
                });

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

    public void setCurrentUser(String email){

        userRef.whereEqualTo("beastEmail",email).get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<UserModel> user = new ArrayList();
                        user = task.getResult().toObjects(UserModel.class);
                        UserModel currentUser = new UserModel();
                        currentUser = user.get(0);
                        CurrentUser.setCurrentUser(currentUser);
                        getFreundeCurrentUser();
                    }
                });
    }

    public void getFreundeCurrentUser(){
        firebaseFirestore.collection("User")
                .document(CurrentUser.getCurrentUser().getBeastId())
                .collection("Freunde von User").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            freundeOfCurrentUser = task.getResult().toObjects((UserModel.class));
                            CurrentUser.getCurrentUser().setFreundeCurrentUser(freundeOfCurrentUser);
                        }else{
                            return;
                        }
                    }
                });
    }


}
