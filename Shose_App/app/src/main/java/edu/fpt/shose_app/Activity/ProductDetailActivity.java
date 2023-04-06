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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.Size;
import edu.fpt.shose_app.R;

public class ProductDetailActivity extends AppCompatActivity {
     private Product product;
    private  TextView txtName,txtDesc,txtprice,txtsale,txtcontent;
    private Toolbar toolbar;
    private RecyclerView recyImage,recySize;
    ImageView imageViewdetail;
    AppCompatButton add_to_cart;
    private edu.fpt.shose_app.Adapter.imageAdapter imageAdapter;
    private edu.fpt.shose_app.Adapter.sizeAdapter sizeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        product= (Product) getIntent().getSerializableExtra("product");
        initiu();
        initactionbar();
        initAction();
    }

    private void initAction() {
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ProductDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_update_cart);

                ImageView img_update_cart = dialog.findViewById(R.id.img_update_cart);
                ImageView img_close = dialog.findViewById(R.id.img_close);
                ImageView item_btnIMG_minus_cart = dialog.findViewById(R.id.item_btnIMG_minus_cart);
                ImageView item_btnIMG_plus_cart = dialog.findViewById(R.id.item_btnIMG_plus_cart);
                TextView txt_price = dialog.findViewById(R.id.txt_price);
                TextView txt_sale = dialog.findViewById(R.id.txt_sale);
                TextView txt_quantity = dialog.findViewById(R.id.txt_quantity);
                TextView txt_quantity_cart_update = dialog.findViewById(R.id.item_txt_quantity_cart_update);

                RecyclerView recycler_dialog_color = dialog.findViewById(R.id.recycler_dialog_color);
                RecyclerView recycler_dialog_size = dialog.findViewById(R.id.recycler_dialog_size);

                AppCompatButton appCompatButton2 = dialog.findViewById(R.id.btn_xacnhan);
                //recy
                recycler_dialog_size.setAdapter(sizeAdapter);
                recycler_dialog_size.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));

                Glide.with(ProductDetailActivity.this).load(product.getImage().get(0).get("1").getName()).into(img_update_cart);
                txt_price.setText(new DecimalFormat("###,###,###,###").format(product.getPrice()));
                txt_price.setPaintFlags(txt_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txt_sale.setText(new DecimalFormat("###,###,###,###").format(product.getSale()));
                txt_quantity_cart_update.setText("40");

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

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