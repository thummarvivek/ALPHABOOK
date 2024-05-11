package com.example.alphabook.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.alphabook.Category_show;
import com.example.alphabook.Order;
import com.example.alphabook.R;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.example.alphabook.fragment.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cardadapter  extends RecyclerView.Adapter< Cardadapter.Myclass > {


    Context context;
    ArrayList<Book> arrayList;

    public Cardadapter(Context context ,ArrayList arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardadapter,parent,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int i) {

        Picasso.get().load(arrayList.get(i).getImg1()).into(holder.imageView);
        holder.title.setText(arrayList.get(i).getBookTitle());
        holder.price.setText(arrayList.get(i).getBookAmount());
      //  holder.location.setText(arrayList.get(i).getbo());
        holder.publisher.setText(arrayList.get(i).getBookPublisher());
        holder.edition.setText(arrayList.get(i).getBookEdition());
        holder.category.setText(arrayList.get(i).getCategory());
//        holder.ratingBar.setNumStars(Integer.parseInt(arrayList.get(i).getBookRating()));
        String  cartID = arrayList.get(i).getCartID();
     //   holder.title.setText(arrayList.get(i).getBookTitle());


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
        Button orderbtn;

        public Myclass(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.cbooktitle);
            price = v.findViewById(R.id.cbookprice);
            location = v.findViewById(R.id.clocationtext);
            category = v.findViewById(R.id.cbookcategory);
            publisher = v.findViewById(R.id.cbookpub);
            edition = v.findViewById(R.id.cbookedition);
            imageView =v.findViewById(R.id.cimg1);
            ratingBar=v.findViewById(R.id.crating);



            SharedPreferences pref= context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
            String userid =pref.getString("userid",null);


            button = v.findViewById(R.id.removecart);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
                    Call<AddBookResult> call=apiInterface.deletecart(userid,arrayList.get(getAdapterPosition()).getCartID(),arrayList.get(getAdapterPosition()).getBookId());
                    call.enqueue(new Callback<AddBookResult>() {
                        @Override
                        public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                            Toast.makeText(context, "Cart Removed" , Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<AddBookResult> call, Throwable t) {

                        }
                    });
                }
            });

            orderbtn =v.findViewById(R.id.buycard);
            orderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Order.class);
                    intent.putExtra("cartid" , arrayList.get(getAdapterPosition()).getCartID());
                    context.startActivity(intent);

                }
            });

        }
    }



    }
