package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.MainActivity;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1000;
    TextInputLayout txt1, txt2;
    TextView RecoveryPassword, singUP;
    TextInputEditText edEmail, edpassword;
    AppCompatButton btnLogin, loginGmail;
    private GoogleSignInClient mGoogleSignInClient;
    ApiApp apiInterface;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_sign_in);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initUI();
        initAction();
        String username = sharedPreferences.getString("username", "1");
        String pass = sharedPreferences.getString("pass", "1");
        firebaseAuth = FirebaseAuth.getInstance();
        if(pass.equals("1")||username.equals("1")){
            return;
        }
        else {
            edEmail.setText(username);
            Log.d("TAG", "onCreate: "+username+pass);
            POST_Retrofit_Login(username,pass);
        }
    }

    private void initUI() {
        txt1 = findViewById(R.id.txtInputLayout1);
        txt2 = findViewById(R.id.txtInputLayout2);
        edEmail = findViewById(R.id.ed_Email);
        edpassword = findViewById(R.id.ed_password);

        RecoveryPassword = findViewById(R.id.RecoveryPassword);
        singUP = findViewById(R.id.SignUp);
        btnLogin = findViewById(R.id.btnLogin);
        loginGmail = findViewById(R.id.btnloginGmail);

    }

    private void initAction() {
        RecoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, FogetPassActivity.class);
                startActivity(i);
            }
        });
        singUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePass()) {
                    return;
                }
                loginFirebase(edEmail.getText().toString(),edpassword.getText().toString());

            }
        });
        loginGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignInGmail = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(SignInGmail, 1000);
            }
        });
    }

    private void loginFirebase(String email, String pass) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

// Đăng nhập bằng email và mật khẩu


        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Lấy thông tin người dùng hiện tại
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        // Kiểm tra nếu người dùng không null
                        if (currentUser != null) {
                            String uid = currentUser.getUid();
                            POST_Retrofit_Login(edEmail.getText().toString(),edpassword.getText().toString());
                        }
                    } else {
                        // Đăng nhập thất bại
                        Exception exception = task.getException();

                    }
                });
    }

    //Post api len sever
    void POST_Retrofit_Login(String emai, String pass) {
        // tạo đối tượng chuyển đổi
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        // tạo đối tượng DTO để gửi lên server


        Call<loginRequest> objCall = apiInterface._logGin(emai,pass);
        objCall.enqueue(new Callback<loginRequest>() {
            @Override
            public void onResponse(Call<loginRequest> call, Response<loginRequest> response) {

                if (response.isSuccessful()) {
                    loginRequest loginRequest = response.body();
                    if(loginRequest.getStatus().equals("202")){
                        Utils.Users_Utils = loginRequest.getData();
                        Toast.makeText(getApplicationContext(), loginRequest.getMessage(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(i);
                        Utils.Users_Utils.setPassword(pass);
                        editor.putString("username", emai);
                        editor.putString("pass", pass);
                        editor.apply();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), loginRequest.getMessage(), Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<loginRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Sever", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                firebaseAuthWithGoogle();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle() {
        finish();
        Intent i = new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(i);

    }

    private boolean validateEmail() {
        String email = txt1.getEditText().getText().toString();
        if (email.isEmpty()) {
            txt1.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt1.setError("Please enter a valid email anddress");
            return false;
        } else {
            txt1.setError(null);
            return true;
        }
    }

    private boolean validatePass() {
        String pass = txt2.getEditText().getText().toString().trim();
        if (pass.isEmpty()) {
            txt2.setError("Field can't be empty");
            return false;
        } else {
            txt2.setError(null);
            return true;
        }
    }
}