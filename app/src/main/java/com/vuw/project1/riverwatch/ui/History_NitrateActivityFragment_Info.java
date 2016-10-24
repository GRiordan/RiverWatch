package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.objects.Nitrate_Report;

/**
 * Created by James on 8/10/2016.
 */

public class History_NitrateActivityFragment_Info extends Fragment {

    private ImageView Image;
    private TextView Nitrate;
    private TextView Nitrite;
    private TextView Description;
    private TextView Name;
    private TextView Date;
    private Nitrate_Report report;

    static History_NitrateActivityFragment_Info newInstance(long id){
        History_NitrateActivityFragment_Info fragment = new History_NitrateActivityFragment_Info();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_history_nitrate, container, false);

        report = new Database(getActivity()).getNitrateReportById(getArguments().getLong("id"));
        Image = (ImageView) rootView.findViewById(R.id.image);
        Glide.with(this)
                .load(report.image)
                .placeholder(null)
                .crossFade()
                .centerCrop()
                .into(Image);
        Nitrate = (TextView) rootView.findViewById(R.id.nitrate);
        Nitrate.setText(""+report.nitrate);
        Nitrite = (TextView) rootView.findViewById(R.id.nitrite);
        Nitrite.setText(""+report.nitrite);
        Description = (TextView) rootView.findViewById(R.id.description);
        Description.setText(report.description);
        Name = (TextView) rootView.findViewById(R.id.name);
        Name.setText(report.name);
        Date = (TextView) rootView.findViewById(R.id.date);
        Date.setText(report.date);
        return rootView;
    }

}
