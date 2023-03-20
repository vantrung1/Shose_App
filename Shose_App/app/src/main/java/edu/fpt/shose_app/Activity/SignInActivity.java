package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.fpt.shose_app.MainActivity;
import edu.fpt.shose_app.Model.User;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initUI();
        initAction();
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
                POST_Retrofit_Login();
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

    //Post api len sever
    void POST_Retrofit_Login() {
        // tạo đối tượng chuyển đổi
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        // tạo đối tượng DTO để gửi lên server
        User objLogin = new User();
        objLogin.setEmail(edEmail.getText().toString());
        objLogin.setPassword(edpassword.getText().toString());

        Call<User> objCall = apiInterface.postLogin(objLogin);
        objCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User user = response.body();
                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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
