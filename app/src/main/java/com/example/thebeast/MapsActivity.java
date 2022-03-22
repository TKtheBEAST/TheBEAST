package com.example.thebeast;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 48.89731);
        longitude = intent.getDoubleExtra("longitute", 9.19161);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker where the workout is and move the camera
        LatLng workoutStandort = new LatLng(latitude, longitude);

        if(latitude == 48.89731 && longitude == 9.19161){
            // TODO: TextView oder Feld einfügen mit Info das das Workout nicht gefunden wurden. So wie hier ist doof
            mMap.addMarker(new MarkerOptions().position(workoutStandort).title("Workout wurde nicht gefunden"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(workoutStandort));
            return;
        }
        mMap.addMarker(new MarkerOptions().position(workoutStandort).title("Hier findet das Workout statt"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(workoutStandort));
    }
}