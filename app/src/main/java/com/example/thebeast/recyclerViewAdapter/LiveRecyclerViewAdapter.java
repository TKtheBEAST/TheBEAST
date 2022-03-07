package com.example.thebeast.recyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thebeast.R;
import com.example.thebeast.businessobjects.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

public class LiveRecyclerViewAdapter extends RecyclerView.Adapter<LiveRecyclerViewAdapter.MyViewHolder>{

    private List<WorkoutModel> workoutsList = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liveobjekt,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WorkoutModel currentWorkout = workoutsList.get(position);
        holder.beastName.setText(currentWorkout.getBeastName());
        holder.workoutsTextView.setText(currentWorkout.getUebungen());
        String imageUrl = workoutsList.get(position).getAvatar();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.avatar);


    }

    @Override
    public int getItemCount() {
        if(workoutsList == null){
            return 0;
        }else {
            return workoutsList.size();
        }
    }


    public void setWorkouts(List<WorkoutModel> workouts){
        this.workoutsList = workouts;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView beastName;
        private TextView workoutsTextView;
        private TextView standort;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarimageViewLive);
            beastName = itemView.findViewById(R.id.beastNameTextView);
            workoutsTextView = itemView.findViewById(R.id.workoutTextView);
            standort = itemView.findViewById(R.id.standortTextView);

        }
    }
}
