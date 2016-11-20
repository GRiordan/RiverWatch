//*H****************************************************************************
// FILENAME:	History_WaterActivityFragment_Samples.java
//
// DESCRIPTION:
//  handles user actions in the water report history section
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Water_Report_Sample;

import java.util.ArrayList;

/**
 * Created by James on 8/10/2016.
 */

public class History_WaterActivityFragment_Samples extends Fragment {

    private RecyclerView recyclerView;
    private AdapterHistory_Water_Samples mAdapter;

    static History_WaterActivityFragment_Samples newInstance(long id){
        History_WaterActivityFragment_Samples fragment = new History_WaterActivityFragment_Samples();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_history_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        ArrayList<Water_Report_Sample> waterTestSamples = new Database(getActivity()).getWaterReportSamplesList(getArguments().getLong("id"));
        mAdapter = new AdapterHistory_Water_Samples(getActivity(), waterTestSamples, new AdapterHistory_Water_Samples.Callback() {
            @Override
            public void open(Water_Report_Sample obj) {
            }
        });
        GridLayoutManager llm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

}
