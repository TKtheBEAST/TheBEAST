package com.example.thebeast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thebeast.businessobjects.WorkoutModel;
import com.example.thebeast.entitys.Workout;

import java.util.ArrayList;
import java.util.List;

public class LiveRecyclerViewAdapter extends RecyclerView.Adapter<LiveRecyclerViewAdapter.MyViewHolder>{

    private List<WorkoutModel> workouts = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liveobjekt,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { ;
        WorkoutModel currentWorkout = workouts.get(position);
        holder.avatar.setImageResource(currentWorkout.getAvatar());
        holder.beastName.setText(currentWorkout.getBeastName());
        holder.workoutsTextView.setText(currentWorkout.getUebungen());

        /*for(int i=0;i<currentWorkout.getUebungen().size();i++){
            holder.workouts.append(currentWorkout.getUebungen().get(i));
            if(currentWorkout.getUebungen().size() > i+1){
                holder.workouts.append(", ");
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }


    public void setWorkouts(List<WorkoutModel> workouts){
        this.workouts = workouts;
        notifyDataSetChanged();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView beastName;
        TextView workoutsTextView;
        TextView standort;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarimageView);
            beastName = itemView.findViewById(R.id.beastNameTextView);
            workoutsTextView = itemView.findViewById(R.id.workoutTextView);
            standort = itemView.findViewById(R.id.standortTextView);

        }
    }
}
