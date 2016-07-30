package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;

public class History_WaterActivity_Item extends AppCompatActivity {

    private ImageView Image;
    private TextView Description;
    private TextView Name;
    private TextView Location;
    private TextView Date;
    private TextView Temperature;
    private TextView PH;
    private TextView Conductivity;
    private TextView Turbidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_water);

        Bundle extras = getIntent().getExtras();
        double temperature = 0.0;
        double pH = 0.0;
        double conductivity = 0.0;
        double turbidity = 0.0;
        String description = "";
        String image = "";
        String name = "";
        String location = "";
        String date = "";
        if(extras != null){
            temperature = extras.getDouble("temperature", 0.0);
            pH = extras.getDouble("pH", 0.0);
            conductivity = extras.getDouble("conductivity", 0.0);
            turbidity = extras.getDouble("turbidity", 0.0);
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
        Temperature = (TextView) findViewById(R.id.temperature);
        Temperature.setText("Temperature: "+ temperature);
        PH = (TextView) findViewById(R.id.pH);
        PH.setText("pH: "+pH);
        Conductivity = (TextView) findViewById(R.id.conductivity);
        Conductivity.setText("Conductivity: "+conductivity);
        Turbidity = (TextView) findViewById(R.id.turbidity);
        Turbidity.setText("Turbidity: "+turbidity);
    }
}
