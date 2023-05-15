package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private ProgressDialog progressDialog;

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (!validatePhonenumber()) {
            if (id == R.id.save) {
                updateUser();
            }
        }else{
            updateUser();
            Intent intent =new Intent(this,Activity_profiles.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUi() {
        toolbar = findViewById(R.id.toolbar_putphonenumber);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm số điện thoại ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ed_putPhonenumber  =findViewById(R.id.ed_putPhonenumber);
        ed_putPhonenumber.setText(Utils.Users_Utils.getPhoneNumber());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
    }
    //-------------
    private void updateUser(){

        Utils.Users_Utils.setPhoneNumber(ed_putPhonenumber.getText().toString());
        Call<loginRequest> objCall = apiInterface._updateUser(Utils.Users_Utils.getId(),Utils.Users_Utils);
        objCall.enqueue(new Callback<loginRequest>() {
            @Override
            public void onResponse(Call<loginRequest> call, Response<loginRequest> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Thay đổi thành công",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginRequest> call, Throwable t) {

            }
        });
    }
    private boolean validatePhonenumber() {
        String phonenumber = ed_putPhonenumber.getText().toString();
        String val = "[a-zA-Z\\s]+";
        if (phonenumber.isEmpty()) {
            ed_putPhonenumber.setError("Bạn chưa nhập số điện thoại");
            return false;
        } else if (phonenumber.length() > 10) {
            ed_putPhonenumber.setError("Số điện thoại không vượt quá 10 chữ số");
            return false;
        } else {
            ed_putPhonenumber.setError(null);
            return true;
        }
    }
}