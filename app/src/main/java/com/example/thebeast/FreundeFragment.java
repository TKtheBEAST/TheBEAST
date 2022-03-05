package com.example.thebeast;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.thebeast.R;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.recyclerViewAdapter.FreundeRecyclerViewAdapter;
import com.example.thebeast.recyclerViewAdapter.LiveRecyclerViewAdapter;
import com.example.thebeast.viewmodel.FreundeFragmentViewModel;
import com.example.thebeast.viewmodel.LiveFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class FreundeFragment extends Fragment {


    private FreundeFragmentViewModel freundeFragmentViewModel;
    private RecyclerView recyclerView;
    private FreundeRecyclerViewAdapter adapter;

    private List<UserModel> freunde;

    ImageButton addFreundButton;

    public FreundeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_freunde, container, false);
        freunde = new ArrayList<UserModel>();
        freunde = CurrentUser.getCurrentUser().getFreundeCurrentUser();

        addFreundButton = view.findViewById(R.id.addFreundButton);

        addFreundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),AddFreundActivity.class));

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.freundeRecyclerView);
        adapter = new FreundeRecyclerViewAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setFreunde(freunde);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }

    public void onStart() {
        super.onStart();

    }
}