package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fpt.shose_app.Adapter.Products_Oder_Adapter;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.OderRequest;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.Products_Oder;
import edu.fpt.shose_app.Model.Size;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import edu.fpt.shose_app.dialogModel.dialogProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CancelOderBtnActivity extends AppCompatActivity {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Oder oder;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    TextView txtNameUser, txtPhone, txtAddress, txtPaymentAmount, txtNameProduct, txtAttributes, txtQuantity, txtPrice, txtsale, txtTotal, txtCreateAt, txtTotal2;
    AppCompatButton btnChat, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_oder_btn);
        oder = (Oder) getIntent().getSerializableExtra("oderConfirm");
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        initUi();
    }

    private void initUi() {
        recyclerView = findViewById(R.id.recy_cancel_oder_btn);
        txtNameUser = findViewById(R.id.txt_name_users);
        txtPhone = findViewById(R.id.txt_phone_users);
        txtAddress = findViewById(R.id.txt_address_users);
        txtPaymentAmount = findViewById(R.id.txt_paymentAmount);
        txtTotal = findViewById(R.id.txt_total);
        txtCreateAt = findViewById(R.id.txt_createAt);
        txtTotal2 = findViewById(R.id.txt_total2);
        btnChat = findViewById(R.id.btn_chat);
        btnCancel = findViewById(R.id.btn_cancel);

        toolbar = findViewById(R.id.toolbar_cancel_oder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông tin đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //-------------
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cancel_oder();
            }
        });
        //-------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        products_oder_adapter = new Products_Oder_Adapter(this, oder.getProducts());
        recyclerView.setAdapter(products_oder_adapter);

        txtNameUser.setText(oder.getName());
        txtPhone.setText(oder.getNumber());
        txtPaymentAmount.setText(oder.getPaymentAmount());
        txtCreateAt.setText(oder.getCreated_at());
    }

    public void btn_cancel_oder() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_reason_cancel_oder);

        ImageView img = dialog.findViewById(R.id.img_close);
        RadioButton radioButton1 = dialog.findViewById(R.id.rd_reason_cancel1);
        RadioButton radioButton2 = dialog.findViewById(R.id.rd_reason_cancel2);
        RadioButton radioButton3 = dialog.findViewById(R.id.rd_reason_cancel3);
        RadioButton radioButton4 = dialog.findViewById(R.id.rd_reason_cancel4);
        RadioButton radioButton5 = dialog.findViewById(R.id.rd_reason_cancel5);
        RadioButton radioButton6 = dialog.findViewById(R.id.rd_reason_cancel6);
        AppCompatButton btn = dialog.findViewById(R.id.btn_submit);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_status_oder();
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), OderActivity.class);
                startActivity(i);

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void put_status_oder() {
        Call<Oder> objPutOder = apiInterface.putOder(oder.getId(), 4);

        objPutOder.enqueue(new Callback<Oder>() {

            @Override
            public void onResponse(Call<Oder> call, Response<Oder> response) {
                if (response.isSuccessful()) {
                    Oder oder = response.body();
                }
            }

            @Override
            public void onFailure(Call<Oder> call, Throwable t) {

            }
        });
    }
}