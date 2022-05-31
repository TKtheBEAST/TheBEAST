package com.example.thebeast;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements MainActivitySelectionListener {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private ProgressBar progressBar;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("User");
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);


        progressBar = findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.GONE);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2, false);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        uploadFcmToken();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void uploadFcmToken(){

        FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<InstallationTokenResult> task) {
                if (task.isSuccessful()){
                    String token = task.getResult().getToken();
                    Map<String,Object> updates = new HashMap<>();
                    updates.put("token", token);

                    mAuth = FirebaseAuth.getInstance();
                    userRef.document(mAuth.getCurrentUser().getUid()).update(updates)
                            .addOnCompleteListener(new OnCompleteListener<Void>(){

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.i(TAG, "Token wurde in Firestore aktualisiert.");
                                        if(CurrentUser.getCurrentUser() != null){
                                            CurrentUser.getCurrentUser().setToken(token);
                                            Log.i(TAG, "Token konnte dem CurrentUser gesetzt werden.");
                                        }else{
                                            Log.w(TAG, "Token konnte nicht dem CurrentUser gesetzt werden, da Current User null ist.");
                                        }
                                    }else{
                                        Log.w(TAG, "Token konnte nicht in Firestore aktualisiert werden" + task.getException());
                                    }
                                }
                            });

                    if(CurrentUser.getCurrentUser().getFreundeCurrentUser() != null) {
                        for (UserModel freund : CurrentUser.getCurrentUser().getFreundeCurrentUser()) {
                            userRef.document(freund.getBeastId()).collection("FreundeVonUser")
                                    .document(CurrentUser.getCurrentUser().getBeastId()).update(updates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.i(TAG, "Token von User " + CurrentUser.getCurrentUser().getBeastName() + " wurde erfolgreich bei allen Freunden geupdetet.");
                                            } else {
                                                Log.e(TAG, "Token von User " + CurrentUser.getCurrentUser().getBeastName() + " konnte bei den Freunden nicht upgedated werden.");
                                            }
                                        }
                                    });

                        }
                    }else{
                        Log.w(TAG, "Token wurde nicht bei den Freunden geupdated da Freunde of CurrentUser null ist.");
                    }
                }else{
                    Log.w(TAG, "Task getToken() konnte nicht war nicht erfolgreich.");
                }
            }
        });
    }




    public void wechselZuKalenderFragment(View view) {

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1, false);
    }

    public void wechselZuEinstellungenFragment(View view) {

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0, false);
    }

    public void wechselZuLiveFragment(View view) {

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(3, false);
    }

    public void wechselZuFreundeFragment(View view) {

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(4, false);
    }

    public void wechselZuHomeFragment(View view) {

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2, false);
    }


    @Override
    public void onWorkoutSelected(WorkoutModel workout) {
        Log.i(TAG, workout.getStartzeit());
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("longitude", workout.getLongitude());
        intent.putExtra("latitude", workout.getLatitude());
        startActivity(intent);
    }

    @Override
    public void onFreundSelected(UserModel freund) {
        selectedFreundDialog(freund);
    }

    public void selectedFreundDialog(UserModel freund) {

        dialogBuilder = new AlertDialog.Builder(this);
        final View selectedFreundView = getLayoutInflater().inflate(R.layout.freund_popup, null);


        Button freundEntfernenButton = selectedFreundView.findViewById(R.id.freundEntfernenButton);
        Button freundBackButton = selectedFreundView.findViewById(R.id.freundBackButton);

        TextView beastNameFreundTextView = selectedFreundView.findViewById(R.id.beastNameSelectedFreund);
        beastNameFreundTextView.setText(freund.getBeastName());

        ImageView avatar = selectedFreundView.findViewById(R.id.avatarSelectedFreundEntfernenIV);
        String imageUrl = freund.getAvatar();

        Glide.with(selectedFreundView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatar);

        freundEntfernenButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                freundEntfernen(freund);
                progressBar.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        freundBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(selectedFreundView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    public void freundEntfernen(UserModel freund) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        FirebaseMessaging.getInstance().unsubscribeFromTopic(freund.getBeastId())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, freund.getBeastName() +
                                    "Hier ist was schief gelaufen...", Toast.LENGTH_LONG).show();
                            viewPager = findViewById(R.id.pager);
                            pagerAdapter = new CollectionPagerAdapter(MainActivity.this);
                            viewPager.setAdapter(pagerAdapter);
                            viewPager.setCurrentItem(4, false);
                        } else {
                            db.collection("User").document(CurrentUser.getCurrentUser().getBeastId())
                                    .collection("FreundeVonUser").document(freund.getBeastId()).delete()
                                    .addOnCompleteListener(new OnCompleteListener() {

                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if (task.isSuccessful()) {
                                                List<UserModel> freunde = CurrentUser.getCurrentUser().getFreundeCurrentUser();
                                                freunde.remove(freund);
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(MainActivity.this, freund.getBeastName() +
                                                        " ist nicht mehr dein Freund", Toast.LENGTH_LONG).show();

                                            } else {
                                                progressBar.setVisibility(View.GONE);

                                                Toast.makeText(MainActivity.this, freund.getBeastName() +
                                                        " konnte nicht entfernt werden.", Toast.LENGTH_LONG).show();
                                            }
                                            viewPager = findViewById(R.id.pager);
                                            pagerAdapter = new CollectionPagerAdapter(MainActivity.this);
                                            viewPager.setAdapter(pagerAdapter);
                                            viewPager.setCurrentItem(4, false);
                                        }
                                    });
                        }
                    }
                });


    }

    public void onStart() {

        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        //TODO: UI updates.
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            return;
        }
    }
}