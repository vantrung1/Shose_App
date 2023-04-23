
        package edu.fpt.shose_app.Activity;

        import android.annotation.SuppressLint;
        import android.app.Dialog;
        import android.app.Notification;
        import android.app.NotificationManager;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.AppCompatButton;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.app.NotificationCompat;
        import androidx.core.app.NotificationManagerCompat;

        import com.google.android.material.imageview.ShapeableImageView;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;


        import edu.fpt.shose_app.Model.Cart;

        import edu.fpt.shose_app.Model.addRess_response;
        import edu.fpt.shose_app.Model.address;
        import edu.fpt.shose_app.Model.products;
        import edu.fpt.shose_app.R;
        import edu.fpt.shose_app.Retrofit.ApiApp;
        import edu.fpt.shose_app.Utils.Utils;
        import edu.fpt.shose_app.dialogModel.dialogOrder;
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
            private static final int NOTIFICATION_ID = 1;
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
    List<address> addressList;
    AutoCompleteTextView autoCompleteTextView,autoCompleteAdress;
    ShapeableImageView img_update_phone;

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
        autoCompleteAdress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Check_Out_MainActivity.this, autoCompleteAdress.getText().toString(), Toast.LENGTH_SHORT).show();
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

                if(PaymentAmount.equalsIgnoreCase("cash on delivery")){
                    SendNotification();//--------------------Notification
                }

            }
        });
        img_update_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogUpdatePhone(Gravity.CENTER);
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
                     //   Toast.makeText(Check_Out_MainActivity.this,myResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        dialogOrder dialog = new dialogOrder(Check_Out_MainActivity.this);
                        dialog.show();
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.CENTER);
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
        //address_checkout = findViewById(R.id.address_checkout);
        emailcheck_out.setText(Utils.Users_Utils.getEmail());
        // address
        autoCompleteAdress = findViewById(R.id.filled_exposed_address);
        img_update_phone = findViewById(R.id.img_update_phone);

        /*if(Utils.Users_Utils.getPhoneNumber().equals("")){
            phonecheckout.setText("hay nhap sdt");
        }
        else {
            phonecheckout.setText(Utils.Users_Utils.getPhoneNumber());
        }*/

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
        getaddress();
    }

    private void getaddress() {
        addressList = new ArrayList<>();
        Call<addRess_response> objgetaddress = apiInterface.getallAdess(Utils.Users_Utils.getId());
        objgetaddress.enqueue(new Callback<addRess_response>() {
            @Override
            public void onResponse(Call<addRess_response> call, Response<addRess_response> response) {
               if(response.isSuccessful()){
                   addRess_response addRess_response = response.body();
                   if(addRess_response.getStatus() == 202){

                        addressList = addRess_response.getData();
                       List<String> address = new ArrayList<>();
                       address.add("add your new address");
                       Log.d("TAG", "onResponse: "+addressList.size());
                        if(addressList.size()>0){

                            for(address abc : addressList){
                                address.add(abc.getDesc());
                            }


                            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Check_Out_MainActivity.this,R.layout.drop_down_item,address);
                            autoCompleteAdress.setAdapter(adapter2);
                        }
                   }
               }
            }

            @Override
            public void onFailure(Call<addRess_response> call, Throwable t) {
                Log.d("TAG", "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }

            @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    @SuppressLint("MissingPermission")
    private void SendNotification(){
        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Order Success")
                .setContentText("Your order will be delivered in 3 to 5 days")
                .setSmallIcon(R.drawable.notifications_24)
                .setLargeIcon(bitmap)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationId(), notification);

        /*NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null){
            notificationManager.notify(getNotificationId(), notification);
        }*/
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }

    private void OpenDialogUpdatePhone(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_custom_phone);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click out off dialog
        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        EditText edt_add_phone = dialog.findViewById(R.id.edt_add_phone);
        Button btn_cancel_phone = dialog.findViewById(R.id.btn_cancel_phone);
        Button btn_add_phone = dialog.findViewById(R.id.btn_add_phone);

        btn_cancel_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
