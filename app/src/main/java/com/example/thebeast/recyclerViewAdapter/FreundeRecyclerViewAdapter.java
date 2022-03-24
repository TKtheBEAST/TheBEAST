package com.example.thebeast.recyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
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
import com.example.thebeast.MainActivity;
import com.example.thebeast.MainActivitySelectionListener;
import com.example.thebeast.R;
import com.example.thebeast.businessobjects.UserModel;

import java.util.ArrayList;
import java.util.List;

public class FreundeRecyclerViewAdapter extends RecyclerView.Adapter<FreundeRecyclerViewAdapter.MyViewHolder>{

    private List<UserModel> freunde = new ArrayList<>();
    private Context context;
    private MainActivitySelectionListener mainActivitySelectionListener;

    public FreundeRecyclerViewAdapter(Context context) {
        this.context = context;
        mainActivitySelectionListener = (MainActivity)context;
    }

    @NonNull
    @Override
    public FreundeRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freunde,parent,false);
        FreundeRecyclerViewAdapter.MyViewHolder myViewHolder = new FreundeRecyclerViewAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FreundeRecyclerViewAdapter.MyViewHolder holder, int position) { ;

        UserModel currentFreund = freunde.get(position);

        holder.beastName.setText(currentFreund.getBeastName());
        holder.beastSpruch.setText(currentFreund.getBeastSpruch());
        String imageUrl = freunde.get(position).getAvatar();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.avatar);


    }

    @Override
    public int getItemCount() {
        if(freunde == null){
            return 0;
        }else {
            return freunde.size();
        }
    }


    public void setFreunde(List<UserModel> freunde){
        this.freunde = freunde;
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
                    mainActivitySelectionListener.onFreundSelected(freunde.get(getAdapterPosition()));
                }
            });
        }
    }
}
