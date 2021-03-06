package com.example.thebeast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.recyclerViewAdapter.LiveRecyclerViewAdapter;
import com.example.thebeast.viewmodel.LiveFragmentViewModel;

import java.util.List;


public class LiveFragment extends Fragment {

    private LiveFragmentViewModel liveFragmentViewModel;
    private LiveFragmentViewModel liveFragmentViewModelRefresh;
    private RecyclerView recyclerView;
    private LiveRecyclerViewAdapter adapter;
    private TextView niemandTrainiertTV;

    private SwipeRefreshLayout swipeRefreshLayout;


    public LiveFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_live, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        niemandTrainiertTV = view.findViewById(R.id.niemandTrainiertTV);
        recyclerView = view.findViewById(R.id.liveWorkoutsRecyclerView);
        adapter = new LiveRecyclerViewAdapter(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() == 0){
            niemandTrainiertTV.setVisibility(View.VISIBLE);
        }else{
            niemandTrainiertTV.setVisibility(View.GONE);
        }

        swipeRefreshLayout = view.findViewById(R.id.liveSwipeToRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                liveFragmentViewModelRefresh = new ViewModelProvider(getActivity()).get(LiveFragmentViewModel.class);
                liveFragmentViewModelRefresh.refreshWorkouts().observe(getViewLifecycleOwner(), new Observer<List<WorkoutModel>>() {
                    @Override
                    public void onChanged(List<WorkoutModel> workoutModels) {
                        adapter.setWorkouts(workoutModels);
                        adapter.notifyDataSetChanged();
                        if(adapter.getItemCount() == 0){
                            niemandTrainiertTV.setVisibility(View.VISIBLE);
                        }else{
                            niemandTrainiertTV.setVisibility(View.GONE);
                        }
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        liveFragmentViewModel = new ViewModelProvider(getActivity()).get(LiveFragmentViewModel.class);
        liveFragmentViewModel.getWorkouts().observe(getViewLifecycleOwner(), new Observer<List<WorkoutModel>>() {
            @Override
            public void onChanged(List<WorkoutModel> workoutModels) {
                adapter.setWorkouts(workoutModels);
                adapter.notifyDataSetChanged();
                if(adapter.getItemCount() == 0){
                    niemandTrainiertTV.setVisibility(View.VISIBLE);
                }else{
                    niemandTrainiertTV.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();


    }

}