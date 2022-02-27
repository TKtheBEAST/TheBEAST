package com.example.thebeast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
import com.example.thebeast.viewmodel.RegistrierenFragmentViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;


public class RegistrierenFragment extends Fragment {


    private RegistrierenFragmentViewModel registrierenFragmentViewModel;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private final int standardWorkoutlaenge = 2;

    private NavController nav;


    private FirebaseAuth mAuth;

    private Button registrierenButton;
    private EditText beastNameEditText,beastSpruchEditText,emailEditText,passwort1EditText,passwort2EditText;
    private TextView loginTV;
    private ProgressBar registrierenProgressBar;



    public RegistrierenFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrieren, container, false);


        registrierenButton = view.findViewById(R.id.registrierenButton);
        beastNameEditText = view.findViewById(R.id.beastnameRegET);
        beastSpruchEditText = view.findViewById(R.id.beastSpruchRegET);
        emailEditText = view.findViewById(R.id.emailRegET);
        passwort1EditText = view.findViewById(R.id.password1RegET);
        passwort2EditText = view.findViewById(R.id.password2RegET);
        registrierenProgressBar = view.findViewById(R.id.registrierenProgressBar);
        loginTV = view.findViewById(R.id.loginRegTV);


        mAuth = FirebaseAuth.getInstance();


        registrierenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                registrieren();
            }
        });
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registrierenFragment_to_loginFragment);
            }
        });


        return view;
    }

    public void registrieren (){
        String beastName = beastNameEditText.getText().toString().trim();
        String beastSpruch = beastSpruchEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String passwort1 = passwort1EditText.getText().toString().trim();
        String passwort2 = passwort2EditText.getText().toString().trim();

        if(beastName.isEmpty()){
            beastNameEditText.setError("Bitte gebe einen Spitznamen ein");
            beastNameEditText.requestFocus();
            return;
        }
        if(beastSpruch.isEmpty()){
            beastSpruchEditText.setError("Sag den anderen was du für ein Beast bist");
            beastSpruchEditText.requestFocus();
            return;
        }
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
        if(passwort1.isEmpty()){
            passwort1EditText.setError("Bitte gebe ein Passwort ein");
            passwort2EditText.requestFocus();
            return;
        }
        if(passwort1.length() < 6){
            passwort1EditText.setError("Das Passwort muss mindestens 6 zeichen enthalten");
            passwort1EditText.requestFocus();
            return;
        }
        if(!passwort2.equals(passwort1)){
            passwort2EditText.setError("Die Passwörter stimmen nicht überein");
            passwort2EditText.requestFocus();
            return;
        }

        registrierenProgressBar.setVisibility(View.VISIBLE);

        //über Viemodel und Repository??
        //registrierenFragmentViewModel.createUserWithEmailAndPassword(beastName, beastSpruch, email, passwort2);

        mAuth.createUserWithEmailAndPassword(email, passwort2)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        UserModel user = new UserModel(beastName, beastSpruch, email, standardWorkoutlaenge, 0);
                        Map<String,Object> data = new HashMap<>();
                        data.put("beastID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        data.put("beastName", user.getBeastName());
                        data.put("beastSpruch", user.getSpruch());
                        data.put("beastEmail", user.getEmail());
                        data.put("workoutlaenge", user.getWorkoutlaenge());



                        userRef.add(data)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if(task.isSuccessful()){
                                            user.sendEmailVerification();
                                            Toast.makeText(getView().getContext(), beastName+" wurde erfolgreich hinzugefügt!" +
                                                    " Checke deine Mails um deine Mail zu verifizieren", Toast.LENGTH_LONG).show();
                                            Log.d(TAG,"User wurde hinzugefügt ");
                                            registrierenProgressBar.setVisibility(GONE);
                                            Navigation.findNavController(getView()).navigate(R.id.action_registrierenFragment_to_loginFragment);

                                        }else{
                                            Toast.makeText(getView().getContext(), "Du konntest nicht Registriert werden :( Versuche es noch einmal!", Toast.LENGTH_LONG).show();
                                            Log.d(TAG,"User konnte nicht hinzugefuegt werden Firestore");
                                            registrierenProgressBar.setVisibility(GONE);
                                        }
                                    }
                                });
                    }else{
                        Toast.makeText(getView().getContext(), "Du konntest nicht Registriert werden :( Versuche es noch einmal!", Toast.LENGTH_LONG).show();
                        Log.d(TAG,"User konnte nicht hinzugefuegt werden Firebase Auth");
                        registrierenProgressBar.setVisibility(GONE);
                    }

                }
            });

    }

}