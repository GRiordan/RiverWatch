package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.colour_algorithm.CameraActivity;

public class NitrateActivity extends AppCompatActivity {
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrate);

        testButton = (Button)findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NitrateActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }
}
