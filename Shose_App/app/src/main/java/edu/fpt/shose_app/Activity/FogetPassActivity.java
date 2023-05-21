package edu.fpt.shose_app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FogetPassActivity extends AppCompatActivity {
    TextInputLayout txt1;
    TextInputEditText email;
    Button btncontinue ;
    ApiApp apiApp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressBar progressBar ;
    Toolbar toolbar;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_pass);
        anhxa();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiApp = retrofit.create(ApiApp.class);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initcontrol(email.getText().toString());
            }
        });
    }
    void initcontrol(String email) {
        progressDialog.show();
        Call<User> objCall = apiApp._forgotpassword(email);
        objCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "vào lại gmail của bạn để nhận mã đăng nhập", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "them khong thanh cong", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Sever", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
//        objCall.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<UserRequest> response) {
//                if (response.isSuccessful()) {
//                    UserRequest userRequest = response.body();
//                    Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "them khong thanh cong", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserRequest> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Error Sever", Toast.LENGTH_LONG).show();
//       });
//
//   }




    private void anhxa(){
        toolbar = findViewById(R.id.toolbarabc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        txt1 = findViewById(R.id.textInputLayout1);
        email = findViewById(R.id.edEmail);
        btncontinue = findViewById(R.id.btncontinue);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}