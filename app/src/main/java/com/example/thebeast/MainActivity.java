package com.example.thebeast;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.thebeast.businessobjects.UserModel;
import com.example.thebeast.businessobjects.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity implements MainActivitySelectionListener {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private ProgressBar progressBar;


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

    @Override
    public void onFreundSelected(UserModel freund) {
        selectedFreundDialog(freund);
    }

    public void selectedFreundDialog(UserModel user){

        dialogBuilder = new AlertDialog.Builder(this);
        final View selectedFreundView = getLayoutInflater().inflate(R.layout.freund_popup,null);



        Button freundEntfernenButton = selectedFreundView.findViewById(R.id.freundEntfernenButton);
        Button freundBackButton = selectedFreundView.findViewById(R.id.freundBackButton);

        TextView beastNameFreundTextView = selectedFreundView.findViewById(R.id.beastNameFreundBestaetigen);
        beastNameFreundTextView.setText(user.getBeastName());

        ImageView avatar = selectedFreundView.findViewById(R.id.avatarFrundeIV);
        String imageUrl = user.getAvatar();

        Glide.with(selectedFreundView.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(avatar);

        freundEntfernenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                freundEntfernen(user);
                progressBar.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        freundBackButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setView(selectedFreundView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    public void freundEntfernen(UserModel freund){
        List<UserModel> freundeVonUser = new ArrayList<>();
        freundeVonUser = CurrentUser.getCurrentUser().getFreundeCurrentUser();

    }
}