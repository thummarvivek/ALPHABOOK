package com.example.alphabook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphabook.R;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.category.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


//end

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Myclass > {


    Context context;
    ArrayList<Category> arrayList;

    public CategoryAdapter(Context context , ArrayList arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int i) {

        holder.category.setText(arrayList.get(i).getCategory() + ", " + arrayList.get(i).getUniverBoard());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myclass extends  RecyclerView.ViewHolder{


        TextView category ;


        public Myclass(@NonNull View v) {
            super(v);

            category=v.findViewById(android.R.id.text1);


        }
    }



}
