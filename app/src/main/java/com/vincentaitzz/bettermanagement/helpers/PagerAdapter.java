package com.vincentaitzz.bettermanagement.helpers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vincentaitzz.bettermanagement.controllers.PageOne;
import com.vincentaitzz.bettermanagement.controllers.PageTwo;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(FragmentActivity fa){
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new PageOne();
            case 1:
                return new PageTwo();
            default:
                return new PageOne();
        }
    }

    @Override
    public int getItemCount(){
        return 2;
    }
}
