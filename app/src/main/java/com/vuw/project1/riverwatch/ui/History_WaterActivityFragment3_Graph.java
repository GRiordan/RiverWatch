package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Water_Report_Sample;

import java.util.ArrayList;

import static com.vuw.project1.riverwatch.R.id.graph_conductivity;
import static com.vuw.project1.riverwatch.R.id.graph_pH;
import static com.vuw.project1.riverwatch.R.id.graph_temperature;
import static com.vuw.project1.riverwatch.R.id.graph_turbidity;

/**
 * Created by James on 8/10/2016.
 */

public class History_WaterActivityFragment3_Graph extends Fragment {

    private GraphView graphView_temperature;
    private GraphView graphView_pH;
    private GraphView graphView_conductivity;
    private GraphView graphView_turbidity;

    static History_WaterActivityFragment3_Graph newInstance(long id){
        History_WaterActivityFragment3_Graph fragment = new History_WaterActivityFragment3_Graph();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Water_Report_Sample> waterTestSamples = new Database(getActivity()).getWaterReportSamplesList(getArguments().getLong("id"));
        View rootView = inflater.inflate(R.layout.activity_history_water_graph, container, false);
        graphView_temperature = (GraphView) rootView.findViewById(graph_temperature);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for(Water_Report_Sample s : waterTestSamples){
            series.appendData(new DataPoint(s.time, s.temperature),false,Integer.MAX_VALUE);
        }
        graphView_temperature.addSeries(series);
        graphView_temperature.setTitle("Temperature \u00b0C");
        graphView_temperature.getViewport().setScalable(true);
        GridLabelRenderer gridLabel = graphView_temperature.getGridLabelRenderer();
        gridLabel.setPadding(30);

        graphView_pH = (GraphView) rootView.findViewById(graph_pH);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
        for(Water_Report_Sample s : waterTestSamples){
            series2.appendData(new DataPoint(s.time, s.pH),false,Integer.MAX_VALUE);
        }
        graphView_pH.addSeries(series2);
        graphView_pH.setTitle("pH Level");
        graphView_pH.getViewport().setScalable(true);
        GridLabelRenderer gridLabel2 = graphView_pH.getGridLabelRenderer();
        gridLabel2.setPadding(30);

        graphView_conductivity = (GraphView) rootView.findViewById(graph_conductivity);
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();
        for(Water_Report_Sample s : waterTestSamples){
            series3.appendData(new DataPoint(s.time, s.conductivity),false,Integer.MAX_VALUE);
        }
        graphView_conductivity.addSeries(series3);
        graphView_conductivity.setTitle("Conductivity");
        graphView_conductivity.getViewport().setScalable(true);
        GridLabelRenderer gridLabel3 = graphView_conductivity.getGridLabelRenderer();
        gridLabel3.setPadding(30);

        graphView_turbidity = (GraphView) rootView.findViewById(graph_turbidity);
        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<DataPoint>();
        for(Water_Report_Sample s : waterTestSamples){
            series4.appendData(new DataPoint(s.time, s.turbidity),false,Integer.MAX_VALUE);
        }
        graphView_turbidity.addSeries(series4);
        graphView_turbidity.setTitle("Turbidity");
        graphView_turbidity.getViewport().setScalable(true);
        GridLabelRenderer gridLabel4 = graphView_turbidity.getGridLabelRenderer();
        gridLabel4.setPadding(30);

        return rootView;
    }

}
