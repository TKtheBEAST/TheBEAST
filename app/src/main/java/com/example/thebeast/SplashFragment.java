package com.example.thebeast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.viewmodel.LoginFragmentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class SplashFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LoginFragmentViewModel loginFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initialisieren ViewModel
        loginFragmentViewModel = new ViewModelProvider(getActivity()).get(LoginFragmentViewModel.class);
        mAuth = FirebaseAuth.getInstance();


        final Handler splashScreenHandler = new Handler(Looper.getMainLooper());
        splashScreenHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        getUserInformation();
                    } else {
                        mAuth.getCurrentUser().sendEmailVerification();
                        Toast.makeText(getActivity(), "Überprüfe deine Mails und verifiziere deine Mail", Toast.LENGTH_LONG);
                        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
                    }
                } else {
                    Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
                    return;
                }
            }
        }, 2000);

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    public void getUserInformation() {

        db.collection("User").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            UserModel currentUser;
                            currentUser = task.getResult().toObject(UserModel.class);
                            CurrentUser.setCurrentUser(currentUser);
                            loginFragmentViewModel.getFreundeCurrentUser();
                            Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_mainActivity);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

        /** db.collection("Workouts").whereEqualTo("workoutOwnerID", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    List<WorkoutModel> workoutsOfCurrentUser = task.getResult().toObjects((WorkoutModel.class));
                    CurrentUser.getCurrentUser().setWorkoutsCurrentUser(workoutsOfCurrentUser);

                }else{
                    return;
                }
            }
        }); **/

    }
}