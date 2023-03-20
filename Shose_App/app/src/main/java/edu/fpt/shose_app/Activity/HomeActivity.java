package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.Brand_Adapter;
import edu.fpt.shose_app.Adapter.ProducAdapter2;
import edu.fpt.shose_app.Adapter.ProductAdapter;
import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerViewBrand,recy_1_product,recy_2_product;
    ArrayList<Brand> brandArrayList;
    ArrayList<Product> productArrayList;
    ArrayList<Product> productArrayList2;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView_home;
    DrawerLayout drawerLayout_home;
    Brand_Adapter brand_adapter;
    ProductAdapter productAdapter;
    ProducAdapter2 productAdapter2;
    Toolbar toolbar;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    private int currentPosition = 0;
    private Handler handler = new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        brandArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();
        productArrayList2 = new ArrayList<>();
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        brand_adapter = new Brand_Adapter(this,brandArrayList);
        productAdapter = new ProductAdapter(this,productArrayList);
        productAdapter2 = new ProducAdapter2(this,productArrayList2);
        initUi();
        initAction();
//        startAutoScroll();
//        stopAutoScroll();

    }

    private void initAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle home button click
                        Log.d("TAG", "onNavigationItemSelected: Home");
                        return true;
                    case R.id.Favorite:
                        // Handle profile button click
                        Log.d("TAG", "onNavigationItemSelected: Favorite");
                        return true;
                    case R.id.Notifications:
                        // Handle settings button click
                        Log.d("TAG", "onNavigationItemSelected: Notifications");
                        return true;
                    case R.id.Profile:
                        // Handle settings button click
                        Log.d("TAG", "onNavigationItemSelected: Profile");
                        return true;
                }
                return false;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomeActivity.this, MyCartActivity.class);
                startActivity(i);
            }
        });
    }

    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        floatingActionButton = findViewById(R.id.fab_home);
        navigationView_home = findViewById(R.id.navigation);
        drawerLayout_home = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.iconmenu);

        recyclerViewBrand = findViewById(R.id.RecyclerViewHome_brand);
        recy_1_product = findViewById(R.id.recy_1_product);
        recy_2_product = findViewById(R.id.recy_2_product);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBrand.setLayoutManager(layoutManager);
        recy_1_product.setLayoutManager(layoutManager2);
        recy_2_product.setLayoutManager(layoutManager3);

        recyclerViewBrand.setAdapter(brand_adapter);
        recy_1_product.setAdapter(productAdapter);
        recy_2_product.setAdapter(productAdapter2);


        getallBrand();


        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                drawerLayout_home.openDrawer (GravityCompat.START);
            }
        });
        navigationView_home.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnu_profile:

                        break;
                    case R.id.mnu_home:

                        break;
                    case R.id.mnu_cart:

                        break;
                    case R.id.mnu_favorite:

                        break;
                    case R.id.mnu_orders:

                        break;
                    case R.id.mnu_notifi:

                        break;
                    default:
                }
                drawerLayout_home.closeDrawer(navigationView_home);
                return false;
            }
        });
    }
    private void getallBrand(){
        Call<ArrayList<Brand>> objGetMOto = apiInterface.getAllBrand();
        // thực hiện gọi
        objGetMOto.enqueue(new Callback<ArrayList<Brand>>() {
            @Override
            public void onResponse(Call<ArrayList<Brand>> call, Response<ArrayList<Brand>> response) {
                if(response.isSuccessful()){
                    brandArrayList.clear();
                    brandArrayList = response.body();
                    brand_adapter.setBrandSelected(brandArrayList);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Brand>> call, Throwable t) {

            }
        });
    }
    private void startAutoScroll() {
        if (runnable == null) {
            runnable = new Runnable() {
                public void run() {
                    currentPosition++;
                    if (currentPosition == productAdapter2.getItemCount()) {
                        currentPosition = 0;
                    }
                    recy_2_product.smoothScrollToPosition(currentPosition);
                    handler.postDelayed(this, 2000); // 2 seconds delay
                }
            };
            handler.postDelayed(runnable, 2000); // 2 seconds delay
        }
    }

    private void stopAutoScroll() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
            runnable = null;
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App");
        builder.setMessage("Do You Want Exit App?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}