package com.example.alphabook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.alphabook.adapter.Viewpageradapter;
import com.example.alphabook.fragment.Cart;
import com.example.alphabook.fragment.Category;
import com.example.alphabook.fragment.Home;
import com.example.alphabook.fragment.Notification;
import com.example.alphabook.fragment.Profile;
import com.example.alphabook.fragment.Sell;
import com.example.alphabook.fragment.Sellcol;
import com.example.alphabook.fragment.Sellother;
import com.example.alphabook.fragment.Sellschool;
import com.example.alphabook.fragment.ViewPagerAdapter;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
 
    BottomNavigationView bnv;
    Vibrator vibrator;
    FrameLayout frameLayout; 

    //swip
    ViewPager  viewPager;

    //map
    private static Location location;
    private  static FusedLocationProviderClient providerClient;
    private   boolean b = false;
    private  static final int LOCATIONREQCODE=2;
    private LocationCallback locationCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();

        frameLayout=findViewById(R.id.container);

        //swip
//        viewPager = findViewById(R.id.pager);
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(viewPagerAdapter);


        //map
        providerClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        checkGPS();
        createlocation();


//        map


        providerClient =LocationServices . getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                 location= locationResult.getLocations().get(0);
            }
        };
        startLocationUpdates();

        loadfragment(new Home());
        bnv=findViewById(R.id.bv);
        bnv.setOnNavigationItemSelectedListener(this);
        vibrator =(Vibrator)  getSystemService(Context.VIBRATOR_SERVICE);


    }



    public  boolean loadfragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();

        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;

        switch (item.getItemId())
        {
            case R.id.navigation_home:
                fragment=new Home();
                //swip ke liye
//                viewPager.setCurrentItem(0);
                vibrator.vibrate(50);
                break;
            case R.id.navigation_category:
                fragment=new Category();
//                viewPager.setCurrentItem(1);
                vibrator.vibrate(50);
                break;
                case R.id.navigation_sell:
                fragment=new Sell();
//                viewPager.setCurrentItem(2);
                vibrator.vibrate(50);
                break;
            case R.id.navigation_cart:
                fragment=new Cart();
//                viewPager.setCurrentItem(3);
                vibrator.vibrate(50);
                break;
            case R.id.navigation_profile:
                fragment=new Profile();
//                viewPager.setCurrentItem(4);
                vibrator.vibrate(50);
                break;
        }

        //swip ke liye
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);
//                        break;
//                    case 1:
//                        bnv.getMenu().findItem(R.id.navigation_category).setChecked(true);
//                        break;
//                    case 2:
//                        bnv.getMenu().findItem(R.id.navigation_sell).setChecked(true);
//                        break;
//                    case 3:
//                        bnv.getMenu().findItem(R.id.navigation_cart).setChecked(true);
//                        break;
//                    case 4:
//                        bnv.getMenu().findItem(R.id.navigation_profile).setChecked(true);
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//
//        });
//        return true;
        return loadfragment(fragment);
    }

//


    @Override
    public void onBackPressed() {

        if(bnv.getSelectedItemId()==R.id.navigation_home){
            onDestroy();
            super.onBackPressed();
            finish();
        }
        else {
            bnv.setSelectedItemId(R.id.navigation_home);
        }

//        if(getSupportFragmentManager().getBackStackEntryCount()>0){
//            getSupportFragmentManager().popBackStack();
//        }
//        else {
//            super.onBackPressed();
//            onDestroy();
//        }

    }




//map
public void createlocation() {
    @SuppressLint("MissingPermission")
    Task<Location> locationTask = providerClient.getLastLocation();
    locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
        @Override
        public void onSuccess(Location location1) {
            if (location1 != null) {
                location = location1;
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
                ArrayList<Address> arrayList = null;
                try {
                    arrayList = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, (CharSequence) e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                String address = arrayList.get(0).getAddressLine(0);

               // ed1.setText(address);
            }
        }
    });
}



//map
private void startLocationUpdates() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new
                        String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATIONREQCODE);
    } else {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        providerClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
}





    public void showSettingsAlert() {
        if (!b) {
            android.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // Setting Dialog Title
            alertDialog.setTitle("");
            // Setting Dialog Message
            alertDialog.setMessage("For better experience turn on device's location");
            // On pressing Settings button
            alertDialog.setPositiveButton(
                    getResources().getString(R.string.Turn_on),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            b = true;
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            alertDialog.show();
        }
        else {
           // createlocation();
        }
    }

    void checkGPS() {
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d("GPS_main", "OnSuccess");
                // GPS is ON
                b = true;
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("GPS_main", "GPS off");
                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(MainActivity.this, 100);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                  //  createlocation();
                    break;
                case Activity.RESULT_CANCELED:
                    checkGPS();
                    break;
                default:
                    break;
            }
        }
    }

}