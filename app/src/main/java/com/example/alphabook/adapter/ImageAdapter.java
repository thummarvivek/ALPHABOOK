package com.example.alphabook.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphabook.R;
import com.example.alphabook.api.books.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter< ImageAdapter.Myclass>   {

    Context context;
    ArrayList<Book> arrayList;
    public ImageAdapter(Context context, ArrayList arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public  Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_bookdetails,parent,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.Myclass holder, int i) {
        Picasso.get().load(arrayList.get(i).getImg1()).into(holder.i1);
        Picasso.get().load(arrayList.get(i).getImg2()).into(holder.i2);
        Picasso.get().load(arrayList.get(i).getImg3()).into(holder.i3);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myclass extends RecyclerView.ViewHolder {
        ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10;

        public Myclass(@NonNull View v) {
            super(v);

                i1 = v.findViewById(R.id.img1);
         //MULTI IMAGE

        }

    }
}