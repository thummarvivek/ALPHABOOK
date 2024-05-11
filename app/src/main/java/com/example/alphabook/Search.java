package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.alphabook.adapter.SearchAdapter;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {

    SearchView search;
    ArrayList<Book> arrayList;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search =findViewById(R.id.search_bar);
        rv =findViewById(R.id.searchrv);
        arrayList =new ArrayList<>();

        LinearLayoutManager lv=new LinearLayoutManager(this);
        rv.setLayoutManager(lv);



//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                searchresult(editable.toString());
//
//            }
//        });

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
                    Call<AddBookResult> call=apiInterface.searchbook(s);
                    call.enqueue(new Callback<AddBookResult>() {
                        @Override
                        public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                            arrayList=(ArrayList<Book>)response.body().getBooks();
                            SearchAdapter searchAdapter=new SearchAdapter(Search.this,arrayList);
                            rv.setAdapter(searchAdapter);
                        }
                        @Override
                        public void onFailure(Call<AddBookResult> call, Throwable t) {
                            Toast.makeText(Search.this, "error "+t, Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {

//                    searchresult(s);
                    return false;
                }
            });




    }




}