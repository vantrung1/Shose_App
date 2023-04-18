
        package edu.fpt.shose_app.Activity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.AppCompatButton;
        import androidx.appcompat.widget.Toolbar;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;


        import edu.fpt.shose_app.Model.Cart;

        import edu.fpt.shose_app.Model.products;
        import edu.fpt.shose_app.R;
        import edu.fpt.shose_app.Retrofit.ApiApp;
        import edu.fpt.shose_app.Utils.Utils;
        import edu.fpt.shose_app.zalo.Api.CreateOrder;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;
        import vn.zalopay.sdk.Environment;
        import vn.zalopay.sdk.ZaloPayError;
        import vn.zalopay.sdk.ZaloPaySDK;
        import vn.zalopay.sdk.listeners.PayOrderListener;


        public class Check_Out_MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    TextView txt_total_check_out,txt_price_total_check_out,emailcheck_out,phonecheckout,address_checkout;
    List<products> productsList_checkOut;
    AppCompatButton btn_payment;
    private String PaymentAmount = "cash on delivery";
    private String jsonprocuts;
    private int id_oder;
    String data_price,codezalo;
    AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_main);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        Intent intent = getIntent();


        initUi();
        initAction();



        data_price = intent.getStringExtra("STRING_DATA");
        txt_total_check_out = findViewById(R.id.txt_price_subtotal_check_out);
        txt_price_total_check_out = findViewById(R.id.txt_price_total_check_out);
        txt_total_check_out.setText(data_price);
        txt_price_total_check_out.setText(data_price);


        String[] type = new String[]{"cash on delivery","Zalo Pay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,R.layout.drop_down_item,type);
        autoCompleteTextView = findViewById(R.id.filled_exposed);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Check_Out_MainActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                PaymentAmount = autoCompleteTextView.getText().toString();
            }
        });
    }

    private void initAction() {
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG", "onClick: "+PaymentAmount);
                if(PaymentAmount.equalsIgnoreCase("Zalo Pay")){
                    CreateOrder orderApi = new CreateOrder();

                    try {
                        JSONObject data = orderApi.createOrder("1000000");
                        //  Log.d("Amount", txtAmount.getText().toString());
                        //lblZpTransToken.setVisibility(View.VISIBLE);
                        String code = data.getString("return_code");
                        Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                        if (code.equals("1")) {

                            //   codezalo = (data.getString("zp_trans_token"));
                            PayZalo(data.getString("zp_trans_token"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    CreateOder(Utils.Users_Utils.getId(), 1,02304302403, data_price,"note",PaymentAmount,"1",jsonprocuts,3);
                }

            }
        });

    }
    private void PayZalo(String codezalo){

        ZaloPaySDK.getInstance().payOrder(Check_Out_MainActivity.this, codezalo, "demozpdk://app", new PayOrderListener() {

            @Override
            public void onPaymentSucceeded(String s, String s1, String s2) {
                CreateOder(Utils.Users_Utils.getId(), 1,02304302403, data_price,"note",codezalo,"1",jsonprocuts,3);
                Intent intent = new Intent(Check_Out_MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onPaymentCanceled(String s, String s1) {

            }

            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

            }
        });
    }

    private void CreateOder(int user_id, int address_id, int number, String data_price, String note, String Payment, String status, String jsondata,int quantity) {

        Call<ApiApp.MyResponse> objgetBrands = apiInterface.create_oder(user_id, address_id, number, data_price, note, Payment, status, jsondata, quantity);
        // thực hiện gọi
        objgetBrands.enqueue(new Callback<ApiApp.MyResponse>() {
            @Override
            public void onResponse(Call<ApiApp.MyResponse> call, Response<ApiApp.MyResponse> response) {
                if(response.isSuccessful()){
                    ApiApp.MyResponse myResponse = response.body();
                    if(myResponse.getStatus().equals("success")){
                        id_oder = Integer.parseInt(myResponse.getId_oder());
                        Toast.makeText(Check_Out_MainActivity.this,myResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiApp.MyResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getLocalizedMessage());

            }
        });
    }

    private void initUi(){
        toolbar = findViewById(R.id.toolbar_check_out_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        btn_payment = findViewById(R.id.btn_Payment);
        emailcheck_out = findViewById(R.id.email_checkout);
        phonecheckout = findViewById(R.id.phone_checkout);
        address_checkout = findViewById(R.id.address_checkout);
        emailcheck_out.setText(Utils.Users_Utils.getEmail());
        if(Utils.Users_Utils.getPhoneNumber().equals("")){
            phonecheckout.setText("hay nhap sdt");
        }
        else {
            phonecheckout.setText(Utils.Users_Utils.getPhoneNumber());
        }

        // address_checkout.setText(Utils.Users_Utils.getEmail());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        productsList_checkOut = new ArrayList<>();
        for(Cart product : Utils.cartLists){
            if(product.isIscheck() == true){
                String attributes = product.getColor()+","+product.getSize();
                edu.fpt.shose_app.Model.products products1 =new products(product.getIdProduct(),product.getCartName(),product.getImage(),product.getQuantity(),product.getPrice(),product.getSale(),1,attributes);
                productsList_checkOut.add(products1);
            }
        }


        // Chuyển đối tượng JSONArray sang chuỗi JSON
        jsonprocuts = gson.toJson(productsList_checkOut);
        Log.d("TAG", "initUi: "+ jsonprocuts);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
