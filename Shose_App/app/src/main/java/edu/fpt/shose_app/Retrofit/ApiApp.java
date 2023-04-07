package edu.fpt.shose_app.Retrofit;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.loginRequest;
import retrofit2.Call;
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

}
