package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("UserData",Context.MODE_PRIVATE);
                Boolean b=preferences.getBoolean("is_regi",false);
                if(b)
                {
                    Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);



    }
}