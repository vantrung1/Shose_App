package edu.fpt.shose_app.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import edu.fpt.shose_app.Model.Loading;
import edu.fpt.shose_app.Model.User;
import edu.fpt.shose_app.R;

public class ChangePassword_activity extends AppCompatActivity {
    private User user;
    private TextInputEditText edOldPass;
    private TextInputEditText edNewPass;
    private TextInputEditText edReNewPass;
    private TextInputLayout textInputLayout1;
    private TextInputLayout textInputLayout2;
    private TextInputLayout textInputLayout3;
    private AppCompatButton btnthay_doi;

    private String oldPass, newPass, reNewPass;

    private Loading loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        AnhXa();
    }
    public void AnhXa(){
        edOldPass = findViewById(R.id.edoldpass);
        edNewPass = findViewById(R.id.edNewPass);
        edReNewPass = findViewById(R.id.edReNewPass);
        btnthay_doi = findViewById(R.id.btn_thaydoi);
        textInputLayout1 = findViewById(R.id.textInputLayout1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout3 = findViewById(R.id.textInputLayout3);
        //
        user = new User();
        loading = new Loading(this);
    }

}
