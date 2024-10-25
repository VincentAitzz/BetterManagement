package com.vincentaitzz.bettermanagement.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(FragmentActivity fa){
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new PageOneFragment();
            case 1:
                return new PageTwoFragment();
            case 2:
                return new PageThreeFragment();
            default:
                return new PageOneFragment();
        }
    }

    @Override
    public int getItemCount(){
        return 3;
    }
}
