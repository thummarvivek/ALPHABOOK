package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.alphabook.adapter.Homelistadapter;
import com.example.alphabook.adapter.Myorderadapter;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooks extends AppCompatActivity {

    RecyclerView lv;
    ArrayList<Book> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        SharedPreferences pref= getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String Name =pref.getString("Name",null);
        String userid =pref.getString("userid",null);

        arrayList=new ArrayList<>();
        lv = findViewById(R.id.mybooksrv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);

        APIInterface apiInterface = APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call=apiInterface.showmybook(userid);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                arrayList = (ArrayList<Book>) response.body().getBooks();
                Homelistadapter myorderadapter=new Homelistadapter(MyBooks.this,arrayList);
                lv.setAdapter(myorderadapter);
            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {

            }
        });



    }
}