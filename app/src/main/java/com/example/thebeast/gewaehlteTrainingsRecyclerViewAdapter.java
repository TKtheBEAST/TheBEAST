package com.example.thebeast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class gewaehlteTrainingsRecyclerViewAdapter extends RecyclerView.Adapter<gewaehlteTrainingsRecyclerViewAdapter.MyViewHolder>{

    ArrayList<Integer> gewaehlteTrainingsList;
    ArrayList<String> gewaehlteTrainingsName;

    public gewaehlteTrainingsRecyclerViewAdapter(ArrayList<Integer> bilder, ArrayList<String> namen){
        this.gewaehlteTrainingsList=bilder;
        this.gewaehlteTrainingsName=namen;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gewaehltes_training_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(gewaehlteTrainingsList.get(position));
        holder.textView.setText(gewaehlteTrainingsName.get(position));

    }

    @Override
    public int getItemCount() {
        return gewaehlteTrainingsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gewaehltesTrainingImageView);
            textView = itemView.findViewById(R.id.trainingsName);


        }
    }
}
