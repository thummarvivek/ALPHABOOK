package com.example.alphabook.api.connection;

import com.example.alphabook.api.Register.RegistrationResult;
import com.example.alphabook.api.books.AddBookResult;
import com.example.alphabook.api.category.CategoryResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface APIInterface {


    // this is method is register and login

    @FormUrlEncoded
    @POST("Registration/Registration.php")
    Call<RegistrationResult>Register_Data(@Field("user_name") String userName,
                                          @Field("email") String email,
                                          @Field("phoneno") String phoneno,
                                          @Field("password") String password);


    @FormUrlEncoded
    @POST("Registration/Login.php")
    Call<RegistrationResult>login(@Field("user_name") String userName,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("Registration/Update.php")
    Call<RegistrationResult>update(@Field("Name") String Name,
                                   @Field("address") String uaddress,
                                   @Field("pincode") String upincode,
                                   @Field("lat") String lat,
                                   @Field("log") String log,
                                   @Field("gender") String ugender,
                                   @Field("dob") String udob,
                                   @Field("social_link") String usolink,
                                   @Field("user_id") String uid);









    //in this method is get books


//home page data show
    @FormUrlEncoded
    @POST("Add_Book/Show.php")
    Call<AddBookResult>bookshow( @Field("user_id") String uid);

    @FormUrlEncoded
    @POST("Add_Book/Details.php")
    Call<AddBookResult>bookdetails( @Field("book_id") String book_id);


    @FormUrlEncoded
    @POST("Add_Book/UploadBook.php")
    Call<AddBookResult>uploadsellbook( @Field("book_category") String bcat,
                                       @Field("book_publisher") String bpub,
                                       @Field("book_edition") String bed,
                                       @Field("book_amount") String bam,
                                       @Field("user_id") String user,
                                       @Field("lat") String lat,
                                       @Field("log") String log,
                                       @Field("address") String add);




//category page data show

    @POST("Category/Show.php")
    Call<AddBookResult>categoryshow();


    @FormUrlEncoded
    @POST("Category/ShowAll.php")
    Call<AddBookResult>categoryshowall( @Field("book_category") String book_category);


    @GET("Category/ShowStream.php")
    Call<CategoryResult>showstream();


   //cart
   @FormUrlEncoded
   @POST("Cart/Insert.php")
   Call<AddBookResult>Addtocart(@Field("book_id") String bookid,
                       @Field ("user_id") String userid );


    @FormUrlEncoded
    @POST("Cart/Show.php")
    Call<AddBookResult>showcart(   @Field ("user_id") String userid );


    @FormUrlEncoded
    @POST("Cart/Remove.php")
    Call<AddBookResult>deletecart(    @Field ("user_id") String uid ,
                                      @Field ("cart_id") String cid,
                                      @Field ("book_id") String bid );



    //order

    @FormUrlEncoded
    @POST("Cart/ShowOrderCart.php")
    Call<AddBookResult>showordercart(   @Field ("user_id") String userid ,
                                        @Field ("cart_id") String cid );

    //show my cart order
    @FormUrlEncoded
    @POST("Order/MyOderShow.php")
    Call<AddBookResult>showmyorder(   @Field ("user_id") String userid  );


//show my books
    @FormUrlEncoded
    @POST("Add_Book/MyBooks.php")
    Call<AddBookResult>showmybook(   @Field ("user_id") String userid  );


    //profile update

    @Multipart
    @POST("ProfilePicture/UploadProfile.php")
    Call<RegistrationResult> uploadprofile (@Part MultipartBody.Part  profile_picture,
                                            @Part ("user_id") RequestBody uid);




    //show my feedback
    @FormUrlEncoded
    @POST("Feedback/insert.php")
    Call<RegistrationResult>insertfeedback(   @Field ("user_id") String userid  ,
                                              @Field("rate")String rate,
                                              @Field ("f_comment") String comment  );


    //payment
    @FormUrlEncoded
    @POST("Payment/payment.php")
    Call<AddBookResult> onlinepayment (   @Field ("user_id") String userid  ,
                                          @Field("book_id")String bookid,
                                          @Field ("cart_id") String cartid,
                                          @Field("payment_type") String  type ,
                                          @Field("payment_status") String status);

//    @FormUrlEncoded
//    @POST("Payment/cod.php")
//    Call<AddBookResult> codpayment (   @Field ("user_id") String userid  ,
//                                          @Field("book_id")String bookid,
//                                          @Field ("cart_id") String cartid  );

    //checkuser
    @FormUrlEncoded
    @POST("Registration/Getid.php")
    Call<RegistrationResult> checkuser(@Field("user_name") String username,
                                       @Field("phoneno") String phoneno);

    @FormUrlEncoded
    @POST("Registration/PasswordReset.php")
    Call<RegistrationResult> resetpassword(@Field("user_id") String userid,
                                       @Field("password") String password);

    //search
    @FormUrlEncoded
    @POST("Add_Book/Search.php")
    Call<AddBookResult> searchbook(@Field("book_title") String title);

    @FormUrlEncoded
    @POST("Category/search.php")
    Call<AddBookResult> searchcategorys(@Field("search") String search);


    @FormUrlEncoded
    @POST("Payment/createorder.php")
    Call<AddBookResult> updateorder(@Field("user_id")String uid,
                                    @Field("book_id") String bid,
                                    @Field("pay_id") String pid);


}
