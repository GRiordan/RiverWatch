package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.ui.MainActivity;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;

import static com.vuw.project1.riverwatch.R.id.stripOverlay;

public class ResultsTabbedActivity extends AppCompatActivity {

    private InfoFragment infoFragment;
    private MapFragment mapFragment;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Double nitrate;
    private Double nitrite;
    private Bitmap left;
    private Bitmap right;
    private Bitmap middle;
    private FloatingActionButton fab;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_tabbed);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //TODO: create the two page fragments
        mapFragment = new MapFragment();
        infoFragment = new InfoFragment();

        Intent intent = getIntent();
        left = (Bitmap) intent.getParcelableExtra("left");
        right = (Bitmap) intent.getParcelableExtra("right");
        middle = (Bitmap) intent.getParcelableExtra("middle");
        imagePath = intent.getExtras().getString("image_path");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean saved = saveToDatabase();

                if(!saved){
                    Snackbar.make(view, "Saving to database failed, please fill out all sections", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    Intent intent = new Intent(ResultsTabbedActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_abort) {
            Intent intent = new Intent(ResultsTabbedActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * attempts to save a nitrate/ite result to the database
     * @return true if saved, false if something was not filled out correctly
     */
    public boolean saveToDatabase(){
        String info = infoFragment.getInfo();
        String name = infoFragment.getName();
        String location = "Placeholder";
        Double latitude = mapFragment.getLat();
        Double longitude = mapFragment.getLng();
        String date = new Date(System.currentTimeMillis()).toString();
        Database db = new Database(this);
        long id1 = db.saveNitrateReport(name, location, latitude, longitude, date, info, imagePath, nitrate, nitrite);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ResultsTabbedActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    private class AnalysisTask extends AsyncTask<String, Void, Bundle> {

        @Override
        protected Bundle doInBackground(String... params) {
            //process images
            Analysis analysis = new Analysis();
            analysis.processImages(left, middle, right, getApplicationContext());

            Bundle args = new Bundle();
            args.putDouble("nitrate", analysis.getNitrate());
            args.putDouble("nitrite", analysis.getNitrite());
            return args;
        }

        protected void onPostExecute(Bundle result) {
            infoFragment.setNitrateNitrite(result);
            infoFragment.setLayoutsVisible();

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                Bundle args = new Bundle();
                args.putInt("section_number", 1);
                args.putParcelable("left", left);
                args.putParcelable("right", right);
                args.putString("imgPath", imagePath);
                infoFragment.setArguments(args);
                new AnalysisTask().execute("temp");
                return infoFragment;
            }
            else {
                //return mapFragment
                Bundle args = new Bundle();
                args.putString("info_string", "This is where the map will be");
                mapFragment.setArguments(args);
                return mapFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Map";
            }
            return null;
        }
    }
}
