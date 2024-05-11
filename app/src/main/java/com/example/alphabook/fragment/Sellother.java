package com.example.alphabook.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.R;
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

public class Sellother extends Fragment {



    public Sellother() {
        // Required empty public constructor
    }

    //image
    int PICK_IMAGE_MULTIPLE = 1;
    int position =0;
    ArrayList<Uri> mArrayUri;
    ImageView i1,i2,i3;


//api string
    String address;
    String cateagoryid, userid;
    String latitude;
    String logitude;
    String AMOUNT, EDITION ,TITLE,PUBLI,AUTHORE;

    //address set
    EditText gpsset;
    ImageButton gpscall;
    static FusedLocationProviderClient providerClient;
    boolean b=true;

    //spinerr
    TextView spintextstream;
    ArrayList<Category> arrayListspin;
    ArrayList<Book> arrayList;
    Dialog dialog;
    ArrayList files;
Button upload;




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sellother, container, false);



        //textview all
        EditText title=view.findViewById(R.id.titlesell);
        EditText pub=view.findViewById(R.id.publishersell);
        EditText ed=view.findViewById(R.id.editionid);
        EditText au=view.findViewById(R.id.authoresell);
        EditText amount=view.findViewById(R.id.amountsell);


        TITLE=title.getText().toString();
        PUBLI=pub.getText().toString();
        EDITION=ed.getText().toString();
        AUTHORE=au.getText().toString();
        AMOUNT=amount.getText().toString();




        files= new ArrayList<>();


     upload=view.findViewById(R.id.otherupload);
    upload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "Book Uploaded", Toast.LENGTH_SHORT).show();
        }
    });

        //spinner ke liye
        spintextstream = view.findViewById(R.id.categoryspiner);


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



            mArrayUri=new ArrayList<>();

        i1 = view.findViewById(R.id.bid1);
        i2 = view.findViewById(R.id.bid2);
        i3 = view.findViewById(R.id.bid3);
//        i4 = view.findViewById(R.id.bid4);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"NKHL"),PICK_IMAGE_MULTIPLE);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"NKHL"),PICK_IMAGE_MULTIPLE);


            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"NKHL"),PICK_IMAGE_MULTIPLE);


            }
        });


        gpscall =view.findViewById(R.id.gpscallo);
        gpsset = view.findViewById(R.id.gpsseto);
        gpscall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocation();
                gpsset.setText(address);
                Toast.makeText(getContext(), ""+address, Toast.LENGTH_SHORT).show();
            }
        });


        UploadBook();

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == -1 && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                }
                // setting 1st selected image into image switcher
                i1.setImageURI(mArrayUri.get(0));
                i2.setImageURI(mArrayUri.get(1));
                i3.setImageURI(mArrayUri.get(2));
                position = 0;
            } else {
                Uri imageurl = data.getData();
                mArrayUri.add(imageurl);
                i1.setImageURI(mArrayUri.get(0));
                i2.setImageURI(mArrayUri.get(1));
                i3.setImageURI(mArrayUri.get(2));
                position = 0;
            }
        } else {
            // show this if no image is selected
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }



//        if (requestCode==PICK_IMAGE_MULTIPLE && requestCode==-1){
//            if (data.getClipData()!=null){
//                for (int i=0; i<data.getClipData().getItemCount(); i++)
//                {
//                    Uri imageurl=data.getClipData().getItemAt(i).getUri();
//                    String filename=getfilename(imageurl);
//                    files.add(filename);
//                    mArrayUri.add(imageurl);
//                    i1.setImageURI(mArrayUri.get(0));
//                    i2.setImageURI(mArrayUri.get(1));
//                    i3.setImageURI(mArrayUri.get(2));
//
//                }
//            }
//        }


//        if (requestCode==1){
//            Uri uri1= data.getData();
//            i1.setImageURI(uri1);
//        }
//        else if (requestCode==2){
//            Uri uri2= data.getData();
//            i2.setImageURI(uri2);
//        }
//        else if (requestCode==3){
//            Uri uri3= data.getData();
//            i3.setImageURI(uri3);
//        }

    }

//    @SuppressLint("Range")
//    public String getfilename(Uri filepath)
//    {
//        String result = null;
//        if (filepath.getScheme().equals("content")) {
//            Cursor cursor = getContext().getContentResolver().query(filepath, null, null, null, null);
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        if (result == null) {
//            result = filepath.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut != -1) {
//                result = result.substring(cut + 1);
//            }
//        }
//        return result;
//    }


    //gps
    public  void  setLocation(){

        providerClient= LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            providerClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder=new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //       toolbar.setTitle(addresses.get(0).getAddressLine(0));
                                //      android:drawableLeft="@drawable/ic_baseline_location_on_24"





                                latitude = String.valueOf(addresses.get(0).getLatitude());
                                logitude = String.valueOf(addresses.get(0).getLongitude());
                                address = addresses.get(0).getAddressLine(0);

                            }
                        }
                    });
        }

    }



    public void UploadBook() {
    }

}