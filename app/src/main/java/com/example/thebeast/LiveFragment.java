package com.example.thebeast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebeast.R;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.viewmodel.HomeFragmentViewModel;
import com.example.thebeast.viewmodel.LiveFragmentViewModel;

import java.util.List;


public class LiveFragment extends Fragment {

    private LiveFragmentViewModel liveFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_live, container, false);

        liveFragmentViewModel = new ViewModelProvider(this).get(LiveFragmentViewModel.class);

        liveFragmentViewModel.getAllWorkouts().observe(getViewLifecycleOwner(), new Observer<List<Workout>>() {
            @Override
            public void onChanged(List<Workout> workouts) {
                //update RecyclerView
            }
        });


        return view;
    }

}