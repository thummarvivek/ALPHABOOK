package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alphabook.api.Register.Registration;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    EditText pass,repass;
    Button btn;
    ArrayList<Registration> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent=getIntent();
        String userid= intent.getStringExtra("uid");

        arrayList = new ArrayList<>();

        pass=findViewById(R.id.resetpassword);
        repass=findViewById(R.id.reresetpassword);
        btn=findViewById(R.id.resetbtn);






        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref= getSharedPreferences("UserData", Context.MODE_PRIVATE);
                String userids =pref.getString("userid",null);
                String passwod=pass.getText().toString();
                
                
                APIInterface apiInterface=APPClient.getctient().create(APIInterface.class);
                Call<RegistrationResult> call=apiInterface.resetpassword(userids,passwod);
                call.enqueue(new Callback<RegistrationResult>() {
                    @Override
                    public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {

                        arrayList= (ArrayList<Registration>) response.body().getRegistration();

                        Toast.makeText(ResetPassword.this, "Your Password Sucessfully Reseted", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ResetPassword.this,Login.class);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<RegistrationResult> call, Throwable t) {
                        Toast.makeText(ResetPassword.this, ""+t, Toast.LENGTH_SHORT).show();
                    }
                });
                
                
//                Toast.makeText(ResetPassword.this, "userid =>"+userids+" pass =>"+passwod, Toast.LENGTH_SHORT).show();
//                APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
//                Call<RegistrationResult> call= apiInterface.resetpassword(userids,passwod);
//                Call<RegistrationResult> call= apiInterface.resetpassword("10005","018251052055");
//                call.enqueue(new Callback<RegistrationResult>() {
//                    @Override
//                    public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {
//
//                        arrayList= (ArrayList<Registration>) response.body().getRegistration();
//

//                    }
//
//                    @Override
//                    public void onFailure(Call<RegistrationResult> call, Throwable t) {
//                        Log.e("jsonerr", String.valueOf(t));
//                        Toast.makeText(ResetPassword.this, ""+t, Toast.LENGTH_SHORT).show();
//                    }
//                });


            }
        });


    }
}