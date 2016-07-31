package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        final Double nitrate = intent.getExtras().getDouble("nitrate");
        final Double nitrite = intent.getExtras().getDouble("nitrite");
        Bitmap left = (Bitmap) intent.getParcelableExtra("left");
        Bitmap middle = (Bitmap) intent.getParcelableExtra("middle");
        Bitmap right = (Bitmap) intent.getParcelableExtra("right");
        Bitmap stripBitmap = (Bitmap) intent.getParcelableExtra("stripBitmap");

        ImageView leftView = (ImageView) findViewById(R.id.leftSection);
        ImageView middleView = (ImageView) findViewById(R.id.middleSection);
        ImageView rightView = (ImageView) findViewById(R.id.rightSection);

        leftView.setImageBitmap(left);
        middleView.setImageBitmap(middle);
        rightView.setImageBitmap(stripBitmap);

        nitrateView.setText("Nitrate: " + nitrate);
        nitriteView.setText("Nitrite: " + nitrite);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) findViewById(R.id.editText);
                        String info = editText.getText().toString();
                        NitrateResult result = new NitrateResult(nitrate, nitrite, info);
                        Intent intent = new Intent(NitrateResultsActivity.this, NitrateActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
