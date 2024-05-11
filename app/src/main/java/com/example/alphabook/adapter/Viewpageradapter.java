package com.example.alphabook.adapter;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class Viewpageradapter extends FragmentStatePagerAdapter {


    ArrayList<Fragment>arrayList=new ArrayList<>();
    ArrayList<String>arrayList1=new ArrayList<>();
   
    public Viewpageradapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }



    public void addFragment(Fragment fragment, String name){
        arrayList.add(fragment);
        arrayList1.add(name);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {


        return arrayList1.get(position);
    }
}
