package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.alphabook.adapter.Homelistadapter;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_show extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView lv;

    //api array
    ArrayList<Book> arrayList;


    //swipe represh
    SwipeRefreshLayout refreshLayout;

    static Location location;
    static FusedLocationProviderClient providerClient;
    boolean b = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show);



        toolbar =findViewById(R.id.toolbarcategoryall);
        AppCompatActivity appCompatActivity=this;

        appCompatActivity.setSupportActionBar(toolbar);


        Intent intent=getIntent();
        String category_name=intent.getStringExtra("categoryname");
        String category_id=intent.getStringExtra("categoryid");


                toolbar.setTitle("Category : "+category_name);


        Refresh();

        //refresh code
        refreshLayout =findViewById(R.id.categoryrefershall);
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


        lv =findViewById(R.id.lvcateory);


        arrayList=new ArrayList<>();
        LinearLayoutManager l=new LinearLayoutManager( this);
        lv.setLayoutManager(l);

    }


    public  void Refresh(){


        Intent intent=getIntent();
        String category_id=intent.getStringExtra("categoryid");

        APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call = apiInterface.categoryshowall(category_id);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                try {
                    arrayList = (ArrayList<Book>) response.body().getBooks();
                    Homelistadapter h=new Homelistadapter(Category_show.this,arrayList);
                    lv.setAdapter(h);
                }
                catch (Exception e){

                    Toast.makeText( Category_show.this , "this app dones not have data"+e, Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {
                Toast.makeText(Category_show.this, "error : "+t, Toast.LENGTH_SHORT).show();
            }
        });



    }
    public  void  setLocation(){

        providerClient= LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder=new Geocoder(Category_show.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }





                                String latitude = String.valueOf(addresses.get(0).getLatitude());
                                String logitude = String.valueOf(addresses.get(0).getLongitude());
                                String city = addresses.get(0).getLocality();
                                String countryname = addresses.get(0).getCountryName();

                            }
                        }
                    });
        }

    }

}