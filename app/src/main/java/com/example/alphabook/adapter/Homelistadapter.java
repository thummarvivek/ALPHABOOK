package com.example.alphabook.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphabook.R;
import com.example.alphabook.Bookdetails;
import com.example.alphabook.api.books.Book;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Homelistadapter extends RecyclerView.Adapter<Homelistadapter.Myclass> {



    Context context;
    ArrayList<Book> arrayList;

    static FusedLocationProviderClient providerClient;
    boolean b = false;

    double latitude ,logitude ;

    public Homelistadapter(Context context, ArrayList arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.homecartlist,parent,false);
        return new Myclass(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int i) {


        Picasso.get().load(arrayList.get(i).getImg1()).into(holder.i1);
        Picasso.get().load(arrayList.get(i).getImg2()).into(holder.i2);
        Picasso.get().load(arrayList.get(i).getImg3()).into(holder.i3);

        holder.s1.setText(arrayList.get(i).getBookTitle());

        holder.s2.setText(arrayList.get(i).getBookAmount());

setLocation();

        double blat = Double.parseDouble(arrayList.get(i).getLat());
        double blog = Double.parseDouble(arrayList.get(i).getLog());

//        holder.s3.setText("Near By : "+ distance(latitude,blat,logitude,blog)  +"km ");

        holder.s3.setText("Near By : "+ latitude+blat+logitude+blog  +"km ");

        holder.s4.setText(arrayList.get(i).getCategory());

        holder.s5.setRating((float) 3.3);

//        holder.s5.setRating(Float.parseFloat(arrayList.get(i).getBookRating()));

        holder.s6.setText(arrayList.get(i).getBookPublisher());
        holder.s7.setText(arrayList.get(i).getBookEdition());

        if (holder.s1.getText().toString().trim().length() ==0){
            holder.s1.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myclass extends RecyclerView.ViewHolder {

        ImageView i1, i2, i3;
        TextView s1, s2, s3, s4,   s6, s7;
        CardView cardView;
        RatingBar s5;

        public Myclass(@NonNull View v) {
            super(v);

            i1 = v.findViewById(R.id.img1);
            i2 = v.findViewById(R.id.img2);
            i3 = v.findViewById(R.id.img3);
            s1 = v.findViewById(R.id.booktitle);
            s2 = v.findViewById(R.id.bookprice);
            s3=v.findViewById(R.id.locationtext);
            s4 = v.findViewById(R.id.bookcategory);
            s5 = v.findViewById(R.id.bookrating);
            s6 = v.findViewById(R.id.bookpub);
            s7 = v.findViewById(R.id.bookedition);

            cardView = v.findViewById(R.id.bookdetails);



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



    private   void  setLocation(){

        providerClient= LocationServices.getFusedLocationProviderClient(context.getApplicationContext());
        if (ContextCompat.checkSelfPermission( context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder=new Geocoder(context.getApplicationContext(), Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                 latitude = addresses.get(0).getLatitude();
                                  logitude =  addresses.get(0).getLongitude();

                            }
                        }
                    });
        }

    }


    private double distance(double lat1, double log1, double lat2, double log2) {
        double theta = log1 - log2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
       double finaldict = Double.parseDouble(String.format("%.2f",dist));
        return (finaldict);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}