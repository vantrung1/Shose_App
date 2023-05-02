package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.UserRequest;
import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_pass);
        anhxa();
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

        Call<User> objCall = apiApp._forgotpassword(email);
        objCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "them khong thanh cong", Toast.LENGTH_LONG).show();

                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Error Sever", Toast.LENGTH_LONG).show();
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
