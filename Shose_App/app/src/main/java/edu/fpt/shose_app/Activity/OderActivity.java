package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import edu.fpt.shose_app.Adapter.ViewPageOderAdapter;
import edu.fpt.shose_app.R;

public class OderActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ViewPageOderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        initUi();

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager mviewPager = findViewById(R.id.view_pager);

        adapter = new ViewPageOderAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mviewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(mviewPager);

    }

    private void initUi() {
        toolbar = findViewById(R.id.toolbar_oder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Oder");
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