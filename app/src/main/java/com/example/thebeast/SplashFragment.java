package com.example.thebeast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SplashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Handler splashScreenHandler = new Handler(Looper.getMainLooper());
        splashScreenHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(getView()).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 3000);

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
}