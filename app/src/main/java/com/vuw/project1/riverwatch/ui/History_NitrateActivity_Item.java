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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_nitrate);

        Bundle extras = getIntent().getExtras();
        String name = "";
        if(extras != null){
            name = extras.getString("name", "");
        }
        Image = (ImageView) findViewById(R.id.image);
        Glide.with(this)
                .load("http://www.fullstop.com.au/HTMLfilesv2/060_SoilSolution/030_MonitoringNutrients/MonitoringNitrate_Images/020_MatchStripColour300.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(Image);
        Nitrate = (TextView) findViewById(R.id.nitrate);
        Nitrate.setText(name);
        Nitrite = (TextView) findViewById(R.id.nitrite);
        Nitrite.setText("test nitrite");
        Description = (TextView) findViewById(R.id.description);
        Description.setText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}
