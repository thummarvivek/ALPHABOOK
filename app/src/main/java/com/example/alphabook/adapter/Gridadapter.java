package com.example.alphabook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphabook.Category_show;
import com.example.alphabook.R;
import com.example.alphabook.api.books.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Gridadapter extends RecyclerView.Adapter<Gridadapter.Myclass>   {



    Context context;
    ArrayList<Book> arrayList;

    public Gridadapter(Context context, ArrayList<Book> arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.gridadapter,parent,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int i) {

        Picasso.get().load(arrayList.get(i).getImg1()).placeholder(R.drawable.offline).into(holder.i1);
        holder.s1.setText(arrayList.get(i).getCategory());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

   public class Myclass extends RecyclerView.ViewHolder {

        ImageView i1;
        TextView s1;
        CardView cardView;

        public Myclass(@NonNull View v) {
            super(v);

            i1 = v.findViewById(R.id.imggrid);
            s1 = v.findViewById(R.id.textgrid);



            cardView = v.findViewById(R.id.categoryallgrid);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Category_show.class);
                    intent.putExtra("categoryid" , arrayList.get(getAdapterPosition()).getCategoryId());
                    intent.putExtra("categoryname" , arrayList.get(getAdapterPosition()).getCategory());
                    context.startActivity(intent);


                }
            });

        }
    }
}