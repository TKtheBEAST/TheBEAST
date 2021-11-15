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

        switch (position){
            case 2:
                return new HomeFragment();

            case 1:
                return new KalenderFragment();

            case 0:
                return new EinstellungenFragment();

            case 3:
                return new LiveFragment();

            case 4:
                return new FreundeFragment();
        }

        return new HomeFragment();
    }


    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

}
