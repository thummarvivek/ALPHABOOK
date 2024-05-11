package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.Register.Registration;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.example.alphabook.fragment.Home;
import com.example.alphabook.fragment.Notification;
import com.example.alphabook.fragment.Profile;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Editprofile extends AppCompatActivity {

    Toolbar toolbar;
    EditText ename ,eadd,epincode,elang,esolink;
    TextView editbob;
    ImageButton  gpscall;
    Spinner egen;
    Calendar c=Calendar.getInstance();
    ArrayList<Registration>arrayList;


    //gps set
    String Address,Pincode,LAT,LOG;
    static  FusedLocationProviderClient providerClient;
    boolean b=true;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        toolbar = findViewById(R.id.toolbareditprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrayList = new ArrayList<>();
//data update
        setLocation();

        SharedPreferences pref= getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String Name =pref.getString("Name",null);
        String userid =pref.getString("userid",null);
        String username =pref.getString("username",null);
        String useraddress =pref.getString("address",null);
        String userphone =pref.getString("phoneno",null);
        String usermail =pref.getString("email",null);
        String userpincode =pref.getString("pincode",null);
        String userdob =pref.getString("dob",null);
        String usergender =pref.getString("gender",null);
        String userdp =pref.getString("dp",null);
        String userlocation =pref.getString("location",null);
        String userlink =pref.getString("link",null);
        String userlanguage =pref.getString("language",null);
        String password=pref.getString("password",null);







        ename= findViewById(R.id.editname);
        ename.setText(Name +" ");


        eadd= findViewById(R.id.editaddress);
        eadd.setText(useraddress+" ");

        epincode= findViewById(R.id.editpincode);
        epincode.setText(userpincode+"");

        gpscall = findViewById(R.id.egpscall);
        gpscall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    setLocation();
                    eadd.setText(Address);
                    epincode.setText(Pincode);
                }catch (Exception e){
                    Toast.makeText(Editprofile.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }
        });




        elang= findViewById(R.id.editlang);
        elang.setText(userlanguage +" " );

        esolink= findViewById(R.id.editsocialmedia);
        esolink.setText(userlink +" ");

        Spinner egen= findViewById(R.id.editgender);
        egen.setTag(usergender+" ");

        editbob =findViewById(R.id.editdob);
        editbob.setText(userdob);
        editbob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Editprofile.this,SETDT,c.get(Calendar.YEAR)   ,c.get(Calendar.MONTH) ,c.get(Calendar.DATE) )
                        .show();
            }
        });





    }

    private DatePickerDialog.OnDateSetListener SETDT =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

//            editbob.setText(day+"/"+(month+1)+"/"+year);
            editbob.setText(year+"-"+(month+1)+"-"+day);

        }
    };

    //toolmenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
       getMenuInflater().inflate(R.menu.toolmenueditprofile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.saved){

            ProgressDialog progressDialog=new ProgressDialog(Editprofile.this);
            progressDialog.setTitle("Updating...");
            progressDialog.setMessage("Loading");
            progressDialog.show();

            //save button
            SharedPreferences pref= getSharedPreferences("UserData",Context.MODE_PRIVATE);
            String userid =pref.getString("userid",null);
            String name  = ename.getText().toString();
            String dob  = editbob.getText().toString();
         //   String gender=egen.getTag().toString();
            String gender  = "male";
            String address  = eadd.getText().toString();
            String pincode  = epincode.getText().toString();
            String links  = esolink.getText().toString();
            setLocation();
            String lat=LAT;
            String log=LOG;

            APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
            Call<RegistrationResult> call=apiInterface.update(name,address,pincode ,lat,log,gender,dob, links, userid);

            call.enqueue(new Callback<RegistrationResult>() {
                @Override
                public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {

                arrayList = (ArrayList<Registration>) response.body().getRegistration();

                    Toast.makeText(Editprofile.this, "updated", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("UserData",Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed=preferences.edit();

                    ed.putString("Name", (String) arrayList.get(0).getName());
                    ed.putString("email",(String) arrayList.get(0).getEmail());
                    ed.putString("phoneno",(String) arrayList.get(0).getPhoneno());
                    ed.putString("address", (String) arrayList.get(0).getAddress());
                    ed.putString("pincode", (String) arrayList.get(0).getPincode());
                    ed.putString("dob", (String) arrayList.get(0).getDob());
                    ed.putString("gender", (String) arrayList.get(0).getGender());
                    ed.putString("dp", (String) arrayList.get(0).getProfilePicture());
                    ed.putString("link", (String) arrayList.get(0).getSocialLink());
                    ed.putString("address", (String) arrayList.get(0).getAddress());
                    ed.apply();



                    progressDialog.dismiss();

                }



                @Override
                public void onFailure(Call<RegistrationResult> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(Editprofile.this, ""+t+"", Toast.LENGTH_LONG).show();
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }




    //gps
    public  void  setLocation(){

        providerClient= LocationServices.getFusedLocationProviderClient( Editprofile.this);
        if (ContextCompat.checkSelfPermission(Editprofile.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder=new Geocoder(Editprofile.this,Locale.getDefault());
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
                                Address=addresses.get(0).getAddressLine(0);
                                Pincode =  addresses.get(0).getPostalCode();
                                LAT= String.valueOf(addresses.get(0).getLatitude());
                                LOG= String.valueOf(addresses.get(0).getLongitude());

                            }
                        }
                    });
        }

    }

}