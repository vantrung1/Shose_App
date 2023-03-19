package edu.fpt.shose_app.Retrofit;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiApp {

    @POST("register")
    Call<User> postUser(@Body User objUser);

    @GET("brands")
    Call<ArrayList<Brand>> getAllBrand();
}
