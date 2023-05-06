package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.Brand_Adapter;
import edu.fpt.shose_app.Adapter.ProducAdapter2;
import edu.fpt.shose_app.Adapter.ProductAdapter;
import edu.fpt.shose_app.Adapter.ProductAdapter3;
import edu.fpt.shose_app.EventBus.BrandEvent;
import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
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
    SearchView SearchViewHome;
    RecyclerView recyclerViewBrand,recy_1_product,recy_2_product,recy_3_product;
    ArrayList<Brand> brandArrayList;
    ArrayList<Product> productArrayList;
    ArrayList<Product> productArrayList3;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView_home;
    DrawerLayout drawerLayout_home;
    Brand_Adapter brand_adapter;
    ProductAdapter productAdapter;
    ProducAdapter2 productAdapter2;
    ProductAdapter3 productAdapter3;
    Toolbar toolbar;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    private int currentPosition = 0;
    private Handler handler = new Handler();
    private Runnable runnable;
    View header_view;
    TextView txta,textViewsee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        brandArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();
        productArrayList3 = new ArrayList<>();
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        brand_adapter = new Brand_Adapter(this,brandArrayList);
        productAdapter = new ProductAdapter(this,productArrayList);
        productAdapter2 = new ProducAdapter2(this,productArrayList);
        productAdapter3 = new ProductAdapter3(this,productArrayList);
        initUi();
        initAction();
        initHeader();

//        startAutoScroll();
//        stopAutoScroll();

    }

    private void get_product(int id) {
        Call<ProductRequest> objgetBrands = apiInterface.getApiProductById(id);
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<ProductRequest>() {
            @Override
            public void onResponse(Call<ProductRequest> call, Response<ProductRequest> response) {
                if(response.isSuccessful()){
                    ProductRequest productRequest = response.body();


                        productArrayList.clear();
                        productArrayList = productRequest.getProducts();
                        if(productArrayList.size() ==0){
                            txta.setVisibility(View.VISIBLE);
                        }
                        else {
                            txta.setVisibility(View.INVISIBLE);

                        }
                        productAdapter.setBrandSelected(productArrayList);
                        productAdapter2.setProdcut(productArrayList);
                        Log.d("TAG", "onResponse: "+productArrayList.size());


                }
            }

            @Override
            public void onFailure(Call<ProductRequest> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void initHeader() {
        ImageView imageView = header_view.findViewById(R.id.profile_image_header);
        TextView textView = header_view.findViewById(R.id.profile_Name_header);
        Glide.with(HomeActivity.this).load(Utils.Users_Utils.getAvatar()).placeholder(R.drawable.image_profile).into(imageView);
        textView.setText(Utils.Users_Utils.getName());
    }

    private void initAction() {
        SearchViewHome.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearch = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intentSearch);
            }
        });
        SearchViewHome.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Bắt sự kiện khi người dùng đóng SearchView ở đây
                SearchViewHome.clearFocus();
                return false;
            }
        });
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
                        Intent icc = new Intent(HomeActivity.this, FavouriteActivity.class);
                        startActivity(icc);
                        return true;
                    case R.id.Notifications:
                        Intent iccc = new Intent(HomeActivity.this,  Notification.class);
                        startActivity(iccc);
                        return true;
                    case R.id.Profile:
                        // Handle settings button click
                        Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(i);
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
        textViewsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,AllProduct.class));
            }
        });
    }

    private void initUi() {
        SearchViewHome = findViewById(R.id.SearchViewHome);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        txta = findViewById(R.id.txta);
        textViewsee = findViewById(R.id.seall);
        bottomNavigationView.setBackground(null);
        floatingActionButton = findViewById(R.id.fab_home);
        navigationView_home = findViewById(R.id.navigation);
        drawerLayout_home = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.iconmenu);
        header_view =navigationView_home.getHeaderView(0);
        recyclerViewBrand = findViewById(R.id.RecyclerViewHome_brand);
        recy_1_product = findViewById(R.id.recy_1_product);
        recy_2_product = findViewById(R.id.recy_2_product);
        recy_3_product = findViewById(R.id.recy_3_product);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewBrand.setLayoutManager(layoutManager);
        recy_1_product.setLayoutManager(layoutManager2);
        recy_3_product.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
        recy_2_product.setLayoutManager(layoutManager3);

        recyclerViewBrand.setAdapter(brand_adapter);
        recy_1_product.setAdapter(productAdapter);
        recy_2_product.setAdapter(productAdapter2);
        recy_3_product.setAdapter(productAdapter3);


        getallBrand();
        getAllProduct();


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
                        Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(i);
                        break;
                    case R.id.mnu_home:

                        break;
                    case R.id.mnu_cart:
                        Intent ics = new Intent(HomeActivity.this, MyCartActivity.class);
                        startActivity(ics);
                        break;
                    case R.id.mnu_Chat:
                        Intent icc = new Intent(HomeActivity.this, ChatBoxActivity.class);
                        startActivity(icc);
                        break;
                    case R.id.mnu_orders:
                        Intent i3 = new Intent(HomeActivity.this, OderActivity.class);
                        startActivity(i3);
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

    private void getAllProduct() {
        Call<ArrayList<Product>> objgetBrands = apiInterface.getallProduct();
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()){
                    ArrayList<Product> productRequest = response.body();




                  //  productAdapter.setBrandSelected(productArrayList);
                    productAdapter3.setProductArrayList(productRequest);
                    Log.d("TAG", "onResponsesads: "+productRequest.size());


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void getallBrand(){
        Call<ArrayList<Brand>> objgetBrands = apiInterface.getAllBrand();
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<ArrayList<Brand>>() {
            @Override
            public void onResponse(Call<ArrayList<Brand>> call, Response<ArrayList<Brand>> response) {
                if(response.isSuccessful()){
                    brandArrayList.clear();
                    brandArrayList = response.body();
                    brand_adapter.setBrandSelected(brandArrayList);
                    get_product(brandArrayList.get(0).getId());
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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventBrand(BrandEvent event) {
        if (event != null) {
            get_product(brand_adapter.getSelected().getId());
        }

    }
}