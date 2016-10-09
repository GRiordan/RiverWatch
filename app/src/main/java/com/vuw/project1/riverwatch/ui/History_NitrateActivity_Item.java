package com.vuw.project1.riverwatch.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Nitrate_Report;

public class History_NitrateActivity_Item extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView Image;
    private TextView Nitrate;
    private TextView Nitrite;
    private TextView Description;
    private TextView Name;
    private TextView Date;
    private Nitrate_Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_nitrate);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        long id = 0;
//        double nitrate = 0.0;
//        double nitrite = 0.0;
//        String description = "";
//        String image = "";
//        String name = "";
//        String location = "";
//        String date = "";
        if(extras != null){
            id = extras.getLong("id", 0);
//            nitrate = extras.getDouble("nitrate", 0.0);
//            nitrite = extras.getDouble("nitrite", 0.0);
//            description = extras.getString("description", "");
//            image = extras.getString("image", "");
//            name = extras.getString("name", "");
//            location = extras.getString("location", "");
//            date = extras.getString("date", "");
        }
        report = new Database(this).getNitrateReportById(id);
        Image = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load(report.image)
                .placeholder(null)
                .crossFade()
                .centerCrop()
                .into(Image);
        Nitrate = (TextView) findViewById(R.id.nitrate);
        Nitrate.setText("Nitrate: "+report.nitrate);
        Nitrite = (TextView) findViewById(R.id.nitrite);
        Nitrite.setText("Nitrite: "+report.nitrite);
        Description = (TextView) findViewById(R.id.description);
        Description.setText(report.description);
        Name = (TextView) findViewById(R.id.name);
        Name.setText("Name: "+report.name);
        setTitle(report.name);
        Date = (TextView) findViewById(R.id.date);
        Date.setText("Date: "+report.date);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
            .position(new LatLng(report.latitude, report.longitude))
            .title(report.name));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(report.latitude, report.longitude), 14));
    }
}
