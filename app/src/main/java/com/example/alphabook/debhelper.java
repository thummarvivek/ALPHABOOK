package com.example.alphabook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.alphabook.api.books.Book;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class debhelper extends SQLiteOpenHelper {

    private static final String DBNAME="mydatabase";
    private static final String TNAME="mytable";
    private static final String ID ="id";
    private static final String BOOKID="bookid";
    private static final String IMG="bookid";
    private static final int VERSION=1;
    byte[] photo;
    public debhelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create  table " + TNAME +"("+ID +" integer primary key autoincrement ," + BOOKID +" text ,"+IMG+"  BLOB NOT NULL)" ;
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  void save(ArrayList<Book>arrayList) {
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(BOOKID, arrayList.get(i).getCategoryId());
            cv.put(IMG, arrayList.get(i).getImg1());
            db.insert(TNAME, ID, cv);
        }
        db.close();
    }


}
