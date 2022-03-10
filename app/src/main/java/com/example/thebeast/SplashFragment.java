package com.example.thebeast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thebeast.viewmodel.LoginFragmentViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class SplashFragment extends Fragment {

    private FirebaseAuth mAuth;
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
                        Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_mainActivity);
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
        loginFragmentViewModel.setCurrentUser(mAuth.getCurrentUser().getEmail());

    }
}