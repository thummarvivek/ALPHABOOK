package com.example.alphabook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphabook.R;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartOrderList extends RecyclerView.Adapter<CartOrderList.Myclass > {


        Context context;
        ArrayList<Book> arrayList;

public CartOrderList(Context context , ArrayList arrayList){
        this.arrayList=arrayList;
        this.context=context;
        }


@NonNull
@Override
public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartoderlist,parent,false);
        return new Myclass(v);
        }

@Override
public void onBindViewHolder(@NonNull Myclass holder, int i) {

        Picasso.get().load(arrayList.get(i).getImg1()).into(holder.imageView);
        holder.title.setText(arrayList.get(i).getBookTitle());
        holder.price.setText(arrayList.get(i).getBookAmount());
        holder.publisher.setText(arrayList.get(i).getBookPublisher());
        holder.edition.setText(arrayList.get(i).getBookEdition());
        holder.ratingBar.setNumStars(Integer.parseInt(arrayList.get(i).getBookRating()));
        String  cartID = arrayList.get(i).getCartID();


        }

@Override
public int getItemCount() {
        return arrayList.size();
        }

class Myclass extends  RecyclerView.ViewHolder{

    ImageView imageView;
    TextView title,price,location,category,publisher,edition ;
    RatingBar ratingBar;
    Button button;

    public Myclass(@NonNull View v) {
        super(v);
        title = v.findViewById(R.id.obooktitle);
        price = v.findViewById(R.id.obookprice);
        location = v.findViewById(R.id.olocationtext);
        category = v.findViewById(R.id.obookcategory);
        publisher = v.findViewById(R.id.cbookpub);
        imageView =v.findViewById(R.id.oimg1);
        ratingBar=v.findViewById(R.id.orating);


    }
}



    }
