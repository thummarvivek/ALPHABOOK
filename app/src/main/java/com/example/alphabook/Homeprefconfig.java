package com.example.alphabook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.alphabook.api.books.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Homeprefconfig {



    public static  void Homeprefconfig(Context context, ArrayList<Book>arrayList) {

        Gson gson =new Gson();
        String jsonString = gson.toJson(arrayList);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("book_key",jsonString);
        editor.apply();
    }

    public  static  ArrayList<Book> readlistpref (Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString("book_list",null);

        Gson gson =new Gson();
        Type type =new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> list = gson.fromJson(jsonString,type);

        return list;
    }

}
