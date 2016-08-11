package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;

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
        double nitrate = 0.0;
        double nitrite = 0.0;
        String description = "";
        String image = "";
        String name = "";
        String location = "";
        String date = "";
        if(extras != null){
            nitrate = extras.getDouble("nitrate", 0.0);
            nitrite = extras.getDouble("nitrite", 0.0);
            description = extras.getString("description", "");
            image = extras.getString("image", "");
            name = extras.getString("name", "");
            location = extras.getString("location", "");
            date = extras.getString("date", "");
        }
        Image = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(Image);
        Nitrate = (TextView) findViewById(R.id.nitrate);
        Nitrate.setText("Nitrate: "+nitrate);
        Nitrite = (TextView) findViewById(R.id.nitrite);
        Nitrite.setText("Nitrite: "+nitrite);
        Description = (TextView) findViewById(R.id.description);
        Description.setText(description);
        Name = (TextView) findViewById(R.id.name);
        Name.setText("Name: "+name);
        Location = (TextView) findViewById(R.id.location);
        Location.setText("Location: "+location);
        Date = (TextView) findViewById(R.id.date);
        Date.setText("Date: "+date);
    }
}
