package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.books.Book;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPayment extends AppCompatActivity implements PaymentResultListener, RadioGroup.OnCheckedChangeListener {

    String oderamount;
    String userid,cartid,bookid;
    CardView cardViewcod,cardViewon;
    Button online ,cod;

    ArrayList<Book> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);

        arrayList=new ArrayList<>();
        
        Intent intent=getIntent();
        oderamount= intent.getStringExtra("orderamount");
        cartid= intent.getStringExtra("cartid");
        bookid= intent.getStringExtra("bookid");

        SharedPreferences preff= getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userid =preff.getString("userid",null);
        
        

        TextView textView1 = findViewById(R.id.priceitempay);
        textView1.setText(oderamount);

        TextView textView2 = findViewById(R.id.totalamountpay);
        textView2.setText(oderamount);

//
//        TextView textView3 = findViewById(R.id.amountoforderpay);
//        textView3.setText(oderamount);



        //btn
        online=findViewById(R.id.orderpayment);
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

        cod=findViewById(R.id.orderpaymentcod);
        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentinsert("CASH ON DILVERY" ,"0");
            }
        });


        //radio
        RadioGroup radioGroup=findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(this);

        //cart
        cardViewcod = findViewById(R.id.cardpricecod);
        cardViewon = findViewById(R.id.cardpriceonline);
        cardViewon.setVisibility(View.VISIBLE);
        cardViewcod.setVisibility(View.GONE);


    }

    //payment method
    public void startPayment() {



//        String amount =oderamount +"00";


        String amount ="100" +"00";

        SharedPreferences pref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name =pref.getString("Name",null);
        String address=pref.getString("address",null);
        String phone=pref.getString("phoneno",null);
        String mail=pref.getString("email",null);


        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_test_bwcdsitMudgHOo");
        checkout.setKeyID("rzp_test_2op5jgLoE4nmdP");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {



            JSONObject options = new JSONObject();

            options.put("name", name);
            options.put("description", "Reference No. #123456");
            options.put("image",R.drawable.logo);
            //      options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#0F537D");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", mail);
            options.put("prefill.contact",phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Toast.makeText(activity, ""+e, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {
//        Intent intent=new Intent(OrderPayment.this,OrderFailed.class);
//        startActivity(intent);
        Toast.makeText(this, "Error" +i+" "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i==R.id.radioButton1){
                cardViewon.setVisibility(View.VISIBLE);
                cardViewcod.setVisibility(View.GONE);
        }
        else if (i==R.id.radioButton2){
            cardViewon.setVisibility(View.GONE);
            cardViewcod.setVisibility(View.VISIBLE);
        }
    }
    //end payment

    public void paymentinsert(String type ,String status){

        APIInterface apiInterface=APPClient.getctient().create(APIInterface.class);
//        Call<AddBookResult> call=apiInterface.onlinepayment("10005","10022","10041",type,status);
        Call<AddBookResult> call=apiInterface.onlinepayment(userid,bookid,cartid,type,status);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                arrayList =(ArrayList<Book>) response.body().getBooks();

                updateorder(userid, arrayList.get(0).getBookId(),arrayList.get(0).getPayId());
                Intent intent=new Intent(OrderPayment.this,OrderSuccessful.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {
                Toast.makeText(OrderPayment.this, ""+t, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(OrderPayment.this,OrderFailed.class);
                startActivity(intent);
            }
        });



    }

    private void updateorder(String a, String b, String c) {
        APIInterface apiInterface=APPClient.getctient().create(APIInterface.class);
        Call<AddBookResult> call=apiInterface.updateorder(a,b,c);
        call.enqueue(new Callback<AddBookResult>() {
            @Override
            public void onResponse(Call<AddBookResult> call, Response<AddBookResult> response) {
                arrayList= (ArrayList<Book>) response.body().getBooks();
                Toast.makeText(OrderPayment.this, "Update Order", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddBookResult> call, Throwable t) {

            }
        });
    }
}