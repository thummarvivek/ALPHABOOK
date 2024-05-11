package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order extends AppCompatActivity  {

  Toolbar toolbar;
  Button paybtn;
  TextView oderamount;
  ArrayList<Book> arrayList;



    ImageView imageView;
    TextView title,price, category,location;
    RatingBar ratingBar;

    TextView itemamount,dileveryamount,totalamount;


    String cartid,bookid,amout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = findViewById(R.id.toolbarordersumbary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList=new ArrayList<>();





        SharedPreferences pref = getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String name =pref.getString("Name",null);
        String address=pref.getString("address",null);
        String phone=pref.getString("phoneno",null);
        String userid =pref.getString("userid",null);


        Intent intent=getIntent();
        cartid=intent.getStringExtra("cartid");

        TextView taddress= findViewById(R.id.oaddresss);
        taddress.setText(address);
        TextView tname=findViewById(R.id.oname);
        tname.setText(name);
        TextView tphone=findViewById(R.id.ophone);
        tphone.setText(phone);


        //books

        title = findViewById(R.id.obooktitle);
        price = findViewById(R.id.obookprice);
        location = findViewById(R.id.olocationtext);
        category = findViewById(R.id.obookcategory);
        imageView =findViewById(R.id.oimg1);
        ratingBar=findViewById(R.id.orating);


        //amount
        itemamount = findViewById(R.id.priceitem);
        dileveryamount = findViewById(R.id.dilveryamount);
        totalamount =findViewById(R.id.totalamount);
        oderamount =findViewById(R.id.amountoforder);



        APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call = apiInterface.showordercart(userid,cartid);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {

                try{
                    arrayList =(ArrayList<Book>) response.body().getBooks();

                    Picasso.get().load(arrayList.get(0).getImg1()).into(imageView);
                    title.setText(arrayList.get(0).getBookTitle());
                    price.setText(arrayList.get(0).getBookAmount());
                    category.setText(arrayList.get(0).getCategory());
                    location.setText(arrayList.get(0).getAddress());
//                    ratingBar.setRating(Integer.parseInt(arrayList.get(0).getBookRating()));



                    amout=  arrayList.get(0).getBookAmount();
                    bookid=  arrayList.get(0).getBookId();



                    itemamount.setText(amout);
                    dileveryamount.setText("0");
                    totalamount.setText(amout);
                    oderamount.setText(amout);





                }catch (Exception c){
                    Toast.makeText(Order.this, ""+c, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {

            }
        });


        Toast.makeText(Order.this, "amount =>"+amout + "book id =>"+bookid, Toast.LENGTH_SHORT).show();



        paybtn=findViewById(R.id.orderpayment);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(Order.this,OrderPayment.class);

                 intent.putExtra("orderamount",amout);
                 intent.putExtra("cartid",cartid);
                 intent.putExtra("bookid",bookid);
                 startActivity(intent);
            }
        });


    }











}