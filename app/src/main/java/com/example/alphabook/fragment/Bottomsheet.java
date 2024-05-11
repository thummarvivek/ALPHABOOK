package com.example.alphabook.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alphabook.About_us;
import com.example.alphabook.ContactUS;
import com.example.alphabook.HelpCenter;
import com.example.alphabook.Login;
import com.example.alphabook.R;
import com.example.alphabook.feedback;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Bottomsheet extends BottomSheetDialogFragment {

    public Bottomsheet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bottomsheet, container, false);

        TextView t1=view.findViewById(R.id.b1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), feedback.class);
                startActivity(intent);
            }
        });



        TextView t2=view.findViewById(R.id.b2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), About_us.class);
                startActivity(intent);
            }
        });



        TextView t3=view.findViewById(R.id.b3);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ContactUS.class);
                startActivity(intent);







            }
        });



        TextView t4=view.findViewById(R.id.b4);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),  HelpCenter.class);
                startActivity(intent);







            }
        });


        TextView t5=view.findViewById(R.id.b5);
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Login.class);
                startActivity(intent);

                SharedPreferences preferences= getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=preferences.edit();
                ed.clear();
                ed.apply();





            }
        });





        return view; }

}