package com.vuw.project1.riverwatch.Report_functionality;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.colour_algorithm.ResultsTabbedActivity;
import com.vuw.project1.riverwatch.ui.ReportActivity;

/**
 * Created by Alex on 10/10/2016.
 */
public class ReportTabbedActivity extends AppCompatActivity {
    private ReportInfoFragment infoFragment;
    private ReportMapFragment mapFragment;

    private ReportSectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private BasicLocation location;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_tabbed);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new ReportSectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mapFragment = new ReportMapFragment();
        infoFragment = new ReportInfoFragment();

        location = getLocation();
        imagePath = getImagePath();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ReportTabbedActivity.this, ReportActivity.class);
        startActivity(intent);

    }

    public String getImagePath(){
        return getIntent().getExtras().getString("IMAGE_PATH");
    }
    public BasicLocation getLocation(){
        double latitude = Double.valueOf(getIntent().getExtras().getString("LATITUDE"));
        double longitude = Double.valueOf(getIntent().getExtras().getString("LONGITUDE"));
        return new BasicLocation(latitude,longitude);
    }

    public class ReportSectionsPagerAdapter extends FragmentPagerAdapter {

        public ReportSectionsPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position){
            if(position == 0){
                return infoFragment;
            }else{
                return mapFragment;
            }
        }

        @Override
        public int getCount(){
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position){
            if(position == 0){
                return "Info";
            }else{
                return "Map";
            }
        }
    }
}
