package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
        initcontrol();
    }
    private void initcontrol(){
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)) {
                    Toast.makeText(getApplicationContext(), "ban chua nhap email", Toast.LENGTH_SHORT).show();
                } else {
                    //resetpass on firebase
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().sendPasswordResetEmail(str_email)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"kiem tra email cua ban",Toast.LENGTH_LONG).show();

                                }
                                finish();
                            });
                }

            }
        });
    }


    private void anhxa(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiApp = retrofit.create(ApiApp.class);
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
