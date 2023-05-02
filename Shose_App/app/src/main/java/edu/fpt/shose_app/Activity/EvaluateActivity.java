package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.R;

public class EvaluateActivity extends AppCompatActivity {
    private Oder oder;
    Toolbar toolbar;
    RatingBar ratingBar;
    TextView txtFeedback, txtName, txtAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
//        oder = (Oder) getIntent().getSerializableExtra("evaluate");
        initUi();
    }

    private void initUi() {
        txtFeedback = findViewById(R.id.txt_feedback);
        txtName = findViewById(R.id.txt_namepr_evaluate);
        txtAttributes = findViewById(R.id.txt_attributes_evaluate);

        toolbar = findViewById(R.id.toolbar_evaluate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đánh giá sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //-------------
        setContentView(R.layout.activity_evaluate);
        ratingBar = findViewById(R.id.simpleRatingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v == 0 || v == 1) {
                    txtFeedback.setText("Tệ");
                } else if (v == 2) {
                    txtFeedback.setText("Không hài lòng");
                } else if (v == 3) {
                    txtFeedback.setText("Bình thường");
                } else if (v == 4) {
                    txtFeedback.setText("Hài lòng");
                } else if (v == 5) {
                    txtFeedback.setText("Tuyệt vời");
                }
            }
        });
    }
}