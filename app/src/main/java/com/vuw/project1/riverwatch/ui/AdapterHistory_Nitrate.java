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
import com.vuw.project1.riverwatch.objects.Nitrate_Report;

import java.util.ArrayList;

public class AdapterHistory_Nitrate extends RecyclerView.Adapter<AdapterHistory_Nitrate.ShowViewHolder> {
    private Context mContext;
    private ArrayList<Nitrate_Report> mContent;
    private Callback mCallback;
    public AdapterHistory_Nitrate(Context mContext, ArrayList<Nitrate_Report> mContent, Callback mCallback) {
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
        final Nitrate_Report obj = mContent.get(position);
        viewHolder.title.setText(obj.name);
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
        return new AdapterHistory_Nitrate.ShowViewHolder(itemView);
    }
    class ShowViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title;
        TextView date;
        ImageView image;
        ShowViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }
    interface Callback {
        void open(Nitrate_Report obj);
        void delete(Nitrate_Report obj);
    }

    public void updateList(ArrayList<Nitrate_Report> list){
        this.mContent = list;
        notifyDataSetChanged();
    }
}