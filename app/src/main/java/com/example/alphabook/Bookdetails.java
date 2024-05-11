package com.example.alphabook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.alphabook.fragment.Cart;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bookdetails extends AppCompatActivity implements OnMapReadyCallback {


    ArrayList<Book> arrayList;

    TextView booktitle, bookamount, bookdegree, bookauthor, bookedition, bookuni;
    ImageView i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
    TextView bookcat1, bookcat2, bookcat3, bookcat4, bookcat5, bookcat6, bookcat7, bookcat8, bookcat9, bookcat10;
    TextView location;
    RatingBar ratingBar;
    Toolbar toolbar;
    Button addtocart;

    GoogleMap map;

    boolean b;
    MapView mapView;
    double lat,log;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);


        ProgressDialog progressDialog = new ProgressDialog(Bookdetails.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        toolbar = findViewById(R.id.toolbarbookdetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);


        booktitle = findViewById(R.id.booktitle);
        bookamount = findViewById(R.id.bookamount);
        bookdegree = findViewById(R.id.bookdegree);
        bookauthor = findViewById(R.id.bookauthor);
        bookedition = findViewById(R.id.bookedition);
        bookuni = findViewById(R.id.bookuni);

        ratingBar = findViewById(R.id.bookrating);

        i1 = findViewById(R.id.book1);
        i2 = findViewById(R.id.book2);
        i3 = findViewById(R.id.book3);
        i4 = findViewById(R.id.book4);
        i5 = findViewById(R.id.book5);
        i6 = findViewById(R.id.book6);
        i7 = findViewById(R.id.book7);
        i8 = findViewById(R.id.book8);
        i9 = findViewById(R.id.book9);
        i10 = findViewById(R.id.book10);


        bookcat1 = findViewById(R.id.bookcat1);
        bookcat2 = findViewById(R.id.bookcat2);
        bookcat3 = findViewById(R.id.bookcat3);
        bookcat4 = findViewById(R.id.bookcat4);
        bookcat5 = findViewById(R.id.bookcat5);
        bookcat6 = findViewById(R.id.bookcat6);
        bookcat7 = findViewById(R.id.bookcat7);
        bookcat8 = findViewById(R.id.bookcat8);
        bookcat9 = findViewById(R.id.bookcat9);
        bookcat10 = findViewById(R.id.bookcat10);


        location=findViewById(R.id.addresshow);


        Intent intent = getIntent();
        String book_id = intent.getStringExtra("bookid");

        arrayList = new ArrayList<>();


        APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call = apiInterface.bookdetails(book_id);
        call.enqueue(new Callback<AddBookResult>() {

            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {

                try {

                    arrayList = (ArrayList<Book>) response.body().getBooks();
                    progressDialog.dismiss();


                    String bookid = arrayList.get(0).getBookId();

                    String settitle = arrayList.get(0).getBookTitle();
                    booktitle.setText(settitle);

                    String setamount = arrayList.get(0).getBookAmount();
                    bookamount.setText( setamount);

                    String setdegree = arrayList.get(0).getCategory();
                    bookdegree.setText( "Degree : "+setdegree);

                    String setauthore = arrayList.get(0).getBookAuthor();
                    bookauthor.setText( "Author : "+setauthore);

                    String setedition = arrayList.get(0).getBookEdition();
                    bookedition.setText("Edition : "+ setedition);

                    String setuni = arrayList.get(0).getUniverboard();
                    bookuni.setText("Board : "+ setuni);

                    String bookrating=arrayList.get(0).getBookRating();
                    ratingBar.setRating(Float.parseFloat(bookrating));



                    String cat1=arrayList.get(0).getSubCat1();
                    String cat2=arrayList.get(0).getSubCat2();
                    String cat3=arrayList.get(0).getSubCat3();
                    String cat4=arrayList.get(0).getSubCat4();
                    String cat5= String.valueOf(arrayList.get(0).getSubCat5());
                    String cat6= String.valueOf(arrayList.get(0).getSubCat6());
                    String cat7= String.valueOf(arrayList.get(0).getSubCat7());
                    String cat8= String.valueOf(arrayList.get(0).getSubCat8());
                    String cat9= String.valueOf(arrayList.get(0).getSubCat9());
                    String cat10= String.valueOf(arrayList.get(0).getSubCat10());


                    if (cat1.equals("null")){
                        bookcat1.setVisibility(View.GONE);
                    }else {
                        bookcat1.setText(cat1);}

                    if (cat2.equals("null")){
                        bookcat2.setVisibility(View.GONE);
                    }else {
                        bookcat2.setText(cat2);}

                    if (cat3.equals("null")){
                        bookcat3.setVisibility(View.GONE);
                    }else {
                        bookcat3.setText(cat3);}

                    if (cat4.equals("null")){
                        bookcat4.setVisibility(View.GONE);
                    }else {
                        bookcat4.setText(cat4);}

                    if (cat5.equals("null")){
                        bookcat5.setVisibility(View.GONE);
                    }else {
                        bookcat5.setText(cat5);}

                    if (cat6.equals("null")){
                        bookcat6.setVisibility(View.GONE);
                    }else {
                        bookcat6.setText(cat6);}

                    if (cat7.equals("null")){
                        bookcat7.setVisibility(View.GONE);
                    }else {
                        bookcat7.setText(cat7);}

                    if (cat8.equals("null")){
                        bookcat8.setVisibility(View.GONE);
                    }else {
                        bookcat8.setText(cat8);}

                    if (cat9.equals("null")){
                        bookcat9.setVisibility(View.GONE);
                    }else {
                        bookcat9.setText(cat9);}

                    if (cat10.equals("null")){
                        bookcat10.setVisibility(View.GONE);
                    }else {
                        bookcat10.setText(cat10);}




                    //i1

                    Picasso.get().load(arrayList.get(0).getImg1()).into(i1);
                    Picasso.get().load(arrayList.get(0).getImg2()).into(i2);
                    Picasso.get().load(arrayList.get(0).getImg3()).into(i3);
                    Picasso.get().load(arrayList.get(0).getImg4()).into(i4);
                    Picasso.get().load(arrayList.get(0).getImg5()).into(i5);
                    Picasso.get().load(arrayList.get(0).getImg6()).into(i6);
                    Picasso.get().load(arrayList.get(0).getImg7()).into(i7);
                    Picasso.get().load(arrayList.get(0).getImg8()).into(i8);
                    Picasso.get().load(arrayList.get(0).getImg9()).into(i9);
                    Picasso.get().load(arrayList.get(0).getImg10()).into(i10);




                    lat = Double.parseDouble(arrayList.get(0).getLat());
                    log = Double.parseDouble(arrayList.get(0).getLog());

                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Bookdetails.this, "exection"+e, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Bookdetails.this, "error : " + t, Toast.LENGTH_SHORT).show();
            }
        });


        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String user_id = preferences.getString("userid", null);

        //add to cart
        addtocart = findViewById(R.id.addtocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(Bookdetails.this);
                progressDialog.setMessage("Add to Cart");
                progressDialog.setIcon(R.drawable.ic_baseline_shopping_cart);
                progressDialog.show();


                APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
                Call<AddBookResult> call = apiInterface.Addtocart(book_id, user_id);
                call.enqueue(new Callback<AddBookResult>() {
                    @Override
                    public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                        progressDialog.dismiss();
                        Toast.makeText(Bookdetails.this, "go to cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AddBookResult> call, Throwable t) {
                        Toast.makeText(Bookdetails.this, "failed" + t, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                    }
                });
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        LatLng latLng=new LatLng(lat,log);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);


        try{

            Geocoder geocoder=new Geocoder(Bookdetails.this, Locale.ENGLISH);
            ArrayList<Address> arrayList=(ArrayList<Address>) geocoder.getFromLocation(lat,log,1);
            address=arrayList.get(0).getAddressLine(1);

        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

        map.addMarker(new MarkerOptions().position(latLng).title(address));
        CameraPosition cameraPosition=CameraPosition.builder().target(latLng).zoom(20f).build();
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        location.setText("Address : "+address);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bookdetailstoolmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
        } else if (id == R.id.cart ) {
            Intent intent=new Intent(this, Cart.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    //map


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

}
