package com.example.alphabook.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new Home();
            case 1:
                return  new Category();
            case 2:
                return  new Sell();
            case 3:
                return  new Cart();
            case 4:
                return  new Profile();
        }


        return null;
    }



    @Override
    public int getCount() {
        return 5;
    }
}
