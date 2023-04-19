package edu.fpt.shose_app.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.fpt.shose_app.Fragment.CancelFragment;
import edu.fpt.shose_app.Fragment.DeliveredFragment;
import edu.fpt.shose_app.Fragment.TransportFragment;
import edu.fpt.shose_app.Fragment.WaitForConfirmationFragment;
import edu.fpt.shose_app.Fragment.WaitingForGoodsFragment;

public class ViewPageOderAdapter extends FragmentStatePagerAdapter {
    public ViewPageOderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WaitForConfirmationFragment();
            case 1:
                return new TransportFragment();
            case 2:
                return new DeliveredFragment();
            case 3:
                return new CancelFragment();
            default:
                return new DeliveredFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Chờ xác nhận";
                break;
            case 1:
                title = "Đang giao";
                break;
            case 2:
                title = "Đã giao";
                break;
            case 3:
                title = "Đã hủy";
                break;
        }
        return title;
    }

}

