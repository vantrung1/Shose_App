package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fpt.shose_app.Adapter.imageAdapter;
import edu.fpt.shose_app.Adapter.sizeAdapter;
import edu.fpt.shose_app.EventBus.BrandEvent;
import edu.fpt.shose_app.EventBus.ImageEvent;
import edu.fpt.shose_app.EventBus.SizeEvent;
import edu.fpt.shose_app.Fragment.dialogProduct;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
import edu.fpt.shose_app.Model.Size;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailActivity extends AppCompatActivity {
     private Product product;
    private  TextView txtName,txtDesc,txtprice,txtsale,txtcontent,txtsaleTop;
    private Toolbar toolbar;
    private RecyclerView recyImage,recySize;
    private int soluong = 1;
    ImageView imageViewdetail;
    AppCompatButton add_to_cart;
    private edu.fpt.shose_app.Adapter.imageAdapter imageAdapter;
    private edu.fpt.shose_app.Adapter.sizeAdapter sizeAdapter;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    List<SizeRequest.SizeQuantity> sizeQuantityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        product= (Product) getIntent().getSerializableExtra("product");
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        initiu();
        initactionbar();
        initAction();
        getQuantilySize(product.getId());
        sizeQuantityList = new ArrayList<>();
    }

    private void getQuantilySize(int id) {
        Call<SizeRequest> objgetBrands = apiInterface.getQuantitySize(id);
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<SizeRequest>() {
            @Override
            public void onResponse(Call<SizeRequest> call, Response<SizeRequest> response) {
                if(response.isSuccessful()){
                    SizeRequest sizeRequest = response.body();
                    sizeQuantityList = sizeRequest.getData();
                }
            }

            @Override
            public void onFailure(Call<SizeRequest> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void initAction() {

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogProduct dialog = new dialogProduct(ProductDetailActivity.this,product,sizeAdapter,sizeQuantityList);

                dialog.show();
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);

            }
        });
    }

    private void initactionbar() {
        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void initiu() {
        txtName = findViewById(R.id.detailname);
        txtDesc = findViewById(R.id.detailDesc);
        txtprice = findViewById(R.id.detailprice);
        txtsale = findViewById(R.id.detailsale);
        txtsaleTop = findViewById(R.id.detailSaleTop);
        txtcontent = findViewById(R.id.detailcontent);
        imageViewdetail = findViewById(R.id.img_detail);
        recyImage = findViewById(R.id.recyImageDetail);
        recySize = findViewById(R.id.recySizeDetail);
        add_to_cart = findViewById(R.id.btn_addCart);
        txtName.setText(product.getName());
        txtDesc.setText(product.getDesc());
        txtcontent.setText(product.getContent());
        txtprice.setText(new DecimalFormat("###,###,###").format(product.getPrice()));
        txtsale.setText(new DecimalFormat("###,###,###").format(product.getSale()));
        txtsaleTop.setText(new DecimalFormat("###,###,###").format(product.getSale()));
        Paint paint = txtprice.getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        // Áp dụng đối tượng Paint vào TextView
        txtprice.setPaintFlags(paint.getFlags());
        Glide.with(getApplicationContext()).load(product.getImage().get(0).get("1").getName()).placeholder(R.drawable.loading).into(imageViewdetail);
        //recy
        recyImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recySize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> imageUrls = new ArrayList<>();
        List<Size> sizeList = new ArrayList<>();

        for (Map<String, Image> imageMap : product.getImage()) {
            for (Map.Entry<String, Image> entry : imageMap.entrySet()) {
                Image image = entry.getValue();
                imageUrls.add(image.getName());
            }
        }
        for (Map<String, Size> sizeMap : product.getSize()) {
            for (Map.Entry<String, Size> entry : sizeMap.entrySet()) {
                Size size = entry.getValue();
                sizeList.add(size);
            }
        }

        imageAdapter = new imageAdapter(this, imageUrls);
        sizeAdapter = new sizeAdapter(this, sizeList);
        recyImage.setAdapter(imageAdapter);
        recySize.setAdapter(sizeAdapter);
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
    public void eventBrand(ImageEvent event) {
        if (event != null) {
            Glide.with(getApplicationContext()).load(imageAdapter.getSelected()).placeholder(R.drawable.loading).into(imageViewdetail);
        }

    }
}