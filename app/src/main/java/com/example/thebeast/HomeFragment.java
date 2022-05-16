package com.example.thebeast;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.recyclerViewAdapter.GewaehlteTrainingsRecyclerViewAdapter;
import com.example.thebeast.viewmodel.HomeFragmentViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {

    //Viewmodel
    private HomeFragmentViewModel homeFragmentViewModel;

    //Firebase Instanzen
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Dialog
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    //RecyclerView
    private RecyclerView gewaehltesTrainingRecyclerView;
    private RecyclerView startWorkoutRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GewaehlteTrainingsRecyclerViewAdapter recyclerViewAdapter;

    //LinearLayout
    private LinearLayout homeLinearLayout;
    private LinearLayout aktuellesWorkoutLinearLayout;

    //Firebase
    private FirebaseFunctions mfunctions;

    //Button
    private ImageButton joggenButton, oberkoerperButton, pulldayButton, pushdayButton, beineButton, hiitButton, playButton;
    private Button workoutsEntfernenButton, workoutBeendenButton;

    //ImageViews
    private int joggenImageView, oberkoerperImageView, pulldayImageView, pushdayImageView, beinImageView, hiitImageView;

    //TextView anlegen
    private TextView deinGewaehltesTraining, trennstrichHome, aktuellesWorkoutUebungen, motivationsTextView;

    //Progressbar
    private ProgressBar aktuellesWorkoutProgressBar;

    //workouts CurrentUser
    private List<WorkoutModel> workoutsCurrentUser;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //initialisieren ViewModel
        homeFragmentViewModel = new ViewModelProvider(getActivity()).get(HomeFragmentViewModel.class);

        //initialisieren des RecyclerViews
        gewaehltesTrainingRecyclerView = view.findViewById(R.id.gewaehltesTrainingRecyclerview);

        //initialisieren der Layouts
        homeLinearLayout = view.findViewById(R.id.homeLinearLayout);
        aktuellesWorkoutLinearLayout = view.findViewById(R.id.aktuellesWorkoutLinearLayout);

        //initialisieren ProgressBar
        aktuellesWorkoutProgressBar = view.findViewById(R.id.aktuellesWorkoutProgressBar);

        //Button zuweisen
        joggenButton = view.findViewById(R.id.joggenButton);
        oberkoerperButton = view.findViewById(R.id.oberkoerperButton);
        pulldayButton = view.findViewById(R.id.pullDayButton);
        pushdayButton = view.findViewById(R.id.pushDayButton);
        beineButton = view.findViewById(R.id.beinButton);
        hiitButton = view.findViewById(R.id.hiitButton);
        playButton = view.findViewById(R.id.playButton);
        workoutBeendenButton = view.findViewById(R.id.workoutBeendenButton);
        playButton.setAlpha(0f);
        playButton.setEnabled(false);


        workoutsEntfernenButton = view.findViewById(R.id.workoutsEntfernen);

        //Textview initialisieren
        deinGewaehltesTraining = view.findViewById(R.id.deinGewaehltesTraining);
        trennstrichHome = view.findViewById(R.id.trennstrichHome);
        aktuellesWorkoutUebungen = view.findViewById(R.id.aktuellesWorkoutUebungenTV);
        motivationsTextView = view.findViewById(R.id.motivationTextView);


        //ImageView zuweisen
        joggenImageView = R.drawable.joggen;
        oberkoerperImageView = R.drawable.oberkoerper;
        pushdayImageView = R.drawable.pushday;
        pulldayImageView = R.drawable.pullday;
        beinImageView = R.drawable.bein;
        hiitImageView = R.drawable.hiit;

        //Button onClickListener setzen
        joggenButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), joggenImageView, "Joggen");
            }

        });

        oberkoerperButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), oberkoerperImageView, "Oberkörper Training");
            }
        });

        pushdayButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), pushdayImageView, "Push Day");
            }
        });

        pulldayButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), pulldayImageView, "Pull Day");
            }
        });

        beineButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), beinImageView, "Beintraining");
            }
        });

        hiitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), hiitImageView, "Hiit");
            }
        });


        workoutsEntfernenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                workoutsEntfernen();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startWorkoutDialog();
            }
        });

        workoutBeendenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                workoutBeenden();
            }
        });

        //checken ob es ein aktuelles Workout vom User gibt
        isWorkoutRunning();


        return view;
    }

    private void workoutBeenden() {

        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View startWorkoutView = getLayoutInflater().inflate(R.layout.workout_beenden_popup, null);

        Button beendenButton = startWorkoutView.findViewById(R.id.workoutBeendenButton);
        Button abbrechenButton = startWorkoutView.findViewById(R.id.workoutBeendenAbbrechenButton);

        beendenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                homeFragmentViewModel.workoutFruehzeitigBeenden(homeFragmentViewModel.getAktuellesWorkout());
                homeFragmentViewModel.setAktuellesWorkout(null);
                dialog.dismiss();
                isWorkoutRunning();
            }
        });


        abbrechenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(startWorkoutView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    private void setAktuellesWorkoutView() {
        homeLinearLayout.setVisibility(View.GONE);
        aktuellesWorkoutUebungen.setText(homeFragmentViewModel.getAktuellesWorkout().getUebungen());
        int progress = homeFragmentViewModel.getWorkoutProgress();
        aktuellesWorkoutProgressBar.setProgress(progress);
        aktuellesWorkoutLinearLayout.setVisibility(View.VISIBLE);
    }

    private void setHomeView(){
        homeLinearLayout.setVisibility(View.VISIBLE);
        aktuellesWorkoutLinearLayout.setVisibility(View.GONE);
        setMotivationsTextView();
    }

    private void setMotivationsTextView(){

        Resources res = getResources();
        String[] motivationssprueche = res.getStringArray(R.array.motivationssprueche);

        Random zufall = new Random();
        int zufallsZahl = zufall.nextInt(motivationssprueche.length); //Ganzahlige Zufallszahl zwischen 0 und Anzahl Motivationssprueche

        motivationsTextView.setVisibility(View.VISIBLE);
        motivationsTextView.setText(motivationssprueche[zufallsZahl]);


    }
    private void isWorkoutRunning() {
        homeFragmentViewModel.setWorkoutRunning(false);
        if(homeFragmentViewModel.getAktuellesWorkout() != null){
            if(homeFragmentViewModel.getAktuellesWorkout().isWorkoutFruehzeitigBeendet() == false){
                if(workoutIsRunning(homeFragmentViewModel.getAktuellesWorkout().getStartzeit(), homeFragmentViewModel.getAktuellesWorkout().getWorkoutlaenge()) == true) {
                    homeFragmentViewModel.setWorkoutRunning(true);
                    setAktuellesWorkoutView();
                    return;
                }else{
                    homeFragmentViewModel.setAktuellesWorkout(null);
                    setHomeView();
                }
            }else{
                homeFragmentViewModel.setAktuellesWorkout(null);
                setHomeView();
            }
        }
        db.collection("Workouts").whereEqualTo("workoutOwnerID", mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    List<WorkoutModel> workoutsOfCurrentUser = task.getResult().toObjects((WorkoutModel.class));
                    if(CurrentUser.getCurrentUser() == null){
                        setHomeView();
                        return;
                    }
                    CurrentUser.getCurrentUser().setWorkoutsCurrentUser(null);
                    CurrentUser.getCurrentUser().setWorkoutsCurrentUser(workoutsOfCurrentUser);
                    workoutsCurrentUser = CurrentUser.getCurrentUser().getWorkoutsCurrentUser();

                    for(WorkoutModel checkWorkout : workoutsCurrentUser){
                        if(checkWorkout.isWorkoutFruehzeitigBeendet() == false){
                            if(workoutIsRunning(checkWorkout.getStartzeit(), checkWorkout.getWorkoutlaenge()) == true){
                                homeFragmentViewModel.setWorkoutRunning(true);
                                homeFragmentViewModel.setAktuellesWorkout(checkWorkout);
                                break;
                            }
                        }
                    }

                    //falls ja setze das aktuelle Workout im HomeMenu - falls nein dann normales HomeView
                    if(homeFragmentViewModel.isWorkoutRunning() == true){
                        setAktuellesWorkoutView();
                    }else{
                        setHomeView();
                    }

                }else{
                    return;
                }
            }
        });


    }

    private boolean workoutIsRunning(String startzeit, float workoutlaenge) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        String currentJahr = currentDateandTime.substring(0, 4);
        String currentMonat = currentDateandTime.substring(5, 7);
        String currentTag = currentDateandTime.substring(8, 10);

        String workoutJahr = startzeit.substring(0, 4);
        String workoutMonat = startzeit.substring(5, 7);
        String workoutTag = startzeit.substring(8, 10);


        if (!currentJahr.equals(workoutJahr) || !currentMonat.equals(workoutMonat) || !currentTag.equals(workoutTag)) {
            return false;
        }

        String workoutStunde = startzeit.substring(11, 13);
        String workoutMinute = startzeit.substring(14, 16);

        String currentStunde = currentDateandTime.substring(11, 13);
        String currentMinute = currentDateandTime.substring(14, 16);

        boolean stundeergaenzen = false;

        float workoutStundeFloat = Integer.parseInt(workoutStunde);
        float workoutMinuteFloat = Integer.parseInt(workoutMinute);
        float workoutEndeStunde;
        float workoutEndeMinute = 0;


        if(workoutlaenge == 0.5 || workoutlaenge == 1.5){
            if(workoutMinuteFloat + 30 > 59){
                stundeergaenzen = true;
                workoutEndeMinute = workoutMinuteFloat - 30;
            }
        }else{
            workoutEndeMinute = workoutMinuteFloat;
        }

        workoutEndeStunde = workoutStundeFloat + (int) workoutlaenge;

        if(stundeergaenzen == true){
            workoutEndeStunde++;
        }


        float currentStundeFloat = Integer.parseInt(currentStunde);
        float currentMinuteFloat = Integer.parseInt(currentMinute);
        //überprüfen stunde und minute
        if (currentStundeFloat - workoutEndeStunde > 0) {
            return false;
        }
        if (currentStundeFloat - workoutEndeStunde == 0) {
            if (currentMinuteFloat - workoutEndeMinute > 0) {
                return false;
            }
        }

        homeFragmentViewModel.setWorkoutProgress(
                (currentStundeFloat - workoutStundeFloat) * 60 + (currentMinuteFloat - workoutMinuteFloat));

        return true;
    }



    public void startWorkoutDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View startWorkoutView = getLayoutInflater().inflate(R.layout.start_workout_popup, null);

        startWorkoutRecyclerView = startWorkoutView.findViewById(R.id.startWorkoutRecyclerView);
        layoutManager = new GridLayoutManager(startWorkoutView.getContext(), homeFragmentViewModel.getGewaehlteTrainingsList().size());
        startWorkoutRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new GewaehlteTrainingsRecyclerViewAdapter(homeFragmentViewModel.getGewaehlteTrainingsList(), homeFragmentViewModel.getGewaehlteTrainingsName());
        startWorkoutRecyclerView.setAdapter(recyclerViewAdapter);
        startWorkoutRecyclerView.setHasFixedSize(true);

        Button letsGoButton = startWorkoutView.findViewById(R.id.letsGoButton);
        Button abbrechenButton = startWorkoutView.findViewById(R.id.abbrechenWorkoutPopup);

        letsGoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String beastName = CurrentUser.getCurrentUser().getBeastName();
                String beastEmail = CurrentUser.getCurrentUser().getBeastEmail();
                String workoutOwnerID = CurrentUser.getCurrentUser().getBeastId();
                String avatar = CurrentUser.getCurrentUser().getAvatar();
                float workoutlaenge = CurrentUser.getCurrentUser().getWorkoutlaenge();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());

                //checken ob Location zugelassen ist
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    getActivity().finish();
                }

                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


                //stadort abfragen und workout erstellen
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            ArrayList<String> workoutsList;
                            workoutsList = homeFragmentViewModel.getGewaehlteTrainingsName();
                            String uebungen = "";
                            for (int i = 0; i < homeFragmentViewModel.getGewaehlteTrainingsName().size(); i++) {
                                uebungen = uebungen + workoutsList.get(i);
                                if (workoutsList.size() > i + 1) {
                                    uebungen = uebungen + ", ";
                                }
                            }


                            WorkoutModel workout;
                            if (location != null) {
                                String standort = getStandort(getActivity(), location.getLatitude(), location.getLongitude());
                                workout = new WorkoutModel(workoutOwnerID, beastName, beastEmail, uebungen, workoutlaenge, currentDateandTime, avatar, standort, location.getLongitude(), location.getLatitude());
                            } else {
                                workout = new WorkoutModel(workoutOwnerID, beastName, beastEmail, uebungen, workoutlaenge, currentDateandTime, avatar);
                            }

                            Log.i(TAG, "standort" + workout.getBeastName() + workout.getUebungen() + workout.getWorkoutlaenge() + workout.getStandort());
                            homeFragmentViewModel.insertWorkout(workout);

                            workoutsEntfernen();
                            isWorkoutRunning();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });


        abbrechenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(startWorkoutView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private String getStandort(Context ctx, double lat, double lng) {
        String standort = "";

        try{
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses.size() > 0){
                Address address = addresses.get(0);
                standort = address.getAddressLine(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return standort;
    }


    public void trainingHinzufuegen(View v, int training, String name) {

        deinGewaehltesTraining.setAlpha(1);
        trennstrichHome.setAlpha(1);
        workoutsEntfernenButton.setAlpha(0.5f);
        workoutsEntfernenButton.setEnabled(true);
        playButton.setEnabled(true);
        playButton.setAlpha(1f);

        boolean trainingBereitsGewaehlt = false;

        if (homeFragmentViewModel.getGewaehlteTrainingsList().size() > 0) {
            for (int i = 0; i < homeFragmentViewModel.getGewaehlteTrainingsName().size(); i++) {
                if (homeFragmentViewModel.getGewaehlteTrainingsName().get(i).equals(name)) {
                    trainingBereitsGewaehlt = true;
                }
            }
        }

        if (trainingBereitsGewaehlt == false) {
            homeFragmentViewModel.addTraining(training);
            homeFragmentViewModel.addTrainingsName(name);

            layoutManager = new GridLayoutManager(v.getContext(), homeFragmentViewModel.getGewaehlteTrainingsList().size());
            gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
            recyclerViewAdapter = new GewaehlteTrainingsRecyclerViewAdapter(homeFragmentViewModel.getGewaehlteTrainingsList(), homeFragmentViewModel.getGewaehlteTrainingsName());
            gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
            gewaehltesTrainingRecyclerView.setHasFixedSize(true);
        } else {
            Toast.makeText(getView().getContext(), "Training bereits gewählt!", Toast.LENGTH_LONG).show();
        }
    }

    public void workoutsEntfernen() {

        homeFragmentViewModel.getGewaehlteTrainingsName().clear();
        homeFragmentViewModel.getGewaehlteTrainingsList().clear();

        deinGewaehltesTraining.setAlpha(0);
        trennstrichHome.setAlpha(0);
        workoutsEntfernenButton.setAlpha(0);
        workoutsEntfernenButton.setEnabled(false);
        playButton.setAlpha(0f);
        playButton.setEnabled(false);

        recyclerViewAdapter = new GewaehlteTrainingsRecyclerViewAdapter(homeFragmentViewModel.getGewaehlteTrainingsList(), homeFragmentViewModel.getGewaehlteTrainingsName());
        gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
        gewaehltesTrainingRecyclerView.setHasFixedSize(true);
    }

}