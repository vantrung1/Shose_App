package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;

import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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

public class CancelOderBtnActivity extends AppCompatActivity implements ImageClickr {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Oder oder;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    TextView txtNameUser, txtPhone, txtAddress, txtPaymentAmount, txtTotal, txtCreateAt, txtTotal2;
    AppCompatButton btnChat, btnCancel;
    String selectedValue ="";
    RadioGroup radioGroup;

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
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplicationContext().startActivity(new Intent(getApplicationContext(),ChatBoxActivity.class));
            }
        });
        //-------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        products_oder_adapter = new Products_Oder_Adapter(this, oder.getProducts(),this);
        recyclerView.setAdapter(products_oder_adapter);
        txtNameUser.setText(oder.getName());
        txtPhone.setText(oder.getNumber());
        txtPaymentAmount.setText(oder.getPaymentAmount());
        txtCreateAt.setText(oder.getCreated_at());
        txtAddress.setText(oder.getAddress_id());
       // txtTotal2.setText(oder.getTotal());
        txtTotal.setText(new DecimalFormat("###,###,### VNĐ").format(Integer.parseInt(oder.getTotal())));
        txtTotal2.setText(new DecimalFormat("###,###,### VNĐ").format(Integer.parseInt(oder.getTotal())));
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
// Lấy đối tượng RadioGroup
        radioGroup =(RadioGroup) dialog.findViewById(R.id.radio_group);
// Lấy ID của RadioButton được chọn
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = group.findViewById(checkedId);
                selectedValue = selectedRadioButton.getText().toString();
              //  Log.d("TAG", "onCheckedChanged: "+selectedValue);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedValue.equals("")){
                    return;
                }
                else {
                   // Log.d("TAG", "Selected value: " + selectedValue);
                    put_status_oder(selectedValue);
                    dialog.dismiss();
                    Intent i = new Intent(getApplicationContext(), OderActivity.class);
                    startActivity(i);
                }
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void put_status_oder(String note) {
        Call<Oder> objPutOder = apiInterface.huydon(oder.getOder_id(), 4,note);
        objPutOder.enqueue(new Callback<Oder>() {

            @Override
            public void onResponse(Call<Oder> call, Response<Oder> response) {
                if (response.isSuccessful()) {
                   // Oder oder = response.body();

                    seNotification("admin",Utils.tokenadmin,"Thông báo đơn hàng","Đơn Hàng "+oder.getOder_id()+" đã được hủy bởi khách hàng");
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