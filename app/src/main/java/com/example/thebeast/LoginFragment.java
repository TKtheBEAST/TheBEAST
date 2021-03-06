package com.example.thebeast;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.repositorys.impl.UserRepositoryImpl;
import com.example.thebeast.viewmodel.HomeFragmentViewModel;
import com.example.thebeast.viewmodel.LoginFragmentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class LoginFragment extends Fragment {

    private Button loginButton;
    private EditText emailEditText, passwortEditText;
    private TextView pwVergessenTextView, registrierenTextView;
    private ProgressBar loginProgressBar;
    private LoginFragmentViewModel loginFragmentViewModel;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private FirebaseAuth mAuth;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.loginButtonLog);
        emailEditText = view.findViewById(R.id.emailPwvET);
        passwortEditText = view.findViewById(R.id.passwortLogET);
        pwVergessenTextView = view.findViewById(R.id.pwVergessenTV);
        registrierenTextView = view.findViewById(R.id.loginRegTV);
        loginProgressBar = view.findViewById(R.id.loginProgressBar);

        //initialisieren ViewModel
        loginFragmentViewModel = new ViewModelProvider(getActivity()).get(LoginFragmentViewModel.class);

        mAuth = FirebaseAuth.getInstance();

        registrierenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrierenFragment);
            }
        });

        pwVergessenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_passwortVergessenFragment);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        pwVergessenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_passwortVergessenFragment);
            }
        });



        return view;
    }

    private void userLogin() {
        String email = emailEditText.getText().toString().trim();
        String passwort = passwortEditText.getText().toString().trim();


        if(email.isEmpty()){
            emailEditText.setError("Bitte gebe deine Email an");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Bitte gebe eine richtige Email an");
            emailEditText.requestFocus();
            return;
        }
        if(passwort.isEmpty()){
            passwortEditText.setError("Bitte gebe ein Passwort ein");
            passwortEditText.requestFocus();
            return;
        }
        if(passwort.length() < 6){
            passwortEditText.setError("Das Passwort muss mindestens 6 zeichen enthalten");
            passwortEditText.requestFocus();
            return;
        }

        loginProgressBar.setVisibility(VISIBLE);

        mAuth.signInWithEmailAndPassword(email,passwort).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();

                if(task.isSuccessful()){
                    if(user.isEmailVerified()){
                        if(mAuth.getCurrentUser() != null){
                            getUserInformation();
                        }else{
                            Toast.makeText(getActivity(), "Hier ist etwas schief gelaufen. Versuche es noch einmal", Toast.LENGTH_LONG);
                            return;
                        }
                    }else{
                        user.sendEmailVerification();
                        loginProgressBar.setVisibility(GONE);
                        Toast.makeText(getActivity(),"??berpr??fe deine Mails und verifiziere deine Mail",Toast.LENGTH_LONG).show();
                    }
                }else{
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidUserException e){
                        loginProgressBar.setVisibility(GONE);
                        emailEditText.setError("User existiert nicht oder ist nicht weiter valide. " +
                                "Bitte noche einmal registrieren.");
                        emailEditText.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        loginProgressBar.setVisibility(GONE);
                        emailEditText.setError("??berpr??fe deine Benutzerdaten.");
                        emailEditText.requestFocus();
                    } catch (Exception e){
                        loginProgressBar.setVisibility(GONE);
                        Toast.makeText(getActivity(),"Falsche Benutzerdaten",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    public void getUserInformation(){
        db.collection("User").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            UserModel currentUser;
                            currentUser = task.getResult().toObject(UserModel.class);
                            CurrentUser.setCurrentUser(currentUser);
                            loginFragmentViewModel.getFreundeCurrentUser();
                            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_mainActivity);
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


       // loginFragmentViewModel.setCurrentUser(mAuth.getCurrentUser().getEmail());
    }
}