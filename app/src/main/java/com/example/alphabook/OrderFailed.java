package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alphabook.fragment.Home;

public class OrderFailed extends AppCompatActivity {
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_failed);
        button = findViewById(R.id.failbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OrderFailed.this,Home.class);
                startActivity(intent);
                finish();
            }
        });


    }
}