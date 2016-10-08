package com.vuw.project1.riverwatch.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.objects.Water_Report;
import com.vuw.project1.riverwatch.objects.Water_Report_Sample;

import java.util.ArrayList;

public class AdapterHistory_Water_Samples extends RecyclerView.Adapter<AdapterHistory_Water_Samples.ShowViewHolder> {
    private Context mContext;
    private ArrayList<Water_Report_Sample> mContent;
    private Callback mCallback;
    public AdapterHistory_Water_Samples(Context mContext, ArrayList<Water_Report_Sample> mContent, Callback mCallback) {
        this.mContext = mContext;
        this.mContent = mContent;
        this.mCallback = mCallback;
    }
    @Override
    public int getItemCount() {
        return mContent.size();
    }
    @Override
    public void onBindViewHolder(final ShowViewHolder viewHolder, int position) {
        final Water_Report_Sample obj = mContent.get(position);
        viewHolder.time.setText(obj.time);
        viewHolder.conductivity.setText("Conductivity: "+obj.conductivity);
        viewHolder.pH.setText("pH: "+obj.pH);
        viewHolder.temperature.setText("Temperature: "+obj.temperature);
        viewHolder.turbidity.setText("Turbidity: "+obj.turbidity);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.open(obj);
            }
        });
    }
    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_history_water_test_sample, viewGroup, false);
        return new AdapterHistory_Water_Samples.ShowViewHolder(itemView);
    }
    class ShowViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView time;
        TextView conductivity;
        TextView pH;
        TextView temperature;
        TextView turbidity;
        ShowViewHolder(View v) {
            super(v);
            view = v;
            time = (TextView) v.findViewById(R.id.time);
            conductivity = (TextView) v.findViewById(R.id.conductivity);
            pH = (TextView) v.findViewById(R.id.pH);
            temperature = (TextView) v.findViewById(R.id.temperature);
            turbidity = (TextView) v.findViewById(R.id.turbidity);
        }
    }
    interface Callback {
        void open(Water_Report_Sample obj);
    }
}