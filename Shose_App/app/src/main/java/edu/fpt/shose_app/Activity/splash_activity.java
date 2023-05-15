package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;

public class splash_activity extends AppCompatActivity {
    private boolean abc = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "1");
        getTocken_admin();
        if(username.equals("1")){
            abc = true;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //do something
                if(abc){
                    startActivity(new Intent(splash_activity.this, Onboard_Activity.class));
                }
                else {
                    startActivity(new Intent(splash_activity.this, SignInActivity.class));
                }
            }
        }, 3000 );//time in milisecond
    }
    private void getTocken_admin(){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("tokens").child("admin");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Kiểm tra xem dữ liệu có tồn tại hay không
                if (dataSnapshot.exists()) {
                    // Lấy giá trị dữ liệu từ dataSnapshot
                    Utils.tokenadmin = dataSnapshot.getValue(String.class);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }
}