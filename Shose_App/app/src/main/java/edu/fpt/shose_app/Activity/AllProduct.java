package edu.fpt.shose_app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.ProductAdapter;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ArrayList<Product> productArrayList;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allproduct_activity);
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        initUI();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar_allproduct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        productArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recy_allproduct);
        productAdapter = new ProductAdapter(AllProduct.this,productArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(AllProduct.this,2));
        recyclerView.setAdapter(productAdapter);
        getAllProduct();
        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getAllProduct() {
        Call<ArrayList<Product>> objgetBrands = apiInterface.getallProduct();
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()){
                    ArrayList<Product> productRequest = response.body();
                    //  productAdapter.setBrandSelected(productArrayList);
                    productAdapter.setBrandSelected(productRequest);
                    Log.d("TAG", "onResponsesads: "+productRequest.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}
