//*H****************************************************************************
// FILENAME:	AdapterHistory_Water.java
//
// DESCRIPTION:
//  adapter for the water report
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.objects.Water_Report;

import java.util.ArrayList;

public class AdapterHistory_Water extends RecyclerView.Adapter<AdapterHistory_Water.ShowViewHolder> {
    private Context mContext;
    private ArrayList<Water_Report> mContent;
    private Callback mCallback;
    public AdapterHistory_Water(Context mContext, ArrayList<Water_Report> mContent, Callback mCallback) {
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
        final Water_Report obj = mContent.get(position);
        viewHolder.name.setText(obj.name);
        viewHolder.date.setText(obj.date);
        Glide.with(mContext)
                .load("http://vignette1.wikia.nocookie.net/clubpenguin/images/a/a7/Water_Droplet_Pin.PNG/revision/latest?cb=20150314141114")
                .placeholder(null)
                .crossFade()
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
        return new AdapterHistory_Water.ShowViewHolder(itemView);
    }
    class ShowViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView image;
        TextView name;
        TextView date;
        Button button;
        ShowViewHolder(View v) {
            super(v);
            view = v;
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
            button = (Button) v.findViewById(R.id.submit_button);
        }
    }
    interface Callback {
        void open(Water_Report obj);
        void delete(Water_Report obj);
        void submit(Water_Report obj);
    }

    public void updateList(ArrayList<Water_Report> list){
        this.mContent = list;
        notifyDataSetChanged();
    }
}