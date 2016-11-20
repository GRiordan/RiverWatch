//*H****************************************************************************
// FILENAME:	History_IncidentActivityFragment_Info.java
//
// DESCRIPTION:
//  A fragment to hold the information of a incident report in the history
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

/**
 * Created by James on 8/10/2016.
 */

public class History_IncidentActivityFragment_Info extends Fragment {

    private ImageView Image;
    private TextView Description;
    private TextView Name;
    private TextView Date;
    private Incident_Report report;

    static History_IncidentActivityFragment_Info newInstance(long id){
        History_IncidentActivityFragment_Info fragment = new History_IncidentActivityFragment_Info();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_history_incident, container, false);

        report = new Database(getActivity()).getIncidentReportById(getArguments().getLong("id"));
        Image = (ImageView) rootView.findViewById(R.id.image);
        Glide.with(this)
                .load(report.image)
                .placeholder(null)
                .crossFade()
                .centerCrop()
                .into(Image);
        Description = (TextView) rootView.findViewById(R.id.description);
        Description.setText(report.description);
        Name = (TextView) rootView.findViewById(R.id.name);
        Name.setText(report.name);
        Date = (TextView) rootView.findViewById(R.id.date);
        Date.setText(report.date);
        return rootView;
    }

}
