package edu.fpt.shose_app.Retrofit;

import android.database.Observable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.UserRequest;
import edu.fpt.shose_app.Model.loginRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiApp {
    @POST("register")
    Call<User> postUser(@Body User objUser);

    @GET("brands")
    Call<ArrayList<Brand>> getAllBrand();
    @GET("products/branch/{id}")
    Call<ProductRequest> getApiProductById(@Path("id") int id);
    @GET("sizes/product/{id}")
    Call<SizeRequest> getQuantitySize(@Path("id") int id);
    @POST("login")
    @FormUrlEncoded
    Call<loginRequest> _logGin(@Field("email") String email,
                               @Field("password")String password);


    @POST("send-otp")
    @FormUrlEncoded
    Observable<UserRequest> _forgotpassword(@Field("email") String email);





    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

   ApiApp apiApp = new Retrofit.Builder()
           .baseUrl("https://shoseapp.click/")
           .addConverterFactory(GsonConverterFactory.create(gson))
           .build()
           .create(ApiApp.class);
//   @GET("api/users")
//   Call<User>changepass(@Query("id")int id,
//                        @Query("name") String name,
//                        @Query("address_id") String address_id,
//                        @Query("role_id") String role_id,
//                        @Query("phoneNumber") String phoneNumber,
//                        @Query("email") String email,
//                        @Query("password") String password,
//                        @Query("avatar") String avatar,
//                        @Query("token") String token,
//                        @Query("status") String status,
//                        @Query("created_at") String created_at,
//                        @Query("updated_at") String updated_at);

}


