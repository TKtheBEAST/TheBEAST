package com.example.thebeast;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    HorizontalScrollView trainingScrollview;

    private RecyclerView gewaehltesTrainingRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Integer> gewaehlteTrainingsList = new ArrayList();
    ArrayList<String> gewaehlteTrainingsName = new ArrayList();

    //Button initialisieren
    ImageButton joggenButton;
    ImageButton oberkoerperButton;
    ImageButton pulldayButton;
    ImageButton pushdayButton;
    ImageButton beineButton;

    Button workoutsEntfernen;

    //ImageViews anlegen;
    int joggenImageView;
    int oberkoerperImageView;
    int pulldayImageView;
    int pushdayImageView;
    int beinImageView;

    //TextView anlegen
    TextView deinGewaehltesTraining;
    TextView trennstrichHome;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //initialisieren des RecyclerViews
        gewaehltesTrainingRecyclerView=view.findViewById(R.id.gewaehltesTrainingRecyclerview);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);

        //Button zuweisen
        joggenButton=view.findViewById(R.id.joggenButton);
        oberkoerperButton=view.findViewById(R.id.oberkoerperButton);
        pulldayButton=view.findViewById(R.id.pullDayButton);
        pushdayButton=view.findViewById(R.id.pushDayButton);
        beineButton=view.findViewById(R.id.beinButton);

        workoutsEntfernen=view.findViewById(R.id.workoutsEntfernen);

        //Textview initialisieren
        deinGewaehltesTraining = view.findViewById(R.id.deinGewaehltesTraining);
        trennstrichHome = view.findViewById(R.id.trennstrichHome);

        //ImageView zuweisen
        joggenImageView=R.drawable.joggen;
        oberkoerperImageView=R.drawable.oberkoerper;
        pushdayImageView=R.drawable.pushday;
        pulldayImageView=R.drawable.pullday;
        beinImageView=R.drawable.bein;
        
        //Button onClickListener setzen
        joggenButton.setOnClickListener(new View.OnClickListener(){

            boolean trainingBereitsGewaehlt=false;
            public void onClick(View v){
                deinGewaehltesTraining.setAlpha(1);
                trennstrichHome.setAlpha(1);
                workoutsEntfernen.setAlpha(1);
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
                workoutsEntfernen.setAlpha(1);
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
                workoutsEntfernen.setAlpha(1);
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
                workoutsEntfernen.setAlpha(1);
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
                workoutsEntfernen.setAlpha(1);
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

                    recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList,gewaehlteTrainingsName);
                    gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
                    gewaehltesTrainingRecyclerView.setHasFixedSize(true);
                }else{
                    Toast.makeText(view.getContext(), "Training bereits gewählt", Toast.LENGTH_LONG).show();
                }

            }
        });


        return view;
    }
}