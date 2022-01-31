package com.example.thebeast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LiveRecyclerViewAdapter extends RecyclerView.Adapter<LiveRecyclerViewAdapter.MyViewHolder>{

    ArrayList<Integer> avatarBilder;
    ArrayList<String> beastNamen;
    ArrayList<String> workouts;
    ArrayList<String> standort;

    public LiveRecyclerViewAdapter(ArrayList<Integer> avatarBilder, ArrayList<String> beastNamen, ArrayList<String> workouts, ArrayList<String> standort){
        this.avatarBilder = avatarBilder;
        this.beastNamen = beastNamen;
        this.workouts = workouts;
        this.standort = standort;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liveobjekt,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { ;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
