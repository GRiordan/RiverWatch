package com.vuw.project1.riverwatch.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.vuw.project1.riverwatch.objects.Incident_Report;

public class History_IncidentActivity_Item extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView Image;
    private TextView Description;
    private TextView Name;
    private TextView Date;
    private Incident_Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_incident);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        long id = 0;
//        String description = "";
//        String image = "";
//        String name = "";
//        String location = "";
//        String date = "";
        if(extras != null){
            id = extras.getLong("id", 0);
//            description = extras.getString("description", "");
//            image = extras.getString("image", "");
//            name = extras.getString("name", "");
//            location = extras.getString("location", "");
//            date = extras.getString("date", "");
        }
        report = new Database(this).getIncidentReportById(id);
        Image = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load(report.image)
                .placeholder(null)
                .crossFade()
                .centerCrop()
                .into(Image);
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
        if(report != null) {
            map.addMarker(new MarkerOptions()
                .position(new LatLng(report.latitude, report.longitude))
                .title(report.name));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(report.latitude, report.longitude), 14));
        }
    }
}
