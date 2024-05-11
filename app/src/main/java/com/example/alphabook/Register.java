package com.example.alphabook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.connection.APIInterface;
import com.example.alphabook.api.connection.APPClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4,editText5;
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText1=findViewById(R.id.registerusername);
        editText2=findViewById(R.id.registeremail);
        editText3=findViewById(R.id.registerphoneno);
        editText4=findViewById(R.id.registerpassword);
        editText5=findViewById(R.id.registerrepassword);
        button=findViewById(R.id.registerbtn);
        textView=findViewById(R.id.loginregister);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        ProgressDialog progressDialog=new ProgressDialog(Register.this);
                        progressDialog.setMessage("Sing up");
                        progressDialog.show();

                        String user_name=editText1.getText().toString();
                        String email=editText2.getText().toString();
                        String phoneno=editText3.getText().toString();
                        String password=editText4.getText().toString();

                        APIInterface apiInterface= APPClient.getctient().create(APIInterface.class);
                        Call<RegistrationResult> call =apiInterface.Register_Data(user_name, email, phoneno,password);

                        call.enqueue(new Callback<RegistrationResult>() {
                            @Override
                            public void onResponse(Call<RegistrationResult> call, Response<RegistrationResult> response) {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Register.this,Login.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<RegistrationResult> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "DATA NOT SAVE", Toast.LENGTH_SHORT).show();

                            }

                        });


                    }

                });

//end
    }


    private void validation (){

//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        String uservali  =" [ a-z0-9_.-]  ";
//                //name validation
        if (editText1.length() > 35) {
            editText1.setError("pls enter less the 30 character in user name");
            return;
        }
        if (editText1.length() <5 ) {
            editText1.setError("pls enter grater than the 5 character in user name");
            return;
        }
        // email validaton
//        if(!editText2.getText().toString().matches(emailPattern)) {
//            editText2.setError("Please Enter Valid Email Address");
//            return ;
//        }
        //password validation
        if(TextUtils.isEmpty(editText4.getText().toString())){
            editText4.setError("this password field is emty");
            return;
        }
        String  pass= editText4.getText().toString().trim();
        String  repass= editText5.getText().toString().trim();
        if (!pass.equals(repass)){
            editText5.setText("password not matched");
        }

    }


}