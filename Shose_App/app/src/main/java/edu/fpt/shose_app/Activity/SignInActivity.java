package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.fpt.shose_app.R;

public class SignInActivity extends AppCompatActivity {
    TextView RecoveryPassword,singUP;
    AppCompatButton loginGmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUI();
        initAction();
    }

    private void initUI() {
        RecoveryPassword = findViewById(R.id.RecoveryPassword);
        singUP = findViewById(R.id.SignUp);
        loginGmail = findViewById(R.id.btnloginGmail);

    }
    private void initAction() {
        RecoveryPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SignInActivity.this, FogetPassActivity.class);
                startActivity(i);
            }
        });
        singUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        loginGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}