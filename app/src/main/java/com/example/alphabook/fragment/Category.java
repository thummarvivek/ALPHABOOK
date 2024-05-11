package com.example.alphabook.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.alphabook.R;
import com.example.alphabook.Search;
import com.example.alphabook.adapter.Gridadapter;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.example.alphabook.databinding.ActivityMainBinding;
import com.example.alphabook.debhelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends Fragment {
    ActivityMainBinding binding;
    ArrayList<Book> arrayList;
    RecyclerView recyclerView;
    SearchView  searchView;

    //swipe
    SwipeRefreshLayout refreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.getRoot();

        arrayList=new ArrayList<Book>();
        recyclerView=view.findViewById(R.id.gridid);

        searchView=view.findViewById(R.id.categoryserarch);

        LinearLayoutManager linearLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);




        refreshLayout =view.findViewById(R.id.categoryrefersh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();


                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Stop animation (This will be after 3 seconds)
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000); // Delay in millis


            }
        });
        refreshLayout .setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );





    Refresh();



    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {

            APIInterface apiInterface =APPClient.getctient().create(APIInterface.class);
            Call<AddBookResult> call= apiInterface.searchcategorys(s);
            call.enqueue(new Callback<AddBookResult>() {
                @Override
                public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                    arrayList = (ArrayList<Book>) response.body().getBooks();
                    Gridadapter gridadapter=new Gridadapter(getContext(),arrayList);
                    recyclerView.setAdapter(gridadapter);
                }

                @Override
                public void onFailure(Call<AddBookResult> call, Throwable t) {

                }
            });

            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    });


        return  view;
    }

    public  void Refresh(){
        APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call = apiInterface.categoryshow();
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                try {
                    arrayList= (ArrayList<Book>) response.body().getBooks();
                    Gridadapter gridadapter=new Gridadapter(getContext(),arrayList);
                    recyclerView.setAdapter(gridadapter);
                    debhelper  d=new debhelper(getActivity());
                    d.save(arrayList);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "error : "+e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {
                Toast.makeText(getContext(), "error : "+t, Toast.LENGTH_SHORT).show();
            }
        });

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolmenucart, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.order){
            Intent intent = new Intent(getActivity(), Search.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }





}