package com.example.thebeast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thebeast.entitys.Workout;

import java.util.ArrayList;

public class LiveRecyclerViewAdapter extends RecyclerView.Adapter<LiveRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Workout> workouts = new ArrayList<>();

    public LiveRecyclerViewAdapter(ArrayList<Integer> avatarBilder, ArrayList<String> beastNamen, ArrayList<String> workouts, ArrayList<String> standort){

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
        Workout currentWorkout = workouts.get(position);
        holder.avatar.setImageResource(currentWorkout.getAvatar());

    }

    @Override
    public int getItemCount() {
        return 0;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView beastName;
        TextView workouts;
        TextView standort;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarimageView);
            beastName = itemView.findViewById(R.id.beastNameTextView);
            workouts = itemView.findViewById(R.id.workoutTextView);
            standort = itemView.findViewById(R.id.standortTextView);

        }
    }
}
