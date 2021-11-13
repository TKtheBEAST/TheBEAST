package com.example.thebeast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CollectionPagerAdapter  extends FragmentStateAdapter {

    private static final int NUM_PAGES = 5;

    public CollectionPagerAdapter(FragmentActivity fa) {

        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        if (position == 2) {
            return new HomeFragment();
        }
        if (position == 1) {
            return new KalenderFragment();
        }
        if (position == 0) {
            return new EinstellungenFragment();
        }
        if (position == 3) {
            return new LiveFragment();
        }
        if (position == 4) {
            return new FreundeFragment();
        }
        return new HomeFragment();
    }


    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

}
