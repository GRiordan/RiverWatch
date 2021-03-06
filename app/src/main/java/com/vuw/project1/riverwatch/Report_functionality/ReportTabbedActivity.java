//*H****************************************************************************
// FILENAME:	ResultsTabbedActivity.java
//
// DESCRIPTION:
//  The class that handles the events when a user finishes a incident report.
//
//  A list of names of copyright information is provided in the README
//
//    This file is part of RiverWatch.
//
//    RiverWatch is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    RiverWatch is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with RiverWatch.  If not, see <http://www.gnu.org/licenses/>.
//
// CHANGES:
// DATE			WHO	    DETAILS
// 20/11/1995	George	Added header.
//
//*H*

package com.vuw.project1.riverwatch.Report_functionality;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.colour_algorithm.ResultsTabbedActivity;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.service.App;
import com.vuw.project1.riverwatch.ui.MainActivity;
import com.vuw.project1.riverwatch.ui.ReportActivity;

import java.util.Date;

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
        infoFragment.setParent(this);
        location = getLocation();
        imagePath = getImagePath();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ReportTabbedActivity.this, ReportActivity.class);
        startActivity(intent);

    }

    public void submit(String date, String descriptionText, String extraDetailsText){
        //Save to history
        Database database = new Database(this);

        //TODO attemmpt to submit to website
        final NetworkInfo network = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(network != null && network.isConnected()){
            new App().getServiceBroker().sendReport(new IncidentReport(descriptionText, extraDetailsText, imagePath, location,date));
            database.saveIncidentReport(descriptionText, location.getLatitude(),location.getLongitude(),date,extraDetailsText,imagePath,1);
        }else{
            database.saveIncidentReport(descriptionText, location.getLatitude(),location.getLongitude(),date,extraDetailsText,imagePath,0);
        }
        //Finish up activity
        Toast.makeText(getBaseContext(),"thank you for your submission",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ReportTabbedActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
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
                Bundle args = new Bundle();
                args.putString("ImagePath",imagePath);
                infoFragment.setArguments(args);
                return infoFragment;
            }else{
                Bundle args = new Bundle();
                args.putDouble("Lat",location.getLatitude());
                args.putDouble("Lon", location.getLongitude());
                mapFragment.setArguments(args);
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
