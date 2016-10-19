package com.vuw.project1.riverwatch.Report_functionality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vuw.project1.riverwatch.R;

import java.util.Date;

/**
 * Created by Alex on 10/10/2016.
 */
public class ReportInfoFragment extends Fragment {
    private EditText description;
    private EditText extraDetails;
    private Button submitButton;
    private String imagePath;
    private ReportTabbedActivity parent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_info, container, false);

        description = (EditText) rootView.findViewById(R.id.submission_details_description);
        extraDetails = (EditText) rootView.findViewById(R.id.submission_details_extra_details);

        submitButton = (Button) rootView.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                submit();
            }
        });

        imagePath = getArguments().getString("ImagePath");
        if (imagePath != null && !imagePath.equals("")) {
            final ImageView imageView = (ImageView) rootView.findViewById(R.id.submission_details_photo);
            float imgWeight = ((LinearLayout.LayoutParams) (imageView.getLayoutParams())).weight;
            ScreenDimensions dimensioner = new ScreenDimensions(getActivity().getBaseContext());
            int imgHeight = (int) (dimensioner.getScreenHeight() * imgWeight);
            imageView.setImageBitmap(BitMapDisplay.decodeSampledBitmapFromPath(imagePath, imgHeight));
        } else {
            rootView.findViewById(R.id.submission_details_photo).setVisibility(View.GONE);
        }

        return rootView;
    }


    public void submit(){
        String date = new Date(System.currentTimeMillis()).toString();
        String descriptionText = description.getText().toString();
        String extraDetailsText = extraDetails.getText().toString();
        parent.submit(date, descriptionText,extraDetailsText);

    }

    public void setParent(ReportTabbedActivity p){
        parent = p;
    }
}
