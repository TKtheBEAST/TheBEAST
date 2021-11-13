package com.example.thebeast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Kalender extends AppCompatActivity {

    Button homeButton;
    Button kalenderButton;
    Button einstellungenButton;
    Button liveButton;
    Button freundeButton;
    TextView motivationsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
    }

    public void wechselZuMainAct(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void wechselZuEinstellungenAct(View view){
        Intent intent = new Intent(getApplicationContext(),Einstellungen.class);
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