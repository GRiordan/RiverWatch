package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vuw.project1.riverwatch.R;

public class HistoryActivity extends AppCompatActivity {

    private Button reportButton;
    private Button nitrateButton;
    private Button waterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        reportButton = (Button)findViewById(R.id.reportButton);
        nitrateButton = (Button)findViewById(R.id.nitrateButton);
        waterButton = (Button)findViewById(R.id.waterButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, History_IncidentActivity.class);
                startActivity(intent);
            }
        });

        nitrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, History_NitrateActivity.class);
                startActivity(intent);
            }
        });

        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, History_WaterActivity.class);
                startActivity(intent);
            }
        });
    }
}
