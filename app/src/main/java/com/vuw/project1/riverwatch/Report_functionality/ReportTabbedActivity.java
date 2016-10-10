package com.vuw.project1.riverwatch.Report_functionality;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ReportTabbedActivity.this, ReportActivity.class);
        startActivity(intent);
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
