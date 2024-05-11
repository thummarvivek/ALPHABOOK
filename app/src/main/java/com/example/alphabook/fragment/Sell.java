package com.example.alphabook.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphabook.R;
import com.example.alphabook.adapter.Viewpageradapter;
import com.google.android.material.tabs.TabLayout;


public class Sell extends Fragment {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    public Sell() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_sell, container, false);

        toolbar = view.findViewById(R.id.toolbarsell);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

//        appCompatActivity.setSupportActionBar(toolbar);
//
//        toolbar.setTitle("Sell Book");

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.pagersell);

        Viewpageradapter v =new Viewpageradapter(appCompatActivity.getSupportFragmentManager());
        v.addFragment(new Sellcol(),"Collage/Shcool");
//        v.addFragment(new Sellschool(),"School");
        v.addFragment(new Sellother(),"Other");
        viewPager.setAdapter(v);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }



}