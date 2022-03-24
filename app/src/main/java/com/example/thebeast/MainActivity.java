package com.example.thebeast;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thebeast.businessobjects.WorkoutModel;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements LiveWorkoutSelectionListener{

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2,false);



        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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

    public void wechselZuKalenderFragment(View view){

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1,false);
    }

    public void wechselZuEinstellungenFragment(View view){

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0,false);
    }

    public void wechselZuLiveFragment(View view){

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(3,false);
    }

    public void wechselZuFreundeFragment(View view){

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(4,false);
    }

    public void wechselZuHomeFragment(View view){

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2,false);
    }

    @Override
    public void onWorkoutSelected(WorkoutModel workout) {
        Log.i(TAG, workout.getStartzeit());
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("longitude", workout.getLongitude());
        intent.putExtra("latitude", workout.getLatitude());
        startActivity(intent);
    }
}