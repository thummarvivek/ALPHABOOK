package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.Register.Registration;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.example.alphabook.fragment.Home;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView textView ,textView2,forget;
    Button button;
    EditText user,pass;
    ArrayList<Registration>arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView=findViewById(R.id.register);
        textView2=findViewById(R.id.forgetregister);

        button=findViewById(R.id.loginbtn);

        user=findViewById(R.id.loginuser);
        pass=findViewById(R.id.loginpass);

        forget=findViewById(R.id.forgetregister);

        arrayList = new ArrayList<>();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Comming Soon!", Toast.LENGTH_SHORT).show();

            }
        });

        //checking registration


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_name  = user.getText().toString();
                String password=pass.getText().toString();


                if(TextUtils.isEmpty(user.getText().toString())){
                    user.setError("this username field is emty");
                    return;
                }
                else if(TextUtils.isEmpty(pass.getText().toString())){
                    user.setError("this password field is emty");
                    return;
                }


                ProgressDialog progressDialog=new ProgressDialog(Login.this);
                progressDialog.setMessage("Signing");
                progressDialog.show();

                APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
                Call<RegistrationResult> call=apiInterface.login(user_name,password);

                call.enqueue(new Callback<RegistrationResult>() {
                    @Override
                    public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {

                      try {

                          arrayList =(ArrayList<Registration>) response.body().getRegistration();



                          String u=arrayList.get(0).getUserName();
                          String p=arrayList.get(0).getPassword();

                            if (user_name.equals(u) && password.equals(p)){
//
                                SharedPreferences preferences = getSharedPreferences("UserData",Context.MODE_PRIVATE);
                                SharedPreferences.Editor ed=preferences.edit();

                                ed.putString("username",u);
                                ed.putString("password",p);
                                ed.putString("userid", (String) arrayList.get(0).getUserId());
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
                                ed.putString("password", (String) arrayList.get(0).getPassword());




                                ed.putBoolean("is_regi",true);

                                ed.apply();

                                progressDialog.dismiss();

                                Toast.makeText(Login.this, "Welcome  "+arrayList.get(0).getName()+" ", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();



                            }
                            else {

                                user.setText("");
                                pass.setText("");
                                new AlertDialog.Builder(Login.this)
                                        .setIcon(R.drawable.logo)
                                        .setTitle("Your Are Not Register")
                                        .setMessage("Are You Confirm to Register")
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent=new Intent(Login.this,Register.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(Login.this, "ok try again", Toast.LENGTH_SHORT).show();

                                            }
                                        }).show();
                                Toast.makeText(Login.this, "please retry", Toast.LENGTH_SHORT).show();
                            }


                      }catch (Exception e){
//
                          Toast.makeText(Login.this , "Error!"+e+"", Toast.LENGTH_SHORT).show();
                          progressDialog.show();
                      }

                    }

                    @Override
                    public void onFailure(Call<RegistrationResult> call, Throwable t) {
                        Toast.makeText(Login.this, ""+t+"", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        onDestroy();
        super.onBackPressed();
        }
}