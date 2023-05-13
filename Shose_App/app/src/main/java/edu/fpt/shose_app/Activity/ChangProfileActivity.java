package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangProfileActivity extends AppCompatActivity {
    private EditText edthovaten,edtEmail;
    AppCompatButton btn;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_profile);
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        initView();

    }

    private void initView() {
        edthovaten = findViewById(R.id.edoldten);
        edthovaten.setText(Utils.Users_Utils.getName());

        btn = findViewById(R.id.btn_thaydoi2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName()) {
                    return;
                }
                thaydoi();
            }
        });
    }

    private void thaydoi() {
        Utils.Users_Utils.setName(edthovaten.getText().toString());
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

    private boolean validateUserName() {
        String username = edthovaten.getText().toString();
        String val = "[a-zA-Z\\s]+";
        if (username.isEmpty()) {
            edthovaten.setError("Field can't be empty");
            return false;
        } else if (username.length() >= 50) {
            edthovaten.setError("UserName to Long");
            return false;
        } else {
            edthovaten.setError(null);
            return true;
        }
    }

}