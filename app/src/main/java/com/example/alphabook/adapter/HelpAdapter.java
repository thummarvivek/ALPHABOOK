package com.example.alphabook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.alphabook.R;

public class HelpAdapter extends BaseAdapter {

    Context context;
    String title[];
    String description[];
    public HelpAdapter(Context context, String title[], String description[]){
        this.context=context;
        this.title=title;
        this.description=description;
    }



    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.helpadapter,null);

        TextView tit =v.findViewById(R.id.helptitle);
        TextView desc=v.findViewById(R.id.helpdesc);

        tit.setText(title[i]);
        desc.setText(description[i]);

        if(tit.equals("")){
            tit.setVisibility(View.GONE);
        }
        if(desc.equals("")){
            desc.setVisibility(View.GONE);
        }

        return  v;
    }
}
