package com.ebrahimi2723.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class Social_media extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private  TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_media);


      viewPager = findViewById(R.id.viewPager);
      tabAdapter = new TabAdapter(getSupportFragmentManager());
      viewPager.setAdapter(tabAdapter);
      tabLayout = findViewById(R.id.tabLayout);
      tabLayout.setupWithViewPager(viewPager,false);

    }
}