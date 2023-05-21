package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hbb20.CountryCodePicker;

import java.util.List;

import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PutphoneActivity extends AppCompatActivity {
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    EditText ed_putPhonenumber;
    private CountryCodePicker ccp;
    private ImageView imgcheck;
    private ProgressDialog progressDialog;
    boolean ischeck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_putphone);
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        initUi();

        ccp.registerCarrierNumberEditText(ed_putPhonenumber);
        ed_putPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString().trim();
                if(input.length()>0){
                    imgcheck.setVisibility(View.VISIBLE);
                }else{
                    imgcheck.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_save, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            if(ischeck){
                updateUser();


            }
            else {
                Toast.makeText(getApplicationContext(), "Số Điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (!validatePhonenumber()) {
//            if (id == R.id.save) {
//                updateUser();
//            }
//        }else{
//            updateUser();
//            Intent intent =new Intent(this,Activity_profiles.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void initUi() {
        toolbar = findViewById(R.id.toolbar_putphonenumber);
        ccp = findViewById(R.id.ccp);
        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                if(isValidNumber) {
                    imgcheck.setImageResource(R.drawable.ic_valid);
                    ischeck = true;
                }else{
                    imgcheck.setImageResource(R.drawable.ic_invalid);
                    ischeck = false;
                }
            }
        });

        imgcheck = findViewById(R.id.img_check);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm số điện thoại ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PutphoneActivity.this,Activity_profiles.class));
            }
        });
        ed_putPhonenumber = findViewById(R.id.ed_putPhonenumber);
        ed_putPhonenumber.setText(Utils.Users_Utils.getPhoneNumber());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
    }

    //-------------
    private void updateUser() {
        String phonenew = "0"+ed_putPhonenumber.getText().toString();
        Log.d("TAG", "updateUser: "+("0"+ed_putPhonenumber.getText().toString()));
        Call<loginRequest> objCall = apiInterface._updateUserPhone(Utils.Users_Utils.getId(),phonenew.replaceAll(" ", ""));
        objCall.enqueue(new Callback<loginRequest>() {
            @Override
            public void onResponse(Call<loginRequest> call, Response<loginRequest> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("200")){
                        Utils.Users_Utils = response.body().getData();
                        Toast.makeText(getApplicationContext(),"Thay đổi thành công",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PutphoneActivity.this,Activity_profiles.class));
                    }
                    else {
                        Log.d("TAG", "sadsadsadasd: ");
                    }
                }
                else {
                    Log.d("TAG", "onResponse: "+12321312);
                }
            }

            @Override
            public void onFailure(Call<loginRequest> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
//    private boolean validatePhonenumber() {
//        String phonenumber = ed_putPhonenumber.getText().toString();
//        String val = "[a-zA-Z\\s]+";
//        if (phonenumber.isEmpty()) {
//            ed_putPhonenumber.setError("Bạn chưa nhập số điện thoại");
//            return false;
//        } else if (phonenumber.length() > 10) {
//            ed_putPhonenumber.setError("Số điện thoại không vượt quá 10 chữ số");
//            return false;
//        } else {
//            ed_putPhonenumber.setError(null);
//            return true;
//        }
//    }
}