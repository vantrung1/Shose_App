package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.fpt.shose_app.R;

public class Notification extends AppCompatActivity {

    Toolbar toolbar;
    ImageView img_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initUi();
    }
    private void initUi(){
        toolbar = findViewById(R.id.toolbar_notification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}