package com.example.thebeast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginFragment extends Fragment {

    private Button loginButton;
    private EditText emailEditText;
    private EditText passwortEditText;

    private TextView pwVergessenTextView;
    private TextView registrierenTextView;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.loginButtonLog);
        emailEditText = view.findViewById(R.id.emailLogET);
        passwortEditText = view.findViewById(R.id.passwortLogET);
        pwVergessenTextView = view.findViewById(R.id.pwVergessenTV);
        registrierenTextView = view.findViewById(R.id.registrierenLogTV);


        registrierenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrierenFragment);
            }
        });





        return view;
    }

}