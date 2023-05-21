package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.fpt.shose_app.Adapter.Notifi_adapter;
import edu.fpt.shose_app.Model.FCMRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;

public class Notification extends AppCompatActivity {

    Toolbar toolbar;
    ImageView img_toolbar;
    List<edu.fpt.shose_app.Model.Notification> notifications;
    Notifi_adapter notifi_adapter;
    RecyclerView recyclerView;
    TextView textView;
    private ProgressDialog progressDialog;
    private static final int SWIPE_DISTANCE_THRESHOLD = 100;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notifications = new ArrayList<>();
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Xử lý sự kiện vuốt màn hình
                if (e2.getY() - e1.getY() > SWIPE_DISTANCE_THRESHOLD) {
                    // Vuốt từ dưới lên
                    reloadActivity();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
        initUi();
        getNotification();
    }

    private void getNotification() {
        notifications.clear();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notifications");
        databaseReference.child(Utils.Users_Utils.getId()+"").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String timestap = childSnapshot.getKey();
                    FCMRequest fcmRequest = childSnapshot.getValue(FCMRequest.class);
                    if (fcmRequest != null) {
                        edu.fpt.shose_app.Model.Notification notification = new edu.fpt.shose_app.Model.Notification(timestap,fcmRequest);
                        notifications.add(notification);
                    }

                }
                Collections.reverse(notifications);
                notifi_adapter.setNotifiList(notifications);
                if(notifications.size() == 0){
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    textView.setVisibility(View.INVISIBLE);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                textView.setVisibility(View.VISIBLE);
                textView.setText("Đã có lỗi xảy ra , bạn hãy kiểm tra lại");
            }
        });
    }

    private void initUi(){
         textView = findViewById(R.id.txtakdks);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
        progressDialog.show();
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
        notifi_adapter = new Notifi_adapter(getApplicationContext(),notifications);
        recyclerView = findViewById(R.id.RecyclerView_Notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(notifi_adapter);
    }



    private void reloadActivity() {
        getNotification();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}