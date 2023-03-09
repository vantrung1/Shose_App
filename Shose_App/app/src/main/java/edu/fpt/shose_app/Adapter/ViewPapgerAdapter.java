package edu.fpt.shose_app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.fpt.shose_app.Fragment.OnboardFragment1;
import edu.fpt.shose_app.Fragment.OnboardFragment2;
import edu.fpt.shose_app.Fragment.OnboardFragment3;

public class ViewPapgerAdapter extends FragmentStatePagerAdapter {
    public ViewPapgerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnboardFragment1();
            case 1:
                return new OnboardFragment2();
            case 2:
                return new OnboardFragment3();
            default:
                return new OnboardFragment1();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}
