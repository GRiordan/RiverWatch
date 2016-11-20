//*H****************************************************************************
// FILENAME:	InfoFragment.java
//
// DESCRIPTION:
//  The fragment that gets blown up when the user finishes a nitrate/nitrite
//  test analysis
//
//  A list of names of copyright information is provided in the README
//
//    This file is part of RiverWatch.
//
//    RiverWatch is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    RiverWatch is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with RiverWatch.  If not, see <http://www.gnu.org/licenses/>.
//
// CHANGES:
// DATE			WHO	    DETAILS
// 20/11/1995	George	Added header.
//
//*H*

package com.vuw.project1.riverwatch.colour_algorithm;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by George on 17/09/2016.
 */
public class InfoFragment extends Fragment {

    private EditText name;
    private EditText info;
    private TextView nitrateTextView;
    private TextView nitriteTextView;
    private ImageView leftView;
    private ImageView rightView;
    private LinearLayout nitrateView;
    private LinearLayout nitriteView;
    private ProgressBar progressBar;


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public InfoFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_nitrate_info, container, false);

        // start analysis task
        nitrateView = (LinearLayout) rootView.findViewById(R.id.nitrateLinearLayout);
        nitriteView = (LinearLayout) rootView.findViewById(R.id.nitriteLinearLayout);
        nitrateTextView = (TextView) rootView.findViewById(R.id.nitrateTextView);
        nitriteTextView = (TextView) rootView.findViewById(R.id.nitriteTextView);
        leftView = (ImageView) rootView.findViewById(R.id.nitrateImageView);
        rightView = (ImageView) rootView.findViewById(R.id.nitriteImageView);
        progressBar= (ProgressBar) rootView.findViewById(R.id.progressBar2);

        final Double nitrate = getArguments().getDouble("nitrate");
        final Double nitrite = getArguments().getDouble("nitrite");

        // set image views to left and right square of strip
        Bitmap left = (Bitmap) getArguments().getParcelable("left");
        Bitmap right = (Bitmap) getArguments().getParcelable("right");
        leftView.setImageBitmap(left);
        rightView.setImageBitmap(right);

        // set text view for information
        info = (EditText) rootView.findViewById(R.id.infoEditText);
        name = (EditText) rootView.findViewById(R.id.nameEditText);

        return rootView;
    }

    public String getName(){
        return name.getText().toString();
    }

    public String getInfo(){
        return info.getText().toString();
    }

    public void setNitrateNitrite(Bundle result){
        Double nitrate = result.getDouble("nitrate");
        Double nitrite = result.getDouble("nitrite");
        // set text views to nitrite and nitrate levels
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        nitrateTextView.setText("Nitrate: " + df.format(nitrate));
        nitriteTextView.setText("Nitrite: " + df.format(nitrite));
    }

    public void setLayoutsVisible(){
        // make the views visible
        nitriteView.setVisibility(View.VISIBLE);
        nitrateView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
