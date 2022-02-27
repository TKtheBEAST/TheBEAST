package com.example.thebeast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PasswortVergessenFragment extends Fragment {

    private Button zuruecksetzenButton;
    private EditText emailEditText;
    private ProgressBar pwVprogressBar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_passwort_vergessen, container, false);

        zuruecksetzenButton = view.findViewById(R.id.zuruecksetzenPwvButton);
        emailEditText = view.findViewById(R.id.emailPwvET);
        pwVprogressBar = view.findViewById(R.id.pwVprogressBar);

        mAuth = FirebaseAuth.getInstance();

        zuruecksetzenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswort();
            }
        });

        return view;
    }

    private void resetPasswort(){
        String email = emailEditText.getText().toString().trim();

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

        pwVprogressBar.setVisibility(VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pwVprogressBar.setVisibility(GONE);
                    Toast.makeText(getView().getContext(), "Öffne deine Mails um das Passowrt zurückzusetzen", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_passwortVergessenFragment_to_loginFragment);
                }else{
                    pwVprogressBar.setVisibility(GONE);
                    Toast.makeText(getView().getContext(), "Versuche es noch einmal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}