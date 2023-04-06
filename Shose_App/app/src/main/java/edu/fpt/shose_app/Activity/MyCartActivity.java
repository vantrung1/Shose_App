package edu.fpt.shose_app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.MyCartAdapter;
import edu.fpt.shose_app.Model.Cart;
import edu.fpt.shose_app.EventBus.TotalEvent;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;

public class MyCartActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewCart;
    AppCompatButton appCompatButton;
    TextView txt_total, textToast;
    MyCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initUi();
        totalCartCost();
    }

    private void initUi() {
        appCompatButton = findViewById(R.id.btn_checkout);
        toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = txt_total.getText().toString();
                Intent intent = new Intent(MyCartActivity.this,Check_Out_MainActivity.class);
                intent.putExtra("STRING_DATA", price);
                startActivity(intent);
            }
        });
        recyclerViewCart = findViewById(R.id.recy_my_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager);

        txt_total = findViewById(R.id.txt_price_total);
        textToast = findViewById(R.id.textToast);

        Utils.cartLists = new ArrayList<>();
        Utils.cartLists.add(new Cart(1, "https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/giay-the-thao-nike-air-jordan-1-high-og-unc-university-blue-555088-134-575441-134-mau-xanh-trang-size-41-631998251b927-08092022142213.jpg", "Nike", 20000, 1, "Trang-xanh", 40,false));
        Utils.cartLists.add(new Cart(2, "https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/giay-the-thao-nike-air-jordan-1-high-og-unc-university-blue-555088-134-575441-134-mau-xanh-trang-size-41-631998251b927-08092022142213.jpg", "Nike", 20000, 1, "Trang-xanh", 40,false));
        Utils.cartLists.add(new Cart(3, "https://cdn.vuahanghieu.com/unsafe/0x900/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/giay-the-thao-nike-air-jordan-1-high-og-unc-university-blue-555088-134-575441-134-mau-xanh-trang-size-41-631998251b927-08092022142213.jpg", "Nike", 20000, 1, "Trang-xanh", 40,false));

        if (Utils.cartLists.size() == 0) {
            textToast.setText("Giỏ hàng trống");
            textToast.setVisibility(View.VISIBLE);
        } else {
            adapter = new MyCartAdapter(getApplicationContext(), Utils.cartLists);
            recyclerViewCart.setAdapter(adapter);

        }
    }

    private void totalCartCost() {
        long total = 0;
        for (int i = 0; i < Utils.cartLists.size(); i++) {
            if(Utils.cartLists.get(i).isIscheck()){
                total = total + (Utils.cartLists.get(i).getPrice() * Utils.cartLists.get(i).getQuantity());
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txt_total.setText(decimalFormat.format(total));

    }

    @Override
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
    public void eventToatl(TotalEvent event) {
        if (event != null) {
            totalCartCost();

            if (Utils.cartLists.size() == 0) {
                textToast.setVisibility(View.VISIBLE);
            }
        }

    }

}