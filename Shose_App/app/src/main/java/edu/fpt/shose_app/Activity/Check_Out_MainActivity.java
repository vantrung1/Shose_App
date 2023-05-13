
        package edu.fpt.shose_app.Activity;

        import android.annotation.SuppressLint;
        import android.app.Dialog;
      //  import android.app.Notification;
        import android.app.NotificationManager;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.text.InputType;
        import android.text.TextUtils;
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
        //import com.google.firebase.messaging.Notification;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.AppCompatButton;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.app.NotificationCompat;
        import androidx.core.app.NotificationManagerCompat;

        import com.google.android.material.imageview.ShapeableImageView;
        import com.google.firebase.auth.FirebaseUser;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;


        import edu.fpt.shose_app.Model.Cart;

        import edu.fpt.shose_app.Model.FCMRequest;
        import edu.fpt.shose_app.Model.NotiResponse;
        import edu.fpt.shose_app.Model.addRess_response;
        import edu.fpt.shose_app.Model.address;
        import edu.fpt.shose_app.Model.products;
        import edu.fpt.shose_app.Model.serverRepest;
        import edu.fpt.shose_app.R;
        import edu.fpt.shose_app.Retrofit.ApiApp;
        import edu.fpt.shose_app.Retrofit.PostNotifi;
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
    TextView txt_total_check_out;
            TextView txt_price_total_check_out;
            TextView emailcheck_out;
            TextView phonecheckout;
            TextView address_checkout;
    List<products> productsList_checkOut;
            List<String> address;
    AppCompatButton btn_payment;
    private String PaymentAmount = "Thanh toán khi nhận hàng";
    private String jsonprocuts;
    private String token_admin;
    private DatabaseReference databaseReference;
    private int soluong,id_oder;
    String data_price,codezalo,addressoder;
    List<address> addressList;
    AutoCompleteTextView autoCompleteTextView,autoCompleteAdress;
    ShapeableImageView img_update_phone,img_update_mail;
            ArrayAdapter<String> adapter2;
            ArrayAdapter<String> adapter;
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
        addressoder= "";
        databaseReference = FirebaseDatabase.getInstance().getReference("notifications");
        initUi();
        initAction();
        getTocken_admin();


        data_price = intent.getStringExtra("STRING_DATA");
        soluong = intent.getIntExtra("STRING_soluong",1);
        txt_total_check_out = findViewById(R.id.txt_price_subtotal_check_out);
        txt_price_total_check_out = findViewById(R.id.txt_price_total_check_out);
        txt_total_check_out.setText(data_price);
        txt_price_total_check_out.setText(data_price);


        String[] type = new String[]{"Thanh toán khi nhận hàng","Zalo Pay"};

         adapter = new ArrayAdapter<>(
                this,R.layout.drop_down_item,type);

        autoCompleteTextView = findViewById(R.id.filled_exposed);
        autoCompleteTextView.setText("Cash on delivery");
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
               // Toast.makeText(Check_Out_MainActivity.this, autoCompleteAdress.getText().toString(), Toast.LENGTH_SHORT).show();
                addressoder = autoCompleteAdress.getText().toString();
                if(addressoder .equals( "thêm mới địa chỉ")){
                    OpenDialogUpdateMail(Gravity.CENTER);
                    addressoder="";
                }
            }
        });
    }

    private void initAction() {
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG", "onClick: "+PaymentAmount);
                if(addressoder.equals("")||addressoder.equals("thêm mới địa chỉ")){
                    Toast.makeText(getApplicationContext(), "Chọn địa chỉ nhận hàng", Toast.LENGTH_LONG).show();
                    return;
                }
                if(PaymentAmount.equalsIgnoreCase("Zalo Pay")){
                    CreateOrder orderApi = new CreateOrder();

                    try {
                        JSONObject data = orderApi.createOrder(data_price);
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
                    CreateOder(Utils.Users_Utils.getId(), addressoder, phonecheckout.getText().toString(), data_price,"note",PaymentAmount,"1",jsonprocuts,soluong);
                }



            }
        });
        img_update_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogUpdatePhone(Gravity.CENTER);
            }
        });
        img_update_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private void PayZalo(String codezalo){

        ZaloPaySDK.getInstance().payOrder(Check_Out_MainActivity.this, codezalo, "demozpdk://app", new PayOrderListener() {

            @Override
            public void onPaymentSucceeded(String s, String s1, String s2) {
                CreateOder(Utils.Users_Utils.getId(), addressoder,phonecheckout.getText().toString(), data_price,"note",codezalo,"1",jsonprocuts,soluong);
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

    private void CreateOder(int user_id, String address_id, String number, String data_price, String note, String Payment, String status, String jsondata,int quantity) {

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
                        dialogOrder dialog = new dialogOrder(Check_Out_MainActivity.this);
                        dialog.show();
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.CENTER);
                      //  SendNotification();//--------------------Notification
                        seNotification(token_admin,"Thông báo đơn hàng","Bạn có đơn hàng mới");
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
        phonecheckout.setText("0"+Utils.Users_Utils.getPhoneNumber());
        // address
        autoCompleteAdress = findViewById(R.id.filled_exposed_address);
        img_update_phone = findViewById(R.id.img_update_phone);
        img_update_mail  = findViewById(R.id.img_update_mail);
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
        address = new ArrayList<>();
        Call<addRess_response> objgetaddress = apiInterface.getallAdess(Utils.Users_Utils.getId());
        address.add("thêm mới địa chỉ");
        objgetaddress.enqueue(new Callback<addRess_response>() {
            @Override
            public void onResponse(Call<addRess_response> call, Response<addRess_response> response) {
               if(response.isSuccessful()){
                   addRess_response addRess_response = response.body();
                   if(addRess_response.getStatus() == 202){
                        addressList = addRess_response.getData();

                       Log.d("TAG", "onResponse: "+addressList.size());
                        if(addressList.size()>0){

                            for(address abc : addressList){
                                address.add(abc.getDesc());
                            }


                             adapter2 = new ArrayAdapter<>(Check_Out_MainActivity.this,R.layout.drop_down_item,address);
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
    private int getNotificationId(){
        return (int) new Date().getTime();
    }
    public void seNotification(String recipientToken, String title, String message){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostNotifi  fcmService = retrofit.create(PostNotifi .class);
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
                saveNotification( recipientToken,  title,  message);

            }

            @Override
            public void onFailure(Call<NotiResponse> call, Throwable t) {
                // Xử lý khi gửi thất bại
            }
        });
    }
            public void saveNotification(String recipientToken, String title, String message) {
                String timestamp = String.valueOf(System.currentTimeMillis());
                  // Tạo một key duy nhất cho thông báo

                databaseReference = FirebaseDatabase.getInstance().getReference("notifications");
                    // Tạo đối tượng Notification
                FCMRequest fcmRequest = new FCMRequest();
                fcmRequest.setTo(recipientToken);
                FCMRequest.FCMNotification notification = new FCMRequest.FCMNotification();
                notification.setTitle(title);
                notification.setBody(message);
                fcmRequest.setNotification(notification);

                    // Lưu thông báo vào Firebase Realtime Database trong nút con của người dùng
                    databaseReference.child("admin").child(timestamp).setValue(fcmRequest)
                            .addOnSuccessListener(aVoid -> System.out.println("Successfully saved notification to Firebase"))
                            .addOnFailureListener(e -> System.out.println("Failed to save notification to Firebase: " + e.getMessage()));

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

        edt_add_phone.setText(phonecheckout.getText().toString());
        btn_cancel_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_add_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matkhau = edt_add_phone.getText().toString();
                if (TextUtils.isEmpty(matkhau)){
                    edt_add_phone.setError("please add your phone");
                }
                if (matkhau.length() != 10 ){
                        edt_add_phone.setError("Please Enter valid phone number");
                        edt_add_phone.requestFocus();
                } else {
                phonecheckout.setText(edt_add_phone.getText().toString());
                dialog.dismiss();
            }

            }
        });

        dialog.show();
    }
    private void OpenDialogUpdateMail(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_custom_mail);

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

        EditText edt_add_mail = dialog.findViewById(R.id.edt_add_mail);
        Button btn_cancel_mail = dialog.findViewById(R.id.btn_cancel_mail);
        Button btn_add_mail = dialog.findViewById(R.id.btn_add_mail);

      //  edt_add_mail.setText(emailcheck_out.getText().toString());
        btn_cancel_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_add_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matkhau = edt_add_mail.getText().toString();
                if (TextUtils.isEmpty(matkhau)){
                    edt_add_mail.setError("please add your mail");
                }
                else {
                    Call<serverRepest> call = apiInterface.themdiachi(Utils.Users_Utils.getId(),matkhau );
                    call.enqueue(new Callback<serverRepest>() {
                        @Override
                        public void onResponse(Call<serverRepest> call, Response<serverRepest> response) {
                            // Xử lý khi gửi thành công
                            if(response.isSuccessful()){
                                address.add(matkhau);
                                adapter2.notifyDataSetChanged();
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onFailure(Call<serverRepest> call, Throwable t) {
                            // Xử lý khi gửi thất bại
                        }
                    });

                }
            }
        });
        dialog.show();
    }


            private void getTocken_admin(){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("tokens").child("admin");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại hay không
                if (dataSnapshot.exists()) {
                    // Lấy giá trị dữ liệu từ dataSnapshot
                     token_admin = dataSnapshot.getValue(String.class);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }
}
