package com.ebrahimi2723.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter( FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tapPosition) {
        switch (tapPosition){
            case 0:
                return new ProfileTab();
            case 1:
                return  new UsersTap();
            case 2:
                return  new SharePictureTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int TapPosition) {
        switch (TapPosition){
            case 0:
                return "Profile";
            case 1:
                return  "Users";
            case 2:
                return "Share Picture";
            default:
                return null;
        }
    }
}
