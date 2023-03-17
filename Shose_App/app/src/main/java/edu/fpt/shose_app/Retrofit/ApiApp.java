package edu.fpt.shose_app.Retrofit;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Brand;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiApp {
    @GET("brands")
    Call<ArrayList<Brand>> getAllBrand();
}
