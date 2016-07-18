package com.vuw.project1.riverwatch.Report_functionality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vuw.project1.riverwatch.R;

public class ReportFormActivity extends AppCompatActivity {
    private EditText description;
    private EditText extraDetails;
    private ImageView image;
    private Button submitButton;
    private String imagePath;

    //public static final String IMAGE_PATH = "extra photo path";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);
        description = (EditText) findViewById(R.id.submission_details_description);
        extraDetails = (EditText) findViewById(R.id.submission_details_extra_details);

        //setup the image
        if ((imagePath = getImagePath()) != null && !imagePath.equals("")) {
            final ImageView imageView = (ImageView) findViewById(R.id.submission_details_photo);
            float imgWeight = ((LinearLayout.LayoutParams) (imageView.getLayoutParams())).weight;
            ScreenDimensions dimensioner = new ScreenDimensions(getBaseContext());
            int imgHeight = (int) (dimensioner.getScreenHeight() * imgWeight);
            imageView.setImageBitmap(BitmapDisplay.decodeSampledBitmapFromPath(getImagePath(), imgHeight));
        } else {
            findViewById(R.id.submission_details_photo).setVisibility(View.GONE);
        }
    }

    public String getImagePath(){
        return getIntent().getExtras().getString("IMAGE_PATH");
    }
}
