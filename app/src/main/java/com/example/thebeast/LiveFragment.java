package com.example.thebeast;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.viewmodel.LiveFragmentViewModel;

import java.util.List;

import static android.content.ContentValues.TAG;


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

        liveFragmentViewModel = new ViewModelProvider(getActivity()).get(LiveFragmentViewModel.class);
        liveFragmentViewModel.getWorkoutListModelData().observe(getViewLifecycleOwner(), new Observer<List<WorkoutModel>>() {
            @Override
            public void onChanged(List<WorkoutModel> workoutModel) {
                adapter.setWorkouts(workoutModel);
                adapter.notifyDataSetChanged();
                Log.i(TAG, "Anzahl workouts " + workoutModel.size());

            }

        });



        return view;
    }

}