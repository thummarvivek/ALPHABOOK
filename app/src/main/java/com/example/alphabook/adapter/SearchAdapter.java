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

import com.example.alphabook.Bookdetails;
import com.example.alphabook.R;
import com.example.alphabook.api.books.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Myclass> {

    Context context;
    ArrayList<Book> arrayList;

    public SearchAdapter(Context context, ArrayList<Book> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.search_adapter,parent,false);
        return new Myclass(v);
    }

   public void Filterlist(ArrayList<Book> filterlist){
        arrayList=filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int position) {

        Picasso.get().load(arrayList.get(position).getImg1()).into(holder.img);
        holder.textView1.setText(arrayList.get(position).getBookTitle());
        holder.textView2.setText(arrayList.get(position).getCategory());
        holder.textView3.setText(arrayList.get(position).getBookAmount());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class Myclass extends RecyclerView.ViewHolder{

        ImageView img;
        TextView textView1,textView2,textView3;
        CardView cardView;

        public Myclass(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imageView5);
            textView1 = v.findViewById(R.id.textView5);
            textView2 = v.findViewById(R.id.textView6);
            textView3 = v.findViewById(R.id.textView7);
            cardView = v.findViewById(R.id.cardsearch);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Bookdetails.class);
                    intent.putExtra("bookid" , arrayList.get(getAdapterPosition()).getBookId());
                    context.startActivity(intent);

                }
            });


        }
    }

}
