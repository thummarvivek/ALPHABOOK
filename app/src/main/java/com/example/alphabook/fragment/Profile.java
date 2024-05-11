package com.example.alphabook.fragment;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.Bookdetails;
import com.example.alphabook.Login;
import com.example.alphabook.MyBooks;
import com.example.alphabook.Path;
import com.example.alphabook.R;
import com.example.alphabook.Editprofile;
import com.example.alphabook.Myorder;
import com.example.alphabook.api.Register.Registration;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends Fragment {
    private static final int PICK_IMAGE=1;

    Toolbar toolbar;
    ImageView imageView;
    Vibrator vibrator;
    TextView textView ,textViewedit;
    String ImageUriData;
    ArrayList<Registration>arrayList;
    FloatingActionButton uploadbtn;


    public Profile(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup    container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar =view.findViewById(R.id.toolbarprofile);
        AppCompatActivity appCompatActivity=(AppCompatActivity)getActivity();

        //
        TextView textView0 = view.findViewById(R.id.myboouploaded);
        textView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MyBooks.class);
                startActivity(intent);

            }
        });



        TextView textView1 = view.findViewById(R.id.myorders);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inext = new Intent( getActivity(), Myorder.class);
                startActivity(inext);
            }
        });



        appCompatActivity.setSupportActionBar(toolbar);



        imageView=view.findViewById(R.id.profiledp);

        arrayList = new ArrayList<>();


        textView=view.findViewById(R.id.editpofilebtn);

        SharedPreferences pre= appCompatActivity.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        String user_name =pre.getString("username",null);
        String password=pre.getString("password",null);
        APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
        Call<RegistrationResult> call=apiInterface.login(user_name,password);
        call.enqueue(new Callback<RegistrationResult>() {
            @Override
            public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {

                try {

                    arrayList =(ArrayList<Registration>) response.body().getRegistration();

                    toolbar.setTitle(arrayList.get(0).getUserName());
                    toolbar.setTitleMarginStart(50);




                    TextView useridfind=view.findViewById(R.id.usernametext);
                    useridfind.setText(arrayList.get(0).getName());

                    TextView useraddressfind=view.findViewById(R.id.useraddresstext);
                    useraddressfind.setText(arrayList.get(0).getAddress());

                    TextView userphonefind=view.findViewById(R.id.userphonenutext);
                    userphonefind.setText(arrayList.get(0).getPhoneno());

                    TextView usermailfind=view.findViewById(R.id.usermailtext);
                    usermailfind.setText(arrayList.get(0).getEmail());

                    Picasso.get().load((String) arrayList.get(0).getProfilePicture()).placeholder(R.drawable.userdp).into(imageView);

                }catch (Exception e){
                    Toast.makeText(appCompatActivity, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegistrationResult> call, Throwable t) {
                Intent intent=new Intent(getContext(), Login.class);
                startActivity(intent);
                SharedPreferences.Editor ed=pre.edit();
                ed.clear();
                ed.apply();
                Toast.makeText(appCompatActivity,   ""+t, Toast.LENGTH_SHORT).show();
            }

        });








       //end save data



         textViewedit=view.findViewById(R.id.editpofilebtn);
         textViewedit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(getContext(),Editprofile.class);
                 startActivity(intent);
             }
         });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Editprofile.class);
                startActivity(intent);
            }
        });

        vibrator =(Vibrator)  getContext().getSystemService(Context.VIBRATOR_SERVICE);

   imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {



    }
});

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);

                vibrator.vibrate(100);

                return false;
            }
        });



        uploadbtn = view.findViewById(R.id.uploadbuttton);
        uploadbtn.setVisibility(View.GONE);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                SharedPreferences pre= appCompatActivity.getSharedPreferences("UserData",Context.MODE_PRIVATE);
                String userid =pre.getString("userid",null);

                try {

                    File file=new File(ImageUriData);

                    RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);

                    MultipartBody.Part fileupload = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

                    RequestBody uid=RequestBody.create(MediaType.parse("text/plain"),userid);

                    Log.e("uploadlogerr", " "+userid);

                    APIInterface api=APPClient.getctient().create(APIInterface.class);
                    Call<RegistrationResult>c = api.uploadprofile(fileupload,uid);

                    c.enqueue(new Callback<RegistrationResult>() {
                        @Override
                        public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {

                        arrayList= (ArrayList<Registration>) response.body().getRegistration();

                        Toast.makeText(getContext(), "File Uploaded", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<RegistrationResult> call, Throwable t) {
                            Toast.makeText(getContext(), "Uploaded Failed "+t, Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", String.valueOf(t));
                            progressDialog.dismiss();
                        }
                    });



                }catch (Exception e){
                    Toast.makeText(appCompatActivity, ""+e, Toast.LENGTH_LONG).show();
                    Log.e("errirore", String.valueOf(e));
                    Toast.makeText(appCompatActivity, "ex : "+e, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

        return view;
    }//end create view


//profile upload
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){

            Uri uri= data.getData();
            imageView.setImageURI(uri);
            uploadbtn.setVisibility(View.VISIBLE);
           /* String[] filepathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(uri,filepathColumn,null,null,null,null);
            assert cursor != null;
            cursor.moveToFirst();l̥
            int clumnIndex =cursor.getColumnIndex(filepathColumn[0]);
            ImageUriData =cursor.getString(clumnIndex);*/
            ImageUriData= Path.getPathFromUri(getActivity(),uri);
            Log.e("IMAGPATH",ImageUriData);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolmenuprofile, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.profilemenu){
            Bottomsheet b =new Bottomsheet();
            b.show(getActivity().getSupportFragmentManager(),"tag");
        }


        return super.onOptionsItemSelected(item);
    }







}//end main function