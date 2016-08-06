package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.ui.MainActivity;
import com.vuw.project1.riverwatch.ui.NitrateActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NitrateResultsActivity extends AppCompatActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrate_results);
        Log.d(TAG, "Got to Intent");

        TextView nitrateView = (TextView) findViewById(R.id.nitrateTextView);
        TextView nitriteView = (TextView) findViewById(R.id.nitriteTextView);
        ImageView leftView = (ImageView) findViewById(R.id.nitrateImageView);
        ImageView rightView = (ImageView) findViewById(R.id.nitriteImageView);

        Intent intent = getIntent();
        final Double nitrate = intent.getExtras().getDouble("nitrate");
        final Double nitrite = intent.getExtras().getDouble("nitrite");

        // set image views to left and right square of strip
        Bitmap left = (Bitmap) intent.getParcelableExtra("left");
        Bitmap right = (Bitmap) intent.getParcelableExtra("right");
        leftView.setImageBitmap(left);
        rightView.setImageBitmap(right);

        // set text views to nitrite and nitrate levels
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        nitrateView.setText("Nitrate: " + df.format(nitrate));
        nitriteView.setText("Nitrite: " + df.format(nitrite));
        Button button = (Button) findViewById(R.id.finishButton);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = (EditText) findViewById(R.id.infoEditText);
                        String info = editText.getText().toString();
                        NitrateResult result = new NitrateResult(nitrate, nitrite, info);

                        //TODO: add code to add this result to history

                        Intent intent = new Intent(NitrateResultsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(NitrateResultsActivity.this, CameraActivity.class);
        startActivity(intent);
    }

}
