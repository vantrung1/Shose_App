package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtEmail,txtPhone,txtPass,txtname;
    ImageView profile_image,camera_image;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         gson = new GsonBuilder().setLenient().create();

         retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.update) {
            Intent intent =new Intent(this,ChangePassword_activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ProfileActivity.RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Tạo tham chiếu đến Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference imageRef = storageRef.child("avatar_user/" + selectedImageUri.getLastPathSegment());
            progressDialog.show();
            // Tải ảnh lên Firebase Storage
            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Ảnh đã được tải lên thành công
                    // Lấy URL của ảnh từ taskSnapshot và sử dụng cho mục đích khác (nếu cần)
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            String imageUrl = downloadUri.toString();
                            // Xử lý URL của ảnh tải lên Firebase Storage
                            Utils.Users_Utils.setAvatar(imageUrl);
                            updateUser(Utils.Users_Utils.getId(),Utils.Users_Utils);//
                            Glide.with(ProfileActivity.this).load(imageUrl).placeholder(R.drawable.image_profile).into(profile_image);

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Xử lý khi tải lên ảnh thất bại
                }
            });
            // Xử lý Uri của hình ảnh được chọn hoặc chụp
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }
    private void updateUser(int id , User av){

        Call<loginRequest> objCall = apiInterface._updateUser(id,av);
        objCall.enqueue(new Callback<loginRequest>() {
            @Override
            public void onResponse(Call<loginRequest> call, Response<loginRequest> response) {

                if (response.isSuccessful()) {
                    loginRequest loginRequest = response.body();
                    if(loginRequest.getStatus().equals("202")){
                       // Utils.Users_Utils = loginRequest.getData();
                        Toast.makeText(getApplicationContext(), loginRequest.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), loginRequest.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Thay doi khong thanh cong", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<loginRequest> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Sever", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
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