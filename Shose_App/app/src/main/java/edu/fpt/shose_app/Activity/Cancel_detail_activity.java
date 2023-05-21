package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class Cancel_detail_activity extends AppCompatActivity implements ImageClickr {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    TextView txtUpdateAt, txtRequestBy, txtUpdateAt2, txtReason, txtPaymentAmount;
    private Oder oder;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_detail);
        oder = (Oder) getIntent().getSerializableExtra("oder_cancel_detail");
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        initUi();
    }

    private void initUi() {
        recyclerView = findViewById(R.id.recy_cancel_detail_btn);
        txtUpdateAt = findViewById(R.id.txt_update_at);
        txtRequestBy = findViewById(R.id.txt_request_by);
        txtUpdateAt2 = findViewById(R.id.txt_update_at_2);
        txtReason = findViewById(R.id.txt_reason);
        txtPaymentAmount = findViewById(R.id.txt_paymentAmount);

        toolbar = findViewById(R.id.toolbar_cancel_oder_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết đơn hủy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //-------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        products_oder_adapter = new Products_Oder_Adapter(this, oder.getProducts(),this);
        recyclerView.setAdapter(products_oder_adapter);
        //-------------
        txtUpdateAt2.setText(oder.getUpdated_at());
        txtPaymentAmount.setText(oder.getPaymentAmount());
        txtReason.setText(oder.getNote());
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