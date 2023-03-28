package edu.fpt.shose_app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.MyCartAdapter;
import edu.fpt.shose_app.EventBus.TotalEvent;
import edu.fpt.shose_app.Model.Cart;
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
        textToast = findViewById(R.id.textToast);
        appCompatButton = findViewById(R.id.btn_checkout);
        toolbar = findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        recyclerViewCart = findViewById(R.id.recy_my_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager);

        txt_total = findViewById(R.id.txt_price_total);

        Utils.cartLists = new ArrayList<>();
        Utils.cartLists.add(new Cart("https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1, 40));
        Utils.cartLists.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,39));
        Utils.cartLists.add(new Cart("https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));
        Utils.cartLists.add(new Cart("https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1, 40));
        Utils.cartLists.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,39));
        Utils.cartLists.add(new Cart("https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));
        Utils.cartLists.add(new Cart("https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1, 40));
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
            total = total + (Utils.cartLists.get(i).getPrice() * Utils.cartLists.get(i).getQuantity());
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
        }
    }
}