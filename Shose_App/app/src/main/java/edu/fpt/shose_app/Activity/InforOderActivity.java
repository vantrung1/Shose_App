package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.fpt.shose_app.Adapter.Products_Oder_Adapter;
import edu.fpt.shose_app.Interface.ImageClickr;
import edu.fpt.shose_app.Model.FCMRequest;
import edu.fpt.shose_app.Model.NotiResponse;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Retrofit.PostNotifi;
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
    AppCompatButton btnChat, btnNhanHang;

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
        txt_UPdate = findViewById(R.id.txt_UPdate);
        txtTotal2 = findViewById(R.id.txt_total2);
        btnChat = findViewById(R.id.btn_chat_infor);
        btnNhanHang = findViewById(R.id.btnNhanHang);
        btnChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                getApplicationContext().startActivity(new Intent(getApplicationContext(),ChatBoxActivity.class));
            }
        });

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
        txt_UPdate.setText(oder.getTimeUTC()+"");
        txtAddress.setText(oder.getAddress_id());
        txtTotal.setText(new DecimalFormat("###,###,### VNĐ").format(Integer.parseInt(oder.getTotal())));
        txtTotal2.setText(new DecimalFormat("###,###,### VNĐ").format(Integer.parseInt(oder.getTotal())));
        if(oder.getStatus()==2){
            btnNhanHang.setVisibility(View.VISIBLE);
            btnNhanHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InforOderActivity.this);
                    builder.setTitle("THông báo xác nhận đơn hàng");
                    builder.setMessage("Bạn chắc chắn đã nhận được đơn hàng ?");
                    builder.setPositiveButton("Xác Nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            put_status_oder();

                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }
            });
        }else {
            btnNhanHang.setVisibility(View.GONE);
        }
    }
    public void put_status_oder() {
        Call<Oder> objPutOder = apiInterface.putOder(oder.getOder_id(), 3);
        objPutOder.enqueue(new Callback<Oder>() {

            @Override
            public void onResponse(Call<Oder> call, Response<Oder> response) {
                if (response.isSuccessful()) {
                    // Oder oder = response.body();
                    seNotification("admin",Utils.tokenadmin,"Thông báo đơn hàng","Đơn Hàng "+oder.getOder_id()+"Khách hàng đã nhận được hàng");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Oder> call, Throwable t) {

            }
        });
    }
    public void seNotification(String nguoinhan,String recipientToken, String title, String message){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostNotifi fcmService = retrofit.create(PostNotifi .class);
        FCMRequest fcmRequest = new FCMRequest();
        fcmRequest.setTo(recipientToken);
        FCMRequest.FCMNotification notification = new FCMRequest.FCMNotification();
        notification.setTitle(title);
        notification.setBody(message);
        fcmRequest.setNotification(notification);

        Call<NotiResponse> call = fcmService.sendNotification(fcmRequest);
        call.enqueue(new Callback<NotiResponse>() {
            @Override
            public void onResponse(Call<NotiResponse> call, Response<NotiResponse> response) {
                // Xử lý khi gửi thành công
                saveNotification(nguoinhan, recipientToken,  title,  message);

            }

            @Override
            public void onFailure(Call<NotiResponse> call, Throwable t) {
                // Xử lý khi gửi thất bại
            }
        });
    }
    public void saveNotification(String nguotnhan,String recipientToken, String title, String message) {
        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date currentDate = new Date();
        String dateString = dateFormat.format(currentDate);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");
        // Tạo đối tượng Notification
        FCMRequest fcmRequest = new FCMRequest();
        fcmRequest.setTo(recipientToken);
        FCMRequest.FCMNotification notification = new FCMRequest.FCMNotification();
        notification.setTitle(title);
        notification.setBody(message);
        fcmRequest.setNotification(notification);

        // Lưu thông báo vào Firebase Realtime Database trong nút con của người dùng
        databaseReference.child(nguotnhan).child(dateString).setValue(fcmRequest)
                .addOnSuccessListener(aVoid -> System.out.println("Successfully saved notification to Firebase"))
                .addOnFailureListener(e -> System.out.println("Failed to save notification to Firebase: " + e.getMessage()));


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