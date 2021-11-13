package com.example.thebeast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Einstellungen extends AppCompatActivity {

    Button homeButton;
    Button kalenderButton;
    Button einstellungenButton;
    Button liveButton;
    Button freundeButton;
    TextView motivationsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
    }

    public void wechselZuMainAct(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void wechselZuKalenderAct(View view){
        Intent intent = new Intent(getApplicationContext(),Kalender.class);
        startActivity(intent);
    }

    public void wechselZuLiveAct(View view){
        Intent intent = new Intent(getApplicationContext(),Live.class);
        startActivity(intent);
    }

    public void wechselZuFreundeAct(View view){
        Intent intent = new Intent(getApplicationContext(),Freunde.class);
        startActivity(intent);
    }
}