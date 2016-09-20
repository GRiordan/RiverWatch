package com.vuw.project1.riverwatch.colour_algorithm;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.vuw.project1.riverwatch.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by George on 17/09/2016.
 */
public class MapFragment extends Fragment {
    private TextView text;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MapFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InfoFragment newInstance(int sectionNumber) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nitrate_map, container, false);

        TextView text = (TextView) rootView.findViewById(R.id.section_label);

        String s = getArguments().getString("info_string");
        text.setText(s);

        return rootView;
    }

}
