package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.ui.NitrateActivity;

public class NitrateResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrate_results);

        TextView nitrateView = (TextView) findViewById(R.id.textView);
        TextView nitriteView = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        Double nitrate = intent.getExtras().getDouble("nitrate");
        Double nitrite = intent.getExtras().getDouble("nitrite");

        nitrateView.setText("Nitrate: " + nitrate);
        nitriteView.setText("Nitrite: " + nitrite);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NitrateResultsActivity.this, NitrateActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
