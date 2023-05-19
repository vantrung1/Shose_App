package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fpt.shose_app.Adapter.imageAdapter;
import edu.fpt.shose_app.Adapter.sizeAdapter;
import edu.fpt.shose_app.Model.RatingModel;
import edu.fpt.shose_app.dialogModel.EventBus.ImageEvent;
import edu.fpt.shose_app.dialogModel.EventBus.SizeEvent;
import edu.fpt.shose_app.dialogModel.dialogProduct;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Product;
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
    private  TextView txtName,txtDesc,txtprice,txtsale,txtcontent,txtsaleTop,txtkho;
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
    TextView danhhia,soluogndanhgia,txt000;
    Float sodanhgia = Float.valueOf(0); ;
    Float saotrungbinh = Float.valueOf(0);
    String saotrungbinhs = "0";
    private RatingBar ratingBar;
    RatingModel ratingModel;
    FrameLayout frameLayout;
    NotificationBadge notificationBadge;
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
        getRating(product.getId()+"");
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
        danhhia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),RatingsActivity.class);
                i.putExtra("rating",ratingModel);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
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
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyCartActivity.class));
            }
        });
    }
    private void setsl() {
        notificationBadge.setText(Utils.cartLists.size()+"");
    }
    private void initiu() {
        frameLayout = findViewById(R.id.layoutGioHang_detail);
        notificationBadge = findViewById(R.id.menuSldt);
        txtName = findViewById(R.id.detailname);
        txtkho = findViewById(R.id.detailquantity);
        txtDesc = findViewById(R.id.detailDesc);
        txtprice = findViewById(R.id.detailprice);
        txt000 = findViewById(R.id.txt000);
        txtsale = findViewById(R.id.detailsale);
        txtsaleTop = findViewById(R.id.detailSaleTop);
        soluogndanhgia = findViewById(R.id.soluogndanhgia);
        ratingBar = findViewById(R.id.simpleRatingBar2);
        txtcontent = findViewById(R.id.detailcontent);
        imageViewdetail = findViewById(R.id.img_detail);
        recyImage = findViewById(R.id.recyImageDetail);
        recySize = findViewById(R.id.recySizeDetail);
        add_to_cart = findViewById(R.id.btn_addCart);
        danhhia = findViewById(R.id.danhhia);
        txtName.setText(product.getName());
        txtDesc.setText(product.getDesc());
        txtcontent.setText(product.getContent());
        if(product.getQuantity() == null){
            txtkho.setText("kho: 0");
        }
        else {
            txtkho.setText("kho:" +product.getQuantity());
        }


        txtprice.setText(new DecimalFormat("###,###,###").format(product.getPrice()));
        txtsale.setText(new DecimalFormat("###,###,###").format(product.getSale()));
        txtsaleTop.setText(new DecimalFormat("###,###,###").format(product.getSale()));
        Paint paint = txtprice.getPaint();
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        // Áp dụng đối tượng Paint vào TextView
        txtprice.setPaintFlags(paint.getFlags());
        Glide.with(getApplicationContext()).load(product.getImage().get(0).get("image1").getName()).placeholder(R.drawable.loading).into(imageViewdetail);
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
        setsl();
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
    protected void onResume() {
        super.onResume();
        setsl();
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

    } @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void sizeEvent(SizeEvent event) {
        if (event != null) {
           // Toast.makeText(this, "adsad", Toast.LENGTH_SHORT).show();
        }

    }
    private void getRating(String selected) {
        Call<RatingModel> objgetBrands = apiInterface.getRating(selected);
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<RatingModel>() {
            @Override
            public void onResponse(Call<RatingModel>call, Response<RatingModel >response) {
                if (response.isSuccessful()) {
                    ratingModel = response.body();
                    if(response.body().getData().size() == 0){
                        sodanhgia = Float.valueOf(0);
                        saotrungbinh = Float.valueOf(0);
                        danhhia.setVisibility(View.INVISIBLE);
                        soluogndanhgia.setVisibility(View.INVISIBLE);
                        ratingBar.setVisibility(View.INVISIBLE);
                        txt000.setVisibility(View.VISIBLE);
                        return;
                    }

                    Float tsl = Float.valueOf(0);
                    sodanhgia = Float.valueOf(response.body().getData().size());
                    for(RatingModel.rating rating:response.body().getData()){
                        tsl =tsl+ Integer.parseInt(rating.getStar());
                    }
                    saotrungbinh = tsl/sodanhgia;

                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    DecimalFormat decimalFormat2 = new DecimalFormat("#");
                    soluogndanhgia.setText("Số đánh giá: "+ decimalFormat.format(sodanhgia));
                    ratingBar.setRating(saotrungbinh);

                }
            }
            @Override
            public void onFailure(Call<RatingModel> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}