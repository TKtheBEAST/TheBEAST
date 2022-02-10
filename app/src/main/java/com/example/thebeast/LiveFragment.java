package com.example.thebeast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebeast.R;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.viewmodel.HomeFragmentViewModel;
import com.example.thebeast.viewmodel.LiveFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class LiveFragment extends Fragment {

    private LiveFragmentViewModel liveFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_live, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.liveWorkoutsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        LiveRecyclerViewAdapter adapter = new LiveRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        liveFragmentViewModel = new ViewModelProvider(this).get(LiveFragmentViewModel.class);
        liveFragmentViewModel.getAllWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                adapter.setWorkouts(workouts);
            }
        });


        return view;
    }

}