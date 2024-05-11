package com.example.alphabook.fragment;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import com.example.alphabook.Homeprefconfig;
import com.example.alphabook.R;
import com.example.alphabook.adapter.Homelistadapter;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.example.alphabook.Search;
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


public class Home extends Fragment {

    Toolbar toolbar;
    RecyclerView lv;

    //api array
    ArrayList<Book> arrayList;


    //swipe represh
    SwipeRefreshLayout refreshLayout;

    static Location location;
    static FusedLocationProviderClient providerClient;
    boolean b = false;


    public Home(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.fragment_home, container, false);





        toolbar =view.findViewById(R.id.toolbarhome);
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();

        appCompatActivity.setSupportActionBar(toolbar);



        setLocation();
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocation();
            }
        });

        Refresh();


        ArrayList<Book> tasklist = Homeprefconfig.readlistpref(getContext());
        if (tasklist==null){
            tasklist=new ArrayList<>();
        }


        //refresh code
        refreshLayout =view.findViewById(R.id.homerefersh);
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



        lv =view.findViewById(R.id.lv);


//api array
        arrayList=new ArrayList<>();
        LinearLayoutManager l=new LinearLayoutManager( getContext());
        lv.setLayoutManager(l);
     //   homelistadapter h=new homelistadapter(getContext(),img1,img2,img3,title,price,location,description );
      //  lv.setAdapter(h);



        //map




        return view;
    }


    public  void Refresh(){

        SharedPreferences pref= requireActivity().getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String userid =pref.getString("userid",null);

        APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call = apiInterface.bookshow(userid);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                try {
                    arrayList = (ArrayList<Book>) response.body().getBooks();
                    Homelistadapter h=new Homelistadapter(getContext(),arrayList);
                    lv.setAdapter(h);
                }
                catch (Exception e){

                    Toast.makeText(getContext() , "this app dones not have data"+e, Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {
                Toast.makeText(getContext(), "error : "+t, Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolmenu, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent intent = new Intent(getActivity(), Search.class);
            startActivity(intent);
        }
//        else if (id == R.id.notification) {
//
//            Notification b = new Notification();
//            b.show(getActivity().getSupportFragmentManager(), "tag");
//        }
        return super.onOptionsItemSelected(item);
    }

    public  void  setLocation(){

        providerClient= LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder=new Geocoder(getContext(),Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }



                                toolbar.setTitle(addresses.get(0).getLocality());
                                toolbar.setLogo(R.drawable.location);
                                toolbar.setTitleMarginStart(10);

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