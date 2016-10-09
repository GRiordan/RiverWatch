package com.vuw.project1.riverwatch.ui;


import android.content.Context;
import android.net.Uri;
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
        viewHolder.title.setText(obj.name);
        viewHolder.location.setText(obj.location);
        viewHolder.date.setText(obj.date);
        Glide.with(mContext)
                .load(obj.image)
                .placeholder(null)
                .crossFade()
                .centerCrop()
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
        TextView title;
        TextView location;
        TextView date;
        ImageView image;
        ShowViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.name);
            location = (TextView) v.findViewById(R.id.location);
            date = (TextView) v.findViewById(R.id.date);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }
    interface Callback {
        void open(Water_Report obj);
    }
}