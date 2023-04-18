package edu.fpt.shose_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;

import edu.fpt.shose_app.R;

public class Check_Out_MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    TextView txt_total_check_out,txt_price_total_check_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_main);
        initUi();

        Intent intent = getIntent();
        String data_price = intent.getStringExtra("STRING_DATA");
        txt_total_check_out = findViewById(R.id.txt_price_subtotal_check_out);
        txt_price_total_check_out = findViewById(R.id.txt_price_total_check_out);
        txt_total_check_out.setText(data_price);
        txt_price_total_check_out.setText(data_price);


        String[] type = new String[]{"cash on delivery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,R.layout.drop_down_item,type);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.filled_exposed);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Check_Out_MainActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initUi(){
        toolbar = findViewById(R.id.toolbar_check_out_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");
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