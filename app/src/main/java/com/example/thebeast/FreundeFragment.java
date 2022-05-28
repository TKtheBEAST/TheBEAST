package com.example.thebeast;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.recyclerViewAdapter.FreundeRecyclerViewAdapter;
import com.example.thebeast.viewmodel.FreundeFragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class FreundeFragment extends Fragment {

    private static final String TAG = "FreundeFragment";
    private FreundeFragmentViewModel freundeFragmentViewModel;
    private RecyclerView recyclerView;
    private FreundeRecyclerViewAdapter adapter;
    private TextView erstesBeastHinzufuegenTV;

    private List<UserModel> freunde;

    ImageButton addFreundButton;

    public FreundeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_freunde, container, false);

        freunde = new ArrayList<UserModel>();

        erstesBeastHinzufuegenTV = view.findViewById(R.id.erstesBeastHinzufuegenTV);

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
        adapter = new FreundeRecyclerViewAdapter(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if(CurrentUser.getCurrentUser() != null){
            if(CurrentUser.getCurrentUser().getFreundeCurrentUser() != null){
                freunde = CurrentUser.getCurrentUser().getFreundeCurrentUser();
                if(freunde.size() == 0){
                    erstesBeastHinzufuegenTV.setVisibility(View.VISIBLE);
                }else{
                    erstesBeastHinzufuegenTV.setVisibility(View.GONE);
                    adapter.setFreunde(freunde);
                    adapter.notifyDataSetChanged();
                }
            }
        }else{
            Log.w(TAG, "Current User ist nicht gesetzt. Freunde daher nicht zu Verfügung");
        }



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }

    public void onStart() {
        super.onStart();

    }

    public void onResume() {
        super.onResume();

        adapter = new FreundeRecyclerViewAdapter(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if(CurrentUser.getCurrentUser() != null){
            if(CurrentUser.getCurrentUser().getFreundeCurrentUser() != null){
                freunde = CurrentUser.getCurrentUser().getFreundeCurrentUser();
                if(freunde.size() == 0){
                    erstesBeastHinzufuegenTV.setVisibility(View.VISIBLE);
                }else{
                    erstesBeastHinzufuegenTV.setVisibility(View.GONE);
                    adapter.setFreunde(freunde);
                    adapter.notifyDataSetChanged();
                }
            }
        }else{
            Log.w(TAG, "Current User ist nicht gesetzt. Freunde daher nicht zu Verfügung");
        }
    }



}