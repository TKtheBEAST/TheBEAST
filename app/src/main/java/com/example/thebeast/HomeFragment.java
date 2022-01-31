package com.example.thebeast;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends Fragment {


    private HomeFragmentViewModel homeFragmentViewModel;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private RecyclerView gewaehltesTrainingRecyclerView;
    private RecyclerView startWorkoutRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private gewaehlteTrainingsRecyclerViewAdapter recyclerViewAdapter;


    //Button initialisieren
    private ImageButton joggenButton,oberkoerperButton,pulldayButton,pushdayButton,beineButton,hiitButton,playButton;

    private Button workoutsEntfernenButton;

    //ImageViews anlegen;
    private int joggenImageView,oberkoerperImageView,pulldayImageView,pushdayImageView,beinImageView,hiitImageView;


    //TextView anlegen
    private TextView deinGewaehltesTraining;
    private TextView trennstrichHome;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //initialisieren ViewModel
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);

        //initialisieren des RecyclerViews
        gewaehltesTrainingRecyclerView=view.findViewById(R.id.gewaehltesTrainingRecyclerview);

        //Button zuweisen
        joggenButton=view.findViewById(R.id.joggenButton);
        oberkoerperButton=view.findViewById(R.id.oberkoerperButton);
        pulldayButton=view.findViewById(R.id.pullDayButton);
        pushdayButton=view.findViewById(R.id.pushDayButton);
        beineButton=view.findViewById(R.id.beinButton);
        hiitButton=view.findViewById(R.id.hiitButton);
        playButton=view.findViewById(R.id.playButton);
        playButton.setAlpha(0f);
        playButton.setEnabled(false);


        workoutsEntfernenButton=view.findViewById(R.id.workoutsEntfernen);

        //Textview initialisieren
        deinGewaehltesTraining = view.findViewById(R.id.deinGewaehltesTraining);
        trennstrichHome = view.findViewById(R.id.trennstrichHome);

        //ImageView zuweisen
        joggenImageView=R.drawable.joggen;
        oberkoerperImageView=R.drawable.oberkoerper;
        pushdayImageView=R.drawable.pushday;
        pulldayImageView=R.drawable.pullday;
        beinImageView=R.drawable.bein;
        hiitImageView=R.drawable.hiit;
        
        //Button onClickListener setzen
        joggenButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), joggenImageView, "Joggen");
            }

        });

        oberkoerperButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                trainingHinzufuegen(view.getRootView(), oberkoerperImageView, "Oberkörper Training");
            }
        });

        pushdayButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                trainingHinzufuegen(view.getRootView(), pushdayImageView, "Push Day");
            }
        });

        pulldayButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                trainingHinzufuegen(view.getRootView(), pulldayImageView, "Pull Day");
            }
        });

        beineButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                trainingHinzufuegen(view.getRootView(), beinImageView, "Beintraining");
            }
        });

        hiitButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                trainingHinzufuegen(view.getRootView(), hiitImageView, "Hiit");
            }
        });


        workoutsEntfernenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                homeFragmentViewModel.getGewaehlteTrainingsName().clear();
                homeFragmentViewModel.getGewaehlteTrainingsList().clear();

                deinGewaehltesTraining.setAlpha(0);
                trennstrichHome.setAlpha(0);
                workoutsEntfernenButton.setAlpha(0);
                workoutsEntfernenButton.setEnabled(false);
                playButton.setAlpha(0f);
                playButton.setEnabled(false);

                recyclerViewAdapter = new gewaehlteTrainingsRecyclerViewAdapter(homeFragmentViewModel.getGewaehlteTrainingsList() , homeFragmentViewModel.getGewaehlteTrainingsName());
                gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                gewaehltesTrainingRecyclerView.setHasFixedSize(true);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startWorkoutDialog();
            }
        });

        return view;
    }

   public void startWorkoutDialog(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View startWorkoutView = getLayoutInflater().inflate(R.layout.start_workout_popup,null);

        startWorkoutRecyclerView = startWorkoutView.findViewById(R.id.startWorkoutRecyclerView);
        layoutManager = new GridLayoutManager(startWorkoutView.getContext(), homeFragmentViewModel.getGewaehlteTrainingsList().size());
        startWorkoutRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new gewaehlteTrainingsRecyclerViewAdapter(homeFragmentViewModel.getGewaehlteTrainingsList(),homeFragmentViewModel.getGewaehlteTrainingsName());
        startWorkoutRecyclerView.setAdapter(recyclerViewAdapter);
        startWorkoutRecyclerView.setHasFixedSize(true);



        dialogBuilder.setView(startWorkoutView);
        dialog = dialogBuilder.create();
        dialog.show();
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
            recyclerViewAdapter = new gewaehlteTrainingsRecyclerViewAdapter(homeFragmentViewModel.getGewaehlteTrainingsList(), homeFragmentViewModel.getGewaehlteTrainingsName());
            gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
            gewaehltesTrainingRecyclerView.setHasFixedSize(true);
        } else {
            Toast.makeText(v.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
        }
    }


}