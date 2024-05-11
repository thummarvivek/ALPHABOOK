package com.example.alphabook.fragment;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.Order;
import com.example.alphabook.R;
import com.example.alphabook.adapter.Cardadapter;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Cart extends Fragment {

    Toolbar toolbar;

    SwipeRefreshLayout refreshLayout;
    ArrayList<Book> arrayList;
    RecyclerView lv;

    public Cart(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.fragment_cart, container, false);


        toolbar =view.findViewById(R.id.toolbarcart );
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("Cart");



        SharedPreferences pref = getActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String name =pref.getString("Name",null);
        String address=pref.getString("address",null);
        String phone=pref.getString("phoneno",null);

        TextView taddress= view.findViewById(R.id.caddress);
        taddress.setText(address);






        //refrsch
        refreshLayout =view.findViewById(R.id.cartrefersh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();


                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Stop animation (This will be after 3 seconds)
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000); // Delay in millis


            }
        });
        refreshLayout .setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );



        lv =view.findViewById(R.id.cart);

        arrayList=new ArrayList<>();
        LinearLayoutManager l=new LinearLayoutManager( getContext());
        lv.setLayoutManager(l);

        Refresh();


        return view;
    }

    public void Refresh() {

        SharedPreferences pref= getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userid =pref.getString("userid",null);

        APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call = apiInterface.showcart(userid);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {

                try{
                arrayList =(ArrayList<Book>) response.body().getBooks();
                Cardadapter c =new  Cardadapter(getContext(),arrayList);
                lv.setAdapter(c);
            }catch (Exception c){
                    Toast.makeText(getContext(), ""+c, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {

            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolmenucart, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if(id==R.id.order){}



        return super.onOptionsItemSelected(item);
    }
}