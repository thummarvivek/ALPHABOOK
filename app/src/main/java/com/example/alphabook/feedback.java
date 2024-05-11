package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.Register.Registration;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class feedback extends AppCompatActivity {

    RatingBar rb;
    TextView textView;
    EditText feedback;
    Button btn;
    ArrayList<Registration> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        textView=findViewById(R.id.ratetext);
        textView.setText("Select The Rate");
        arrayList=new ArrayList<>();

        rb=findViewById(R.id.ratingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            if(v==1){
                textView.setText("Good 😅");
            }
            else if(v==2){
                textView.setText("Very Good 😀");
                }
            else if(v==3){
                textView.setText("Best 😊");
            }
            else if(v==4){
                textView.setText("Awesome 😎");
            }
            else if(v==5){
                textView.setText("Excecllent 🤩");
            }



            }
        });

        feedback=findViewById(R.id.txtfeedback);

        
        btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog=new ProgressDialog(feedback.this);
                progressDialog.setMessage("Submiting");
                progressDialog.show();

                SharedPreferences pref= getSharedPreferences("UserData", Context.MODE_PRIVATE);
                String userid =pref.getString("userid",null);
                String feebacktxt=  feedback.getText().toString();
                String rate= String.valueOf(rb.getNumStars());
                APIInterface apiInterface=APPClient.getctient().create(APIInterface.class);
                Call<RegistrationResult> call =apiInterface.insertfeedback(userid,rate,feebacktxt);
                call.enqueue(new Callback<RegistrationResult>() {
                    @Override
                    public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {
                        Toast.makeText(feedback.this, "ThankYou For Sumbiting Feedback", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent=new Intent(com.example.alphabook.feedback.this,MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<RegistrationResult> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
                 
            }
        });
        
    }
}