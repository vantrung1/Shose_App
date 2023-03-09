package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.fpt.shose_app.R;

public class SignUpActivity extends AppCompatActivity {
    TextView Signin;
    AppCompatButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        initActino();
    }

    private void initUI() {
        Signin = findViewById(R.id.signIn);
        btnSignIn = findViewById(R.id.btn_create);
    }

    private void initActino() {
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }
}