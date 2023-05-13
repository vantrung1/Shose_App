package edu.fpt.shose_app.Activity;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePassword_activity extends AppCompatActivity {
    private User user;
    private TextInputEditText edOldPass;
    private TextInputEditText edNewPass;
    private TextInputEditText edReNewPass;
    private TextInputLayout textInputLayout1;
    private TextInputLayout textInputLayout2;
    private TextInputLayout textInputLayout3;
    private AppCompatButton btnthay_doi;
    private ProgressBar progressBar;
    String userName,pass;
    private String oldPass, newPass, reNewPass;
    String URL = "https://shoseapp.click/api/users";
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        AnhXa();
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
         userName = preferences.getString("username","");
         pass = preferences.getString("pass", "1");
        btnthay_doi.setOnClickListener(view -> {
            if (validate()>0) {
                updateMatKhau(newPass);
            }
        });
    }

    public void AnhXa(){
//        String email = getIntent().getExtras().getString("email");
        edOldPass = findViewById(R.id.edoldpass);
        edNewPass = findViewById(R.id.edNewPass);
        edReNewPass = findViewById(R.id.edReNewPass);
        btnthay_doi = findViewById(R.id.btn_thaydoi);
        progressBar = findViewById(R.id.progressBar);
        textInputLayout1 = findViewById(R.id.textInputLayout1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout3 = findViewById(R.id.textInputLayout3);
        //
        user = new User();
//        loading = new Loading(this);

    }
    private void updateMatKhau(String newPass) {
        Utils.Users_Utils.setPassword(newPass);
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
    private  void Dialog(String mess){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_thong_bao, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.show();
        TextView tvThongbao = view.findViewById(R.id.tvMess);
        // dialog
        Button btnThongBao = view.findViewById(R.id.btnThongBao);
        tvThongbao.setText(mess);

        btnThongBao.setOnClickListener(view1 -> {
//            loading.DimissDialog();
            alertDialog.dismiss();
        });
    }
    private  void clearText (){
        edOldPass.setText("");
        edNewPass.setText("");
        edReNewPass.setText("");
        //
        btnthay_doi.setEnabled(false);
    }
    private int validate() {
        int check = 1;
        oldPass = Objects.requireNonNull(edOldPass.getText()).toString().trim();
        newPass = Objects.requireNonNull(edNewPass.getText()).toString().trim();
        reNewPass = Objects.requireNonNull(edReNewPass.getText()).toString().trim();
        //
        if (oldPass.length() == 0) {
            textInputLayout1.setError("Không để trống mật khẩu cũ!");
            edOldPass.requestFocus();
            check = -1;
            return check;
        } else {
            textInputLayout1.setError(null);
        }
        if (!oldPass.equals(pass)) {
            textInputLayout1.setError("Mật khẩu cũ không chính xác!");
            edOldPass.requestFocus();
            check = -1;
            return check;
        } else {
            textInputLayout1.setError(null);
        }
        if (newPass.length() == 0) {
            textInputLayout2.setError("Không để trống mật khẩu mới!");
            edNewPass.requestFocus();
            check = -1;
            return check;
        } else {
            textInputLayout2.setError(null);
        }
        if (reNewPass.length() == 0) {
            textInputLayout3.setError("Không để trống nhập lại mật khẩu!");
            edReNewPass.requestFocus();
            check = -1;
            return check;
        } else {
            textInputLayout3.setError(null);
        }
        if (!newPass.equals(reNewPass)) {
            textInputLayout3.setError("Nhập lại mật khẩu không trùng!");
            edReNewPass.requestFocus();
            check = -1;
            return check;
        } else {
            textInputLayout3.setError(null);
        }
        return check;
    }

}