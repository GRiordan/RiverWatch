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
import com.vuw.project1.riverwatch.objects.Water_Report;

public class History_WaterActivity_Item extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView Image;
    private TextView Description;
    private TextView Name;
    private TextView Location;
    private TextView Date;
    private TextView Temperature;
    private TextView PH;
    private TextView Conductivity;
    private TextView Turbidity;
    private Water_Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_water);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        long id = 0;
//        double temperature = 0.0;
//        double pH = 0.0;
//        double conductivity = 0.0;
//        double turbidity = 0.0;
//        String description = "";
//        String image = "";
//        String name = "";
//        String location = "";
//        String date = "";
        if(extras != null){
            id = extras.getLong("id", 0);
//            temperature = extras.getDouble("temperature", 0.0);
//            pH = extras.getDouble("pH", 0.0);
//            conductivity = extras.getDouble("conductivity", 0.0);
//            turbidity = extras.getDouble("turbidity", 0.0);
//            description = extras.getString("description", "");
//            image = extras.getString("image", "");
//            name = extras.getString("name", "");
//            location = extras.getString("location", "");
//            date = extras.getString("date", "");
        }
        report = new Database(this).getWaterReportById(id);
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
        Location = (TextView) findViewById(R.id.location);
        Location.setText("Location: "+report.location);
        Date = (TextView) findViewById(R.id.date);
        Date.setText("Date: "+report.date);
        Temperature = (TextView) findViewById(R.id.temperature);
        Temperature.setText("Temperature: "+ report.temperature);
        PH = (TextView) findViewById(R.id.pH);
        PH.setText("pH: "+report.pH);
        Conductivity = (TextView) findViewById(R.id.conductivity);
        Conductivity.setText("Conductivity: "+report.conductivity);
        Turbidity = (TextView) findViewById(R.id.turbidity);
        Turbidity.setText("Turbidity: "+report.turbidity);

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
