package com.example.thebeast;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebeast.viewmodel.EinstellungenFragmentViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class EinstellungenFragment extends Fragment {

    Button abmeldenButton,dreißigMinButton,eineHbutton,eineHdreißigButton,zweiHbutton;
    private float aktuelleWorkoutlaenge;
    private TextView beastName, beastEmail;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

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

        beastName = view.findViewById(R.id.beastNameEinsTV);
        beastEmail = view.findViewById(R.id.beastEmailEinstTV);

        beastName.setText(CurrentUser.getCurrentUser().getBeastName());
        beastEmail.setText(CurrentUser.getCurrentUser().getBeastEmail());

        beastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beastNameAnpassenDialog();
            }
        });

        dreißigMinButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(aktuelleWorkoutlaenge == 0.5){
                    Toast.makeText(getActivity(),"Diese Workoutlänge ist bereits hinterlegt",Toast.LENGTH_LONG).show();
                }else{
                    einstellungenFragmentViewModel.updateWorkoutlaenge(0.5f);
                    startActivity(new Intent(getActivity(),MainActivity.class));
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
                    startActivity(new Intent(getActivity(),MainActivity.class));
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
                    startActivity(new Intent(getActivity(),MainActivity.class));
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
                    startActivity(new Intent(getActivity(),MainActivity.class));
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

    public void beastNameAnpassenDialog(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View beastNameAendernView = getLayoutInflater().inflate(R.layout.beastname_aendern_popup,null);


        Button beastNameuebernehmenButton = beastNameAendernView.findViewById(R.id.uebernehmenBeastNaendernPopup);
        Button abbrechenButton = beastNameAendernView.findViewById(R.id.abbrechenBeastNaendernPopup);
        EditText beastNameTextView = beastNameAendernView.findViewById(R.id.beastNameaendernPopupET);

        beastNameTextView.setHint(CurrentUser.getCurrentUser().getBeastName());

        beastNameuebernehmenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String neuerBeastName = beastNameTextView.getText().toString();
                einstellungenFragmentViewModel.updateBeastName(neuerBeastName);
                startActivity(new Intent(getActivity(),MainActivity.class));
                dialog.dismiss();
            }
        });

        abbrechenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(beastNameAendernView);
        dialog = dialogBuilder.create();
        dialog.show();


    }
    

}