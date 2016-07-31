package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vuw.project1.riverwatch.R;

public class MainActivity extends AppCompatActivity {
    private Button reportButton;
    private Button nitrateButton;
    private Button bluetoothButton;
    private Button historyButton;
    private ImageView wainzLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Cool

        reportButton = (Button)findViewById(R.id.reportButton);
        nitrateButton = (Button)findViewById(R.id.nitrateButton);
        bluetoothButton = (Button)findViewById(R.id.bluetoothButton);
        historyButton = (Button)findViewById(R.id.historyButton);
        wainzLogo = (ImageView)findViewById(R.id.wainzLogo);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        nitrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NitrateActivity.class);
                startActivity(intent);
            }
        });

        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                startActivity(intent);
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        wainzLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wainz.org.nz/"));
                startActivity(browserIntent);
            }
        });
    }
}
