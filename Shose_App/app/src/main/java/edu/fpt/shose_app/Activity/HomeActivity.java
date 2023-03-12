package edu.fpt.shose_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import edu.fpt.shose_app.R;


public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    NavigationView navigationView_home;
    DrawerLayout drawerLayout_home;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUi();
        initAction();
    }

    private void initAction() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        // Handle home button click
                        Log.d("TAG", "onNavigationItemSelected: Home");
                        return true;
                    case R.id.Favorite:
                        // Handle profile button click
                        Log.d("TAG", "onNavigationItemSelected: Favorite");
                        return true;
                    case R.id.Notifications:
                        // Handle settings button click
                        Log.d("TAG", "onNavigationItemSelected: Notifications");
                        return true;
                    case R.id.Profile:
                        // Handle settings button click
                        Log.d("TAG", "onNavigationItemSelected: Profile");
                        return true;
                }
                return false;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(HomeActivity.this, MyCartActivity.class);
                startActivity(i);
            }
        });
    }

    private void initUi() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        floatingActionButton = findViewById(R.id.fab_home);
        navigationView_home = findViewById(R.id.navigation);
        drawerLayout_home = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.iconmenu);


        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                drawerLayout_home.openDrawer (GravityCompat.START);
            }
        });
//        navigationView_home.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId())
//                {
//                    case R.id.home:
//
//                        break;
//                    case R.id.Notifications:
//
//                        break;
//
//                    default:
//                }
//                drawerLayout_home.closeDrawer(navigationView_home);
//                return false;
//            }
//        });
    }
}