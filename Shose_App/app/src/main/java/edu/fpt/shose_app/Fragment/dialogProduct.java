package edu.fpt.shose_app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Activity.ProductDetailActivity;
import edu.fpt.shose_app.Adapter.sizeAdapter;
import edu.fpt.shose_app.EventBus.SizeEvent;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.R;

public class dialogProduct extends Dialog {
    List<SizeRequest.SizeQuantity> sizeQuantityList;
    sizeAdapter adapter;
   public int soluong = 1;
    TextView txt_quantity;
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe
    public void onProductEvent(SizeEvent event) {
        // Handle the event here
        if(event != null){

            for (SizeRequest.SizeQuantity sizeQuantity : sizeQuantityList){
                if(sizeQuantity.getSize().equals(adapter.getSelected())){
                    txt_quantity.setText(sizeQuantity.getQuantity());
                }
            }
        }
    }

    public dialogProduct(@NonNull Context context, Product product, sizeAdapter sizeAdapter,List<SizeRequest.SizeQuantity> sizeQuantityLists) {
        super(context);
        setContentView(R.layout.dialog_update_cart);
        setCancelable(false);
        sizeQuantityList = new ArrayList<>();
        sizeQuantityList = sizeQuantityLists;
        adapter = sizeAdapter;
        ImageView img_update_cart = findViewById(R.id.img_update_cart);
        ImageView img_close = findViewById(R.id.img_close);
        ImageView item_btnIMG_minus_cart = findViewById(R.id.dialog_btnIMG_minus_cart);
        ImageView item_btnIMG_plus_cart =findViewById(R.id.dialog_btnIMG_plus_cart);
        TextView txt_price = findViewById(R.id.txt_price);
        TextView txt_sale =findViewById(R.id.txt_sale);
         txt_quantity = findViewById(R.id.txt_quantity);
        TextView txt_quantity_cart_update = findViewById(R.id.dialog_txt_quantity_cart_update);

        RecyclerView recycler_dialog_color = findViewById(R.id.recycler_dialog_color);
        RecyclerView recycler_dialog_size = findViewById(R.id.recycler_dialog_size);

        AppCompatButton appCompatButton2 = findViewById(R.id.btn_xacnhan);
        //recy
        recycler_dialog_size.setAdapter(sizeAdapter);
        recycler_dialog_size.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        Glide.with(context).load(product.getImage().get(0).get("1").getName()).into(img_update_cart);
        txt_price.setText(new DecimalFormat("###,###,###,###").format(product.getPrice()));
        txt_price.setPaintFlags(txt_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txt_sale.setText(new DecimalFormat("###,###,###,###").format(product.getSale()));
        txt_quantity_cart_update.setText(soluong+"");
        txt_quantity.setText(sizeQuantityList.get(0).getQuantity());
        item_btnIMG_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soluong != 1){
                    soluong = soluong - 1;
                    txt_quantity_cart_update.setText(soluong+"");
                }
            }
        });
        item_btnIMG_plus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soluong = soluong +1 ;
                txt_quantity_cart_update.setText(soluong+"");
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });
    }
}
