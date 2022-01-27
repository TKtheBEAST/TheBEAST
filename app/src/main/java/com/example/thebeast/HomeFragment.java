package com.example.thebeast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

import com.example.thebeast.R;

import java.util.List;


public class HomeFragment extends Fragment {


    HorizontalScrollView trainingScrollview;

    private RecyclerView gewaehltesTrainingRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Integer> gewaehlteTrainingsList;

    //Button initialisieren
    ImageButton joggen;
    ImageButton oberkoerper;
    ImageButton pullday;
    ImageButton pushday;
    ImageButton beine;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gewaehltesTrainingRecyclerView=view.findViewById(R.id.gewaehltesTrainingRecyclerview);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        gewaehltesTrainingRecyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(gewaehlteTrainingsList);

        gewaehltesTrainingRecyclerView.setAdapter(recyclerViewAdapter);
        gewaehltesTrainingRecyclerView.setHasFixedSize(true);



        return view;
    }
}