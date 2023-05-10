package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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

public class InforOderActivity extends AppCompatActivity {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Oder oder;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    TextView txtNameUser, txtPhone, txtAddress, txtPaymentAmount, txtTotal, txtCreateAt, txtTotal2;
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
        products_oder_adapter = new Products_Oder_Adapter(this, oder.getProducts());
        recyclerView.setAdapter(products_oder_adapter);

        txtNameUser.setText(oder.getName());
        txtPhone.setText(oder.getNumber());
        txtPaymentAmount.setText(oder.getPaymentAmount());
        txtCreateAt.setText(oder.getCreated_at());
        txtAddress.setText(oder.getAddress_id());
    }
}