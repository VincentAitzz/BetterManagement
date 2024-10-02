package com.vincentaitzz.bettermanagement.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class PageAdapter extends FragmentStateAdapter {

    private final int[] layouts;

    public PageAdapter(FragmentActivity fa, int[] layouts){
        super(fa);
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        return Slider.newInstance(layouts[position]);
    }

    @Override
    public int getItemCount(){
        return layouts.length;
    }
}
