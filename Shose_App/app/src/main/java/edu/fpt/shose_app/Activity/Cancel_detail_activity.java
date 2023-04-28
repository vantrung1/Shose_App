package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.Adapter.Products_Oder_Adapter;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cancel_detail_activity extends AppCompatActivity {
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
        products_oder_adapter = new Products_Oder_Adapter(this, oder.getProducts());
        recyclerView.setAdapter(products_oder_adapter);
        //-------------
        txtUpdateAt.setText(oder.getUpdated_at());
        txtUpdateAt2.setText(oder.getUpdated_at());
        txtPaymentAmount.setText(oder.getPaymentAmount());
    }
}