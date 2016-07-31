package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.objects.Incident_Object;

import java.util.ArrayList;

public class History_IncidentActivity_Item extends AppCompatActivity {

    private ImageView Image;
    private TextView Description;
    private TextView Name;
    private TextView Location;
    private TextView Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_incident);

        Bundle extras = getIntent().getExtras();
        String description = "";
        String image = "";
        String name = "";
        String location = "";
        String date = "";
        if(extras != null){
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
