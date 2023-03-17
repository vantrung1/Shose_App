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

import java.util.ArrayList;

import edu.fpt.shose_app.Adapter.MyCartAdapter;
import edu.fpt.shose_app.Model.Cart;
import edu.fpt.shose_app.R;

public class MyCartActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewCart;
    AppCompatButton appCompatButton;
    TextView txt_subtotal, txt_shopping, txt_total;
    ArrayList<Cart> arrayListCart;
    MyCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        initUi();
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
        recyclerViewCart = findViewById(R.id.recy_my_cart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyCartActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCart.setLayoutManager(layoutManager);

        txt_subtotal = findViewById(R.id.txt_price_subtotal);
        txt_shopping = findViewById(R.id.txt_price_shopping);
        txt_total = findViewById(R.id.txt_price_total);

        arrayListCart = new ArrayList<>();
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,40));
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,39));
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));
        arrayListCart.add(new Cart( "https://thumbs.dreamstime.com/b/blue-shoes-29507491.jpg", "Nike", 20000, 1,42));

        adapter = new MyCartAdapter(getApplicationContext(), arrayListCart);
        recyclerViewCart.setAdapter(adapter);
    }
}