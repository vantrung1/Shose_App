package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.FavouriteAdapter;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;

public class FavouriteActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_toolbar;
    TextView tvTitleFav;

    private RecyclerView recyclerView;
    private FavouriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        initView();
        initActionBar();
        recyclerView = findViewById(R.id.rcv_favourite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo GridLayoutManager với spanCount là 2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Thiết lập layout manager cho RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        // Thiết lập adapter cho RecyclerView
        recyclerView.setAdapter(adapter);

        ArrayList<Product> favouriteProducts = loadFavouriteProducts();
        if (favouriteProducts != null) {
            adapter = new FavouriteAdapter(this, favouriteProducts);
            recyclerView.setAdapter(adapter);
        }
    }

    private ArrayList<Product> loadFavouriteProducts() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("FAVOURITE_PRODUCTS", null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return gson.fromJson(json, type);
    }



    private void initView() {
        toolbar  = findViewById(R.id.toolbar_favourite);
       // img_toolbar = findViewById(R.id.img_toolbar);
        tvTitleFav = findViewById(R.id.tvTitleFav);

    }
    private void initActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite");

        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      //  img_toolbar.setBackgroundResource(R.drawable.background_item_my_cart);



    }
}