package edu.fpt.shose_app.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.github.dhaval2404.imagepicker.ImagePicker;

import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtEmail,txtPhone,txtPass,txtname;
    ImageView profile_image,camera_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initActionBar();
        initAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ProfileActivity.RESULT_OK) {
            Uri uri = data.getData();

            // Xử lý Uri của hình ảnh được chọn hoặc chụp
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    private void initAction() {
        camera_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(ProfileActivity.this)
                        .cropSquare()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initView() {
        toolbar  = findViewById(R.id.toolbar_profile);
        txtEmail = findViewById(R.id.profile_email);
        txtPhone = findViewById(R.id.profile_phone);
        txtPass = findViewById(R.id.profile_password);
        camera_image = findViewById(R.id.img_camera);

        txtname = findViewById(R.id.profile_name);
        profile_image = findViewById(R.id.profile_image);
        txtname.setText(Utils.Users_Utils.getName());
        if(Utils.Users_Utils.getPhoneNumber() == null){
            txtPhone.setText("Phone number not updated");
        }
        else if(Utils.Users_Utils.getPhoneNumber().equals("0")){
            txtPhone.setText("Phone number not updated");
        }
        else {
            txtPhone.setText(Utils.Users_Utils.getPhoneNumber());
        }
       // txtPass.setText(Utils.Users_Utils.getPassword());
        txtEmail.setText(Utils.Users_Utils.getEmail());
        Glide.with(ProfileActivity.this).load(Utils.Users_Utils.getAvatar()).placeholder(R.drawable.image_profile).into(profile_image);
    }
}