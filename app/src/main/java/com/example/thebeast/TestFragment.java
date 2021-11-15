package com.example.thebeast;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


public class TestFragment extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new CollectionPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2,false);

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

}