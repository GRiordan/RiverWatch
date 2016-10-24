package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.vuw.project1.riverwatch.R;

public class History_WaterActivity_Samples extends AppCompatActivity {

    long id;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        id = 0;
        latitude = 0;
        longitude = 0;
        if(extras != null) {
            id = extras.getLong("id", 0);
            latitude = extras.getDouble("latitude", 0);
            longitude = extras.getDouble("longitude", 0);
        }

        final String[] titles = new String[]{"Samples", "Map", "Graphs"};

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0:
                        return History_WaterActivityFragment_Samples.newInstance(id);
                    case 1:
                        return History_ActivityFragment_Map.newInstance(latitude, longitude);
                    case 2:
                        return History_WaterActivityFragment_Graph.newInstance(id);
                    default:
                        return History_WaterActivityFragment_Samples.newInstance(-1);
                }
            }

            @Override
            public CharSequence getPageTitle(int position){
                return titles[position];
            }

            @Override
            public int getCount() {
                return titles.length;
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
