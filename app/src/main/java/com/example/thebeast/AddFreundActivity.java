package com.example.thebeast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.thebeast.recyclerViewAdapter.FreundeRecyclerViewAdapter;

public class AddFreundActivity extends AppCompatActivity {

    private ImageView xImageView;
    private SearchView searchView;

    private RecyclerView addFreundRecyclerView;
    private FreundeRecyclerViewAdapter freundeRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_freund2);

        xImageView = findViewById(R.id.xImageView);

        xImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}