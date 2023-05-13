package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.List;

import edu.fpt.shose_app.Adapter.Products_Oder_Adapter;
import edu.fpt.shose_app.Interface.ImageClickr;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InforOderActivity extends AppCompatActivity implements ImageClickr {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Oder oder;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    TextView txtNameUser, txtPhone, txtAddress, txtPaymentAmount, txtTotal, txtCreateAt, txtTotal2,txt_UPdate;
    AppCompatButton btnChat, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_oder);
        oder = (Oder) getIntent().getSerializableExtra("inforoder");
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        initUi();
    }
    private void initUi() {
        recyclerView = findViewById(R.id.recy_infor_oder);
        txtNameUser = findViewById(R.id.txt_name_users);
        txtPhone = findViewById(R.id.txt_phone_users);
        txtAddress = findViewById(R.id.txt_address_users);
        txtPaymentAmount = findViewById(R.id.txt_paymentAmount);
        txtTotal = findViewById(R.id.txt_total);
        txtCreateAt = findViewById(R.id.txt_createAt);
        txtCreateAt = findViewById(R.id.txt_UPdate);
        txtTotal2 = findViewById(R.id.txt_total2);
        btnChat = findViewById(R.id.btn_chat_infor);

        toolbar = findViewById(R.id.toolbar_inforoder);
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
        //--------------
        //-------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        products_oder_adapter = new Products_Oder_Adapter((Context) this, oder.getProducts(), (ImageClickr) this);
        recyclerView.setAdapter(products_oder_adapter);

        txtNameUser.setText(oder.getName());
        txtPhone.setText(oder.getNumber());
        txtPaymentAmount.setText(oder.getPaymentAmount());
        txtCreateAt.setText(oder.getTimeUTCCreate());
        txt_UPdate.setText(oder.getTimeUTC());
        txtAddress.setText(oder.getAddress_id());
        txtTotal.setText(new DecimalFormat("###,###,### VNĐ").format(Integer.parseInt(oder.getTotal())));
        txtTotal2.setText(new DecimalFormat("###,###,### VNĐ").format(Integer.parseInt(oder.getTotal())));
    }

    public void getProduct(int id){

        Call<List<Product>> objGetOder = apiInterface.getProduct(id);
        objGetOder.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    Product products = response.body().get(0);
                    Intent intent=new Intent(getApplicationContext(), ProductDetailActivity.class);
                    intent.putExtra("product",products);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    @Override
    public void onImageClick(int position) {
        getProduct(position);
    }
}