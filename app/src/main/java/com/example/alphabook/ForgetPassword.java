package com.example.alphabook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.Register.Registration;
import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

   private FirebaseAuth mAuth;
   private String verificationid;
   private Button getotpbtn,confirmotp;
   private EditText user,phone,otptxt;
    ArrayList<Registration>arrayList;
    TextView noshow;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        arrayList = new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();

        confirmotp=findViewById(R.id.conotp);
        getotpbtn=findViewById(R.id.getotp);
        user=findViewById(R.id.forgetuser);
        phone=findViewById(R.id.forgetphoneno);
        otptxt=findViewById(R.id.forgetotptxt);
        noshow=findViewById(R.id.phonetxt);

//        noshow.setVisibility(View.GONE);
//        otptxt.setVisibility(View.GONE);
//        confirmotp.setVisibility(View.GONE);


        getotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                user.setVisibility(View.GONE);
//                phone.setVisibility(View.GONE);
//                getotpbtn.setVisibility(View.GONE);
//
//                noshow.setText("Sucessfully Sent OTP, This Number " +phone.getText().toString());
//                noshow.setVisibility(View.VISIBLE);
//
//                userid=arrayList.get(0).getUserId();
//
//                otptxt.setVisibility(View.VISIBLE);
//                confirmotp.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(ForgetPassword.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
                else{

                    String phoneno = phone.getText().toString();
                    sendVerification(phoneno);
                }


                String userName=user.getText().toString();
                String phoneno =phone.getText().toString();

              APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);

                Call<RegistrationResult> call=apiInterface.checkuser(userName,phoneno);
                call.enqueue(new Callback<RegistrationResult>() {
                    @Override
                    public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {

                        arrayList= (ArrayList<Registration>) response.body().getRegistration();
                        SharedPreferences preferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed=preferences.edit();
                        ed.putString("userid", arrayList.get(0).getUserId());
                        ed.apply();
                    }

                    @Override
                    public void onFailure(Call<RegistrationResult> call, Throwable t) {
                        Toast.makeText(ForgetPassword.this, "This User Done Not Exit, Create Accout", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        confirmotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(otptxt.getText().toString())){
                    Toast.makeText(ForgetPassword.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else{

                    verifyCode(otptxt.getText().toString());
                }
            }
        });




    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
                mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            final  String Code=phoneAuthCredential.getSmsCode();
            if(Code != null){
                otptxt.setText(Code);
                verifyCode(Code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ForgetPassword.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,code);
        signInWithCred(credential);
    }

    private void signInWithCred(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(ForgetPassword.this,ResetPassword.class);
                            intent.putExtra("uid",userid);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(ForgetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void sendVerification(String phone) {
        PhoneAuthOptions options=
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


}