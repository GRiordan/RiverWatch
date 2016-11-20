//*H****************************************************************************
// FILENAME:	AdapterHistory_Incident.java
//
// DESCRIPTION:
//  adapter for the incident report
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


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.Report_functionality.IncidentReport;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.service.App;

import java.util.ArrayList;

public class AdapterHistory_Incident extends RecyclerView.Adapter<AdapterHistory_Incident.ShowViewHolder> {
    private Context mContext;
    private ArrayList<Incident_Report> mContent;
    private Callback mCallback;
    public AdapterHistory_Incident(Context mContext, ArrayList<Incident_Report> mContent, Callback mCallback) {
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
        final Incident_Report obj = mContent.get(position);
        viewHolder.title.setText(obj.name);
        viewHolder.date.setText(obj.date);
        Glide.with(mContext)
                .load(obj.image)
                .placeholder(null)
                .crossFade()
                .centerCrop()
                .into(viewHolder.image);
        if(obj.submitted == 1) {
            viewHolder.button.setAlpha(.25f);
            viewHolder.button.setEnabled(false);
        } else{
            viewHolder.button.setAlpha(1f);
            viewHolder.button.setEnabled(true);
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.submit(obj);
            }
        });
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.open(obj);
            }
        });
        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallback.delete(obj);
                return false;
            }
        });
    }
    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_history, viewGroup, false);
        return new AdapterHistory_Incident.ShowViewHolder(itemView);
    }
    class ShowViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title;
        TextView date;
        ImageView image;
        Button button;
        ShowViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
            image = (ImageView) v.findViewById(R.id.image);
            button = (Button) v.findViewById(R.id.submit_button);
        }
    }
    interface Callback {
        void open(Incident_Report obj);
        void delete(Incident_Report obj);
        void submit(Incident_Report obj);
    }

    public void updateList(ArrayList<Incident_Report> list){
        this.mContent = list;
        notifyDataSetChanged();
    }
}