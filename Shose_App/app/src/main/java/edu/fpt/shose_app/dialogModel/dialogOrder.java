package edu.fpt.shose_app.dialogModel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import edu.fpt.shose_app.Activity.HomeActivity;
import edu.fpt.shose_app.R;

public class dialogOrder extends Dialog {
    public dialogOrder(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog);
        setCancelable(false);
        AppCompatButton appCompatButton = findViewById(R.id.btn_back_home);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HomeActivity.class);
                context.startActivity(i);
            }
        });
    }
}
