package com.example.thebeast;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private HorizontalScrollView trainingScrollview;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private RecyclerView gewaehltesTrainingRecyclerView;
    private RecyclerView startWorkoutRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    private ArrayList<String> gewaehlteTrainingsName = new ArrayList();

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

            boolean trainingBereitsGewaehlt=false;
            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernenButton.setAlpha(0.5f);
                if (gewaehlteTrainingsName.size() > 0){
                    for(int i=0; i<gewaehlteTrainingsName.size();i++){
                        if(gewaehlteTrainingsName.get(i).equals("Joggen")){
                            trainingBereitsGewaehlt = true;
                        }
                    }
                }
                if(trainingBereitsGewaehlt==false) {
                    gewaehlteTrainingsList.add(joggenImageView);
                    gewaehlteTrainingsName.add("Joggen");

                    layoutManager = new GridLayoutManager(view.getContext(), gewaehlteTrainingsList.size());
                    gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });

        oberkoerperButton.setOnClickListener(new View.OnClickListener(){

            boolean trainingBereitsGewaehlt=false;

            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernenButton.setAlpha(0.5f);
                if (gewaehlteTrainingsName.size() > 0){
                    for(int i=0; i<gewaehlteTrainingsName.size();i++){
                        if(gewaehlteTrainingsName.get(i).equals("Oberkörper Training")){
                            trainingBereitsGewaehlt = true;
                        }
                    }
                }
                if(trainingBereitsGewaehlt==false) {
                    gewaehlteTrainingsList.add(oberkoerperImageView);
                    gewaehlteTrainingsName.add("Oberkörper Training");

                    layoutManager = new GridLayoutManager(view.getContext(), gewaehlteTrainingsList.size());
                    gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });

        pushdayButton.setOnClickListener(new View.OnClickListener(){

            boolean trainingBereitsGewaehlt=false;

            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernenButton.setAlpha(0.5f);
                if (gewaehlteTrainingsName.size() > 0){
                    for(int i=0; i<gewaehlteTrainingsName.size();i++){
                        if(gewaehlteTrainingsName.get(i).equals("Push Day")){
                            trainingBereitsGewaehlt = true;
                        }
                    }
                }
                if(trainingBereitsGewaehlt==false) {
                    gewaehlteTrainingsList.add(pushdayImageView);
                    gewaehlteTrainingsName.add("Push Day");

                    layoutManager = new GridLayoutManager(view.getContext(), gewaehlteTrainingsList.size());
                    gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });

        pulldayButton.setOnClickListener(new View.OnClickListener(){

            boolean trainingBereitsGewaehlt=false;

            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernenButton.setAlpha(0.5f);
                if (gewaehlteTrainingsName.size() > 0){
                    for(int i=0; i<gewaehlteTrainingsName.size();i++){
                        if(gewaehlteTrainingsName.get(i).equals("Pull Day")){
                            trainingBereitsGewaehlt = true;
                        }
                    }
                }
                if(trainingBereitsGewaehlt==false) {
                    gewaehlteTrainingsList.add(pulldayImageView);
                    gewaehlteTrainingsName.add("Pull Day");

                    layoutManager = new GridLayoutManager(view.getContext(), gewaehlteTrainingsList.size());
                    gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });

        beineButton.setOnClickListener(new View.OnClickListener(){

            boolean trainingBereitsGewaehlt=false;

            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernenButton.setAlpha(0.5f);
                if (gewaehlteTrainingsName.size() > 0){
                    for(int i=0; i<gewaehlteTrainingsName.size();i++){
                        if(gewaehlteTrainingsName.get(i).equals("Beintraining")){
                            trainingBereitsGewaehlt = true;
                        }
                    }
                }
                if(trainingBereitsGewaehlt==false) {
                    gewaehlteTrainingsList.add(beinImageView);
                    gewaehlteTrainingsName.add("Beintraining");

                    layoutManager = new GridLayoutManager(view.getContext(), gewaehlteTrainingsList.size());
                    gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });

        hiitButton.setOnClickListener(new View.OnClickListener(){

            boolean trainingBereitsGewaehlt=false;

            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernenButton.setAlpha(0.5f);
                if (gewaehlteTrainingsName.size() > 0){
                    for(int i=0; i<gewaehlteTrainingsName.size();i++){
                        if(gewaehlteTrainingsName.get(i).equals("Hiit")){
                            trainingBereitsGewaehlt = true;
                        }
                    }
                }
                if(trainingBereitsGewaehlt==false) {
                    gewaehlteTrainingsList.add(hiitImageView);
                    gewaehlteTrainingsName.add("Hiit");

                    layoutManager = new GridLayoutManager(view.getContext(), gewaehlteTrainingsList.size());
                    gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });


        workoutsEntfernenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                gewaehlteTrainingsList.clear();
                gewaehlteTrainingsName.clear();

                deinGewaehltesTraining.setAlpha(0);
                trennstrichHome.setAlpha(0);
                workoutsEntfernenButton.setAlpha(0);

                recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
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
        layoutManager = new GridLayoutManager(startWorkoutView.getContext(), gewaehlteTrainingsList.size());
        startWorkoutRecyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
        startWorkoutRecyclerView.setAdapter(recyclerViewAdapter);
        startWorkoutRecyclerView.setHasFixedSize(true);

        dialogBuilder.setView(startWorkoutView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
}