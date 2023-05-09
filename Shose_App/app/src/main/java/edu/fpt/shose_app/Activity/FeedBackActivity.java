package edu.fpt.shose_app.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Adapter.FeedBackAdapter;
import edu.fpt.shose_app.Adapter.ImageFeedbackAdapter;
import edu.fpt.shose_app.Model.FeedBack;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.loginRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Const;
import edu.fpt.shose_app.Utils.RealPathUtil;
import edu.fpt.shose_app.Utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;


public class FeedBackActivity extends AppCompatActivity {
    private edu.fpt.shose_app.Adapter.FeedBackAdapter feedBackAdapter;
    private edu.fpt.shose_app.Adapter.ImageFeedbackAdapter imageFeedbackAdapter;
    private Oder oder;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    Toolbar toolbar;
    RatingBar ratingBar;
    TextView txtFeedback;
    EditText edCmt;
    AppCompatButton btnSend;
    RecyclerView recyclerView, recyclerView2;
    LinearLayout linear_photo, linear_photo_library;
    private static final int REQUEST_CODE = 1;
    private static final int Read_Permission = 101;
    private ArrayList<Uri> uriArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        oder = (Oder) getIntent().getSerializableExtra("evaluate");
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);

        initUi();
    }

    private void initUi() {
        recyclerView = findViewById(R.id.recy_feed_back);
        recyclerView2 = findViewById(R.id.recy2_feed_back);
        txtFeedback = findViewById(R.id.txt_feedback);
        linear_photo = findViewById(R.id.linear_photo);
        linear_photo_library = findViewById(R.id.linear_photo_library);
        edCmt = findViewById(R.id.ed_cmt);
        toolbar = findViewById(R.id.toolbar_evaluate);
        ratingBar = findViewById(R.id.simpleRatingBar);
        btnSend = findViewById(R.id.btn_send_evaluate);
        //-------------
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
        //-------------
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
        //------------
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        feedBackAdapter = new FeedBackAdapter(this, oder.getProducts());
        recyclerView.setAdapter(feedBackAdapter);
        //-------------
        imageFeedbackAdapter = new ImageFeedbackAdapter(getApplicationContext(), uriArrayList);
        recyclerView2.setLayoutManager(new GridLayoutManager(FeedBackActivity.this, 4));
        recyclerView2.setAdapter(imageFeedbackAdapter);

        if (recyclerView2 == null) {
            recyclerView2.setVisibility(View.GONE);
        } else {
            recyclerView2.setVisibility(View.VISIBLE);
        }
        if (ContextCompat.checkSelfPermission(FeedBackActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FeedBackActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Read_Permission);
            return;
        }
        //-------------
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v == 0) {
                    txtFeedback.setText("Rất tệ");
                } else if (v == 1) {
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
        //------------
        linear_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "thành công", Toast.LENGTH_LONG).show();
            }
        });
        //------------
        linear_photo_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImages();
            }
        });
        //------------
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri != null) {
                    post_FeedBack();
                }
            }
        });
    }

    public void post_FeedBack() {
        progressDialog.show();
        //--user_id---
        int user_id = Utils.Users_Utils.getId();
        RequestBody requestBodyUserID = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_id));
        //--product_id---
        int product_id = oder.getProducts().get(0).getProduct_id();
        RequestBody requestBodyProductID = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(product_id));
        //--star---
        int star = (int) ratingBar.getRating();
        RequestBody requestBodyStar = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(star));

//        String strRealPath = RealPathUtil.getRealPath(this, mUri);
//        File file = new File(strRealPath);
//        RequestBody requestBodyIMG = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part mPart = MultipartBody.Part.createFormData(Const.KEY_IMAGE, file.getName(), requestBodyIMG);
        //--content---
        String cmt = edCmt.getText().toString().trim();
        //--image---
        RequestBody requestBodyCMT = RequestBody.create(MediaType.parse("multipart/form-data"), cmt);
        List<MultipartBody.Part> images = new ArrayList<>();
        for (Uri uri : uriArrayList) {
            File files = new File(RealPathUtil.getRealPath(this, uri));
            RequestBody requestBodyIMGs = RequestBody.create(MediaType.parse("image/*"), files);
            MultipartBody.Part mPart2 = MultipartBody.Part.createFormData(Const.KEY_IMAGE, files.getName(), requestBodyIMGs);
            images.add(mPart2);
        }
        //------------
        Call<FeedBack> objFeedBack = apiInterface._feedBack(requestBodyUserID, requestBodyProductID, requestBodyStar, images, requestBodyCMT);
        objFeedBack.enqueue(new Callback<FeedBack>() {
            @Override
            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đánh giá thành công", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Đánh giá không thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FeedBack> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    //------------
    private void pickImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int x = data.getClipData().getItemCount();
                    for (int i = 0; i < x; i++) {
                        mUri = data.getClipData().getItemAt(i).getUri();
                        uriArrayList.add(mUri);

                }

               // imageFeedbackAdapter.notifyDataSetChanged();
            } else {

                    mUri = data.getData();
                    uriArrayList.add(mUri);

            }
            imageFeedbackAdapter.notifyDataSetChanged();
           // Toast.makeText(this, uriArrayList.size(), Toast.LENGTH_SHORT).show();
        }
    }
}