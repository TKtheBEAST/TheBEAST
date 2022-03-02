package com.example.thebeast;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.example.thebeast.R;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.viewmodel.EinstellungenFragmentViewModel;
import com.example.thebeast.viewmodel.HomeFragmentViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class EinstellungenFragment extends Fragment {

    Button abmeldenButton,dreißigMinButton,eineHbutton,eineHdreißigButton,zweiHbutton;
    private float aktuelleWorkoutlaenge;

    EinstellungenFragmentViewModel einstellungenFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        abmeldenButton = view.findViewById(R.id.abmeldenButton);
        dreißigMinButton = view.findViewById(R.id.trainingslaenge30minButton);
        eineHbutton = view.findViewById(R.id.trainingslaenge1hButton);
        eineHdreißigButton = view.findViewById(R.id.trainingslaenge1_5hButton);
        zweiHbutton = view.findViewById(R.id.trainingslaenge2hButton);

        dreißigMinButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(aktuelleWorkoutlaenge == 0.5){
                    Toast.makeText(getActivity(),"Diese Workoutlänge ist bereits hinterlegt",Toast.LENGTH_LONG).show();
                }else{
                    einstellungenFragmentViewModel.updateWorkoutlaenge(0.5f);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        eineHbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aktuelleWorkoutlaenge == 1){
                    Toast.makeText(getActivity(),"Diese Workoutlänge ist bereits hinterlegt",Toast.LENGTH_LONG).show();
                }else {
                    einstellungenFragmentViewModel.updateWorkoutlaenge(1f);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        eineHdreißigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aktuelleWorkoutlaenge == 1.5){
                    Toast.makeText(getActivity(),"Diese Workoutlänge ist bereits hinterlegt",Toast.LENGTH_LONG).show();
                }else {
                    einstellungenFragmentViewModel.updateWorkoutlaenge(1.5f);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        zweiHbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aktuelleWorkoutlaenge == 2){
                    Toast.makeText(getActivity(),"Diese Workoutlänge ist bereits hinterlegt",Toast.LENGTH_LONG).show();
                }else {
                    einstellungenFragmentViewModel.updateWorkoutlaenge(2f);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        aktuelleWorkoutlaenge = CurrentUser.getCurrentUser().getWorkoutlaenge();
        setFocusAktuelleLaenge(aktuelleWorkoutlaenge);


        //initialisieren ViewModel
        einstellungenFragmentViewModel = new ViewModelProvider(getActivity()).get(EinstellungenFragmentViewModel.class);


        abmeldenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),StartActivity.class));
            }
        });

        return view;
    }


    public void setFocusAktuelleLaenge(float aktuelleWorkoutlaenge){
        if(aktuelleWorkoutlaenge == 0.5){
            dreißigMinButton.setBackgroundColor(Color.RED);
        }
        if(aktuelleWorkoutlaenge == 1){
            eineHbutton.setBackgroundColor(Color.RED);
        }
        if(aktuelleWorkoutlaenge == 1.5){
            eineHdreißigButton.setBackgroundColor(Color.RED);
        }
        if(aktuelleWorkoutlaenge == 2){
            zweiHbutton.setBackgroundColor(Color.RED);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        aktuelleWorkoutlaenge = CurrentUser.getCurrentUser().getWorkoutlaenge();
        dreißigMinButton.refreshDrawableState();
        setFocusAktuelleLaenge(aktuelleWorkoutlaenge);
    }
    

}