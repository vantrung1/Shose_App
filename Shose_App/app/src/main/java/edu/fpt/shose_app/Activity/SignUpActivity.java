package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    TextView Signin;
    TextInputLayout txt1, txt2, txt3, txt4;
    TextInputEditText edUsername, edEmail, edpassword, edconfirmpassword;
    AppCompatButton btnSignUp;
    ApiApp apiInterface;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        initActino();
    }

    private void initUI() {
        txt1 = findViewById(R.id.textInputLayout1);
        txt2 = findViewById(R.id.textInputLayout2);
        txt3 = findViewById(R.id.textInputLayout3);
        txt4 = findViewById(R.id.textInputLayout4);

        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edpassword = findViewById(R.id.edpassword);
        edconfirmpassword = findViewById(R.id.edchangepassword);

        Signin = findViewById(R.id.signIn);
        btnSignUp = findViewById(R.id.btn_create);
    }

    private void initActino() {
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
//                startActivity(i);
                if (!validateUserName() | !validateEmail() | !validatePass() | !validateConfirmPass()) {
                    return;
                }


                POST_Retrofit_User();
            }
        });
    }

    //Post api len sever
    void POST_Retrofit_User() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(edEmail.getText().toString(),edpassword.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(user!= null){
                                Log.d("TAG", "onComplete: "+user.getUid());
                                Gson gson = new GsonBuilder().setLenient().create();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(Utils.BASE_URL_API)
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                        .build();
                                apiInterface = retrofit.create(ApiApp.class);
                                // tạo đối tượng DTO để gửi lên server
                                User objUser = new User();
                                objUser.setName(edUsername.getText().toString());
                                objUser.setEmail(edEmail.getText().toString());
                                objUser.setPassword(edpassword.getText().toString());
                                objUser.setFilebase_id(user.getUid());

                                Call<User> objCall = apiInterface.postUser(objUser);
                                objCall.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {

                                        if (response.isSuccessful()) {
                                            User user = response.body();
                                            Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "them khong thanh cong", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(SignUpActivity.this,"email đã tồn tại",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"email đã tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        // tạo đối tượng chuyển đổi

    }

    //validate
    private boolean validateUserName() {
        String username = txt1.getEditText().getText().toString();
        String val = "[a-zA-Z\\s]+";
        if (username.isEmpty()) {
            txt1.setError("Field can't be empty");
            return false;
        } else if (username.length() >= 20) {
            txt1.setError("UserName to Long");
            return false;
        } else if (!username.matches(val)) {
            txt1.requestFocus();
            txt1.setError("Enter only alphabetical character");
            return false;
        } else {
            txt1.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String email = txt2.getEditText().getText().toString();
        if (email.isEmpty()) {
            txt2.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt2.setError("Please enter a valid email anddress");
            return false;
        } else {
            txt2.setError(null);
            return true;
        }
    }

    private boolean validatePass() {
        String pass = txt3.getEditText().getText().toString().trim();
        String val =
                "(?=.*[a-zA-Z])" +
                        "(?=.*[@#$%^&+=])" +
                        "(?=\\S+$)" +
                        ".{4,}" +
                        "$";
        if (pass.isEmpty()) {
            txt3.setError("Field can't be empty");
            return false;
        } else if (!pass.matches(val)) {
            txt3.setError("Password is too weak");
            return false;
        } else {
            txt3.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPass() {
        String confirm_pass = txt4.getEditText().getText().toString().trim();
        if (confirm_pass.isEmpty()) {
            txt4.setError("Field can't be empty");
            return false;
        } else if (!confirm_pass.equals(edpassword.getText().toString())) {
            txt4.setError("Password do not mach");
            return false;
        } else {
            txt4.setError(null);
            return true;
        }
    }
}