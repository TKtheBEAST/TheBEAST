package com.example.thebeast.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thebeast.AddFreundActivity;
import com.example.thebeast.AddFreundSelectionListener;
import com.example.thebeast.R;
import com.example.thebeast.businessobjects.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AddFreundRecyclerviewAdapter extends RecyclerView.Adapter<AddFreundRecyclerviewAdapter.MyViewHolder>{

    private List<UserModel> user = new ArrayList<>();
    private Context context;
    private AddFreundSelectionListener addFreundSelectionListener;

    public AddFreundRecyclerviewAdapter(Context context) {
        this.context = context;
        addFreundSelectionListener = (AddFreundActivity)context;
    }

    @NonNull
    @Override
    public AddFreundRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freunde, parent, false);
        AddFreundRecyclerviewAdapter.MyViewHolder myViewHolder = new AddFreundRecyclerviewAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddFreundRecyclerviewAdapter.MyViewHolder holder, int position) {

        UserModel currentFreund = user.get(position);

        holder.beastName.setText(currentFreund.getBeastName());
        holder.beastSpruch.setText(currentFreund.getBeastSpruch());
        String imageUrl = user.get(position).getAvatar();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        if (user == null) {
            return 0;
        } else {
            return user.size();
        }
    }


    public void setFreunde(List<UserModel> user){
        this.user = user;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView beastName;
        private TextView beastSpruch;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarFrundeIV);
            beastName = itemView.findViewById(R.id.beastNameFreundeTV);
            beastSpruch = itemView.findViewById(R.id.beastSpruchFreundeTV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFreundSelectionListener.onFreundSelected(user.get(getAdapterPosition()));
                }
            });
        }

    }
}