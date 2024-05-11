package com.example.alphabook.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphabook.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Notification extends BottomSheetDialogFragment {

    Notification(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notification, container, false);
    }
}