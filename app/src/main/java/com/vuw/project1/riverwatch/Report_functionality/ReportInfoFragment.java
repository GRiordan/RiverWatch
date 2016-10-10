package com.vuw.project1.riverwatch.Report_functionality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.vuw.project1.riverwatch.R;

/**
 * Created by Alex on 10/10/2016.
 */
public class ReportInfoFragment extends Fragment {
    private EditText description;
    private EditText extraDetails;
    private ImageView image;
    private Button submitButton;
    private String imagePath;
    private BasicLocation location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_info, container, false);

        description = (EditText) rootView.findViewById(R.id.submission_details_description);
        extraDetails = (EditText) rootView.findViewById(R.id.submission_details_extra_details);



        return rootView;
    }

}
