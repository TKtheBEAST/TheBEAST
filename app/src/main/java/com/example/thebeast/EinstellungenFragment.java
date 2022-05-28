package com.example.thebeast;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.thebeast.viewmodel.EinstellungenFragmentViewModel;
import com.google.firebase.auth.FirebaseAuth;

import static android.app.Activity.RESULT_OK;

public class EinstellungenFragment extends Fragment {

    private static final String TAG = "EinstellungenFragment";
    private Button abmeldenButton,kontoloeschenButton,dreißigMinButton,eineHbutton,eineHdreißigButton,zweiHbutton;
    private ImageView avatarImageView;

    private static final int PICK_IMAGE_REQUEST = 1;

    private float aktuelleWorkoutlaenge;
    private TextView beastName, beastEmail;

    private Uri uriImage;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    EinstellungenFragmentViewModel einstellungenFragmentViewModel;

    public EinstellungenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        abmeldenButton = view.findViewById(R.id.abmeldenButton);
        kontoloeschenButton = view.findViewById(R.id.kontoLoeschenButton);
        dreißigMinButton = view.findViewById(R.id.trainingslaenge30minButton);
        eineHbutton = view.findViewById(R.id.trainingslaenge1hButton);
        eineHdreißigButton = view.findViewById(R.id.trainingslaenge1_5hButton);
        zweiHbutton = view.findViewById(R.id.trainingslaenge2hButton);

        avatarImageView = view.findViewById(R.id.avatarEinstellungenIV);

        if(CurrentUser.getCurrentUser() != null){
            String imageUrl = CurrentUser.getCurrentUser().getAvatar();
            Glide.with(getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(avatarImageView);
        }else{
            Log.w(TAG, "Kein Current User vorhanden! Avatar kann nicht gesetzt werden.");
        }


        beastName = view.findViewById(R.id.beastNameEinsTV);
        beastEmail = view.findViewById(R.id.beastEmailEinstTV);

        if(CurrentUser.getCurrentUser() != null){
            beastName.setText(CurrentUser.getCurrentUser().getBeastName());
            beastEmail.setText(CurrentUser.getCurrentUser().getBeastEmail());
        }else{
            beastName.setText("Fehler beim Laden");
            beastEmail.setText("Fehler beim Laden");
            Log.w(TAG, "Kein Current User vorhanden! BeastName und BeastMail kann nicht gesetzt werden.");
        }


        avatarImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


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

        if(CurrentUser.getCurrentUser() != null){
            aktuelleWorkoutlaenge = CurrentUser.getCurrentUser().getWorkoutlaenge();
            setFocusAktuelleLaenge(aktuelleWorkoutlaenge);
        }else{
            Log.w(TAG, "Kein Current User vorhanden! Workoutlaenge kann nicht gesetzt werden.");
        }



        //initialisieren ViewModel
        einstellungenFragmentViewModel = new ViewModelProvider(getActivity()).get(EinstellungenFragmentViewModel.class);


        abmeldenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),StartActivity.class));
            }
        });

        kontoloeschenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kontoLoeschenDialog();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            avatarAendernDialog();
        }
    }

    public void avatarAendernDialog(){

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

        if(CurrentUser.getCurrentUser() != null){
            beastNameTextView.setHint(CurrentUser.getCurrentUser().getBeastName());
        }else{
            beastNameTextView.setHint("theBeast");
            Log.w(TAG, "Kein Current User vorhanden! Workoutlaenge kann nicht gesetzt werden.");
        }

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

    private void kontoLoeschenDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View kontoLoeschenView = getLayoutInflater().inflate(R.layout.konto_loeschen_popup,null);


        Button kontoLoeschenButton = kontoLoeschenView.findViewById(R.id.kontoLoeschenButton);
        Button abbrechenButton = kontoLoeschenView.findViewById(R.id.kontoLoeschenAbbrechenButton);

        kontoLoeschenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                einstellungenFragmentViewModel.deleteUser();
                FirebaseAuth.getInstance().getCurrentUser().delete();
                startActivity(new Intent(getActivity(),StartActivity.class));
            }
        });

        abbrechenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(kontoLoeschenView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
    

}