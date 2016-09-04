package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Nitrate_Report;

public class History_NitrateActivity_Item extends AppCompatActivity {

    private ImageView Image;
    private TextView Nitrate;
    private TextView Nitrite;
    private TextView Description;
    private TextView Name;
    private TextView Location;
    private TextView Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_nitrate);

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
        Nitrate_Report report = new Database(this).getNitrateReportById(id);
        Image = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load(report.image)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(Image);
        Nitrate = (TextView) findViewById(R.id.nitrate);
        Nitrate.setText("Nitrate: "+report.nitrate);
        Nitrite = (TextView) findViewById(R.id.nitrite);
        Nitrite.setText("Nitrite: "+report.nitrite);
        Description = (TextView) findViewById(R.id.description);
        Description.setText(report.description);
        Name = (TextView) findViewById(R.id.name);
        Name.setText("Name: "+report.name);
        Location = (TextView) findViewById(R.id.location);
        Location.setText("Location: "+report.location);
        Date = (TextView) findViewById(R.id.date);
        Date.setText("Date: "+report.date);
    }
}