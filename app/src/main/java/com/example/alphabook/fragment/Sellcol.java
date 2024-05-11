package com.example.alphabook.fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.R;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.category.Category;
import com.example.alphabook.api.category.CategoryResult;
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

public class Sellcol extends Fragment {

    static FusedLocationProviderClient providerClient;
    boolean b = true;

    //spine ke liye
    TextView spintextstream;
    ArrayList<Category> arrayListspin;
    ArrayList<Book> arrayList;
    Dialog dialog;

    EditText gpsset;
    ImageButton gpscall;
    EditText sellamount, selledition, sellpublisher;
    Button button;

    TextView info;
    //upload

    String address;
    String cateagoryid, userid;
    String latitude;
    String logitude;
    String AMOUNT, EDITION;
    String PUBLI;

    public static final String TRACER = "tracer";
    private static final String CHANNEL_ID="My Channal";
    private static final int NOTIFICATION_ID = 100;

    public Sellcol() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sellcol, container, false);


        setLocation();

        //spinner ke liye
        spintextstream = view.findViewById(R.id.selectstream);


        arrayListspin = new ArrayList<>();

        arrayList = new ArrayList<>();
        spintextstream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//api call
                APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
                Call<CategoryResult> call = apiInterface.showstream();
                call.enqueue(new Callback<CategoryResult>() {
                    @Override
                    public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {

                        arrayListspin = (ArrayList<Category>) response.body().getCategory();

                        ArrayList<String> arrayList = new ArrayList<String>();
                        for (int i = 0; i < arrayListspin.size(); i++) {
                            arrayList.add(arrayListspin.get(i).getCategory() + ", " + arrayListspin.get(i).getUniverBoard());

                        }

                        dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.dialog_stream_spinner);
                        dialog.getWindow().setLayout(650, 750);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            dialog.getWindow().setBackgroundBlurRadius(100);
                        }
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        dialog.show();

                        EditText editText = dialog.findViewById(R.id.searchspiner);
                        ListView listView = dialog.findViewById(R.id.spinerlist);
                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                        listView.setAdapter(stringArrayAdapter);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                stringArrayAdapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                spintextstream.setText(arrayListspin.get(i).getCategory() + ", " + arrayListspin.get(i).getUniverBoard());
                                cateagoryid = arrayListspin.get(i).getCategoryId();
                                dialog.dismiss();
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<CategoryResult> call, Throwable t) {

                    }
                });

//end api


            }
        });

        //gps
        gpscall = view.findViewById(R.id.gpscall);
        gpsset = view.findViewById(R.id.gpsid);
        gpscall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocation();
                 not("hello");


                gpsset.setText(address);
                Toast.makeText(getContext(), "" + address, Toast.LENGTH_SHORT).show();

            }
        });

        SharedPreferences pref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userid = pref.getString("userid", null);

        sellamount = view.findViewById(R.id.sellamount);
        selledition = view.findViewById(R.id.selledition);
        sellpublisher = view.findViewById(R.id.sellpub);


//
        button = view.findViewById(R.id.uploadbook);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                PUBLI = sellpublisher.getText().toString();
                AMOUNT = sellamount.getText().toString();
                EDITION = selledition.getText().toString();


                try {

                    APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
                    Call<AddBookResult> call = apiInterface.uploadsellbook(cateagoryid, PUBLI, EDITION, AMOUNT, userid, latitude, logitude, address);
//                    Call<AddBookResult> call = apiInterface.uploadsellbook("10001", "PUBLI", "2002", "220", "10002", "0.57", "0.27", "address");
                    call.enqueue(new Callback<AddBookResult>() {
                        @Override
                        public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                            arrayList = (ArrayList<Book>) response.body().getBooks();
                            notification("Book Uploaded", "Congras! Your Book is Uploaded");
                            Toast.makeText(getContext(), "Book Uploaded", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AddBookResult> call, Throwable t) {
                            Toast.makeText(getContext(), "json error : " + t, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getContext(), "exections : " + e, Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }



    private void notification(String t, String s) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());

        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(t)
                .setContentText(s);

        Intent intent = new Intent(getContext(), Home.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);








    }




    //gps
    public void setLocation() {

        providerClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //       toolbar.setTitle(addresses.get(0).getAddressLine(0));
                                //      android:drawableLeft="@drawable/ic_baseline_location_on_24"


                                latitude = String.valueOf(addresses.get(0).getLatitude());
                                logitude = String.valueOf(addresses.get(0).getLongitude());
                                address = addresses.get(0).getAddressLine(0);

//                                Log.e("addedxc",
//                                        addresses.get(0).getAddressLine(1)+"\n"+
//                                            addresses.get(0).getLocality()+"\n"
//                                                +addresses.get(0).getAdminArea()+"\n"
//                                                +addresses.get(0).getFeatureName()+"\n"
//                                                +addresses.get(0). getSubLocality()+"\n"
//                                );

                            }
                        }
                    });
        }

    }


//    void notifyshow(String title, String content) {
//        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("chanel_description");
//            mNotificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(title)
//                .setContentText(content)
//                .setAutoCancel(true)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        Intent intent=new Intent(getContext(),Sellcol.class);
//        PendingIntent pi=PendingIntent.getActivity(getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pi);
//        mNotificationManager.notify(0,builder.build());
//
//    }

    private void not(String methodName) {
        String name = this.getClass().getName();
        String[] strings = name.split("\\.");
        Notification.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(getContext(), TRACER);
        } else {
            //noinspection deprecation
            notificationBuilder = new Notification.Builder(getContext());
        }

        Notification notification = notificationBuilder
                .setContentTitle(methodName + " " + strings[strings.length - 1])
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(name).build();
        NotificationManager notificationManager =
                (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    private void createChannel() {
        NotificationManager mNotificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mNotificationManager = getActivity().getSystemService(NotificationManager.class);
        }
        // The id of the channel.
        String id = TRACER;
        // The user-visible name of the channel.
        CharSequence name = "Activity livecycle tracer";
        // The user-visible description of the channel.
        String description = "Allows to trace the activity lifecycle";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);
        }
        // Configure the notification channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel.setDescription(description);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }


}



