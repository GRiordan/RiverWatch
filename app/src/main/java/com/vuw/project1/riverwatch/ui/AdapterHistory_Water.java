package com.vuw.project1.riverwatch.ui;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vuw.project1.riverwatch.R;
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
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.open(obj);
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
        ShowViewHolder(View v) {
            super(v);
            view = v;
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
        }
    }
    interface Callback {
        void open(Water_Report obj);
    }
}