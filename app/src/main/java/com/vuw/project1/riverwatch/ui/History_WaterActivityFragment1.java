package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
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

import static com.vuw.project1.riverwatch.R.id.view;

/**
 * Created by James on 8/10/2016.
 */

public class History_WaterActivityFragment1 extends Fragment {

    private RecyclerView recyclerView;
    private AdapterHistory_Water_Samples mAdapter;

    static History_WaterActivityFragment1 newInstance(long id){
        History_WaterActivityFragment1 fragment = new History_WaterActivityFragment1();
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
