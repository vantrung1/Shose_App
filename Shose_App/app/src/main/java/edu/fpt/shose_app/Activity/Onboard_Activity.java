package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.fpt.shose_app.Adapter.ViewPapgerAdapter;
import edu.fpt.shose_app.MainActivity;
import edu.fpt.shose_app.R;
import me.relex.circleindicator.CircleIndicator;

public class Onboard_Activity extends AppCompatActivity {
    Button btnOnboardNext;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    private ViewPapgerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        initUi();
        adapter = new ViewPapgerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void initUi() {
        btnOnboardNext = findViewById(R.id.btn_OnboardNext);
        viewPager = findViewById(R.id.viewPager);
        circleIndicator = findViewById(R.id.CircleIndicator);


        btnOnboardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() <2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    Log.d("TAG", "onClick: next");
                    btnOnboardNext.setText("Next");
                }
                if (viewPager.getCurrentItem() == 0) {
                    btnOnboardNext.setText("Get Started");
                }
                if (viewPager.getCurrentItem() ==2) {
                    Intent i =new Intent(Onboard_Activity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}