package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.alphabook.adapter.HelpAdapter;

public class HelpCenter extends AppCompatActivity {

    ListView listView;
    String title[]={"Welcome to our Help Center for selling and reselling old books! We're here to help you navigate our platform and answer any questions you may have",
            "SELLING BOOKS\nIf you have books you would like to sell, here are the steps to follow:",
            "Create an account:" ,"Upload your listings:","Set your prices:","Wait for a buyer:"
            ,"\nRESELLING BOOKS \nIf you're looking to purchase old books from our platform, here's how to get started:"
            ,"Browse our collection:" ,"Check the condition:" ,"Purchase the book:" ,"Receive your book: ",""};
    String description[]={
            "",
            ""
            ,"The first step to selling your old books is to create an account on our Application. This will enable you to upload and manage your listings."
            ,"Once you have an account, you can start uploading your listings. Make sure to provide accurate descriptions of the condition of your books and include clear photos."
            ," You can set your prices based on the condition of your books, rarity, and other factors. We recommend researching the prices of similar books to ensure that your pricing is competitive."
            ," Once your listings are live, potential buyers can browse and purchase your books. You will receive notifications when someone makes a purchase, and you can then ship the books to the buyer."
            ,""
            ," Use our search bar or browse our categories to find the books you're interested in."
            ," Make sure to carefully read the description and check the photos to ensure that the book is in the condition you're looking for."
            ," Once you've found the book you want, add it to your cart and proceed to checkout. We accept various payment methods, including credit cards and Online Payemnt App."
            ,"After your purchase is complete, we will ship the book to your address. We use reliable shipping carriers to ensure that your book arrives safely and on time."
            ,"If you have any further questions about selling or reselling books on our platform, please don't hesitate to contact us. Our customer service team is available to assist you with any issues or concerns. Thank you for choosing our platform to buy or sell your old books"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        listView=findViewById(R.id.helplist);

        HelpAdapter helpAdapter=new HelpAdapter(HelpCenter.this,title,description);
        listView.setAdapter(helpAdapter);

    }
}