package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.objects.Incident_Object;
import com.vuw.project1.riverwatch.objects.Nitrate_Object;

import java.util.ArrayList;

public class History_NitrateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterHistory_Nitrate mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Nitrate_Object> nitrateTests = addDummyData();
        mAdapter = new AdapterHistory_Nitrate(this, nitrateTests, new AdapterHistory_Nitrate.Callback() {
            @Override
            public void open(Nitrate_Object obj) {
                Intent intent = new Intent(History_NitrateActivity.this, History_NitrateActivity_Item.class);
                intent.putExtra("name", obj.name);
                intent.putExtra("location", obj.location);
                intent.putExtra("date", obj.date);
                intent.putExtra("description", obj.description);
                intent.putExtra("image", obj.image);
                intent.putExtra("nitrate", obj.nitrate);
                intent.putExtra("nitrite", obj.nitrite);
                startActivity(intent);
            }
        });
        GridLayoutManager llm = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Nitrate_Object> addDummyData(){
        ArrayList<Nitrate_Object> incidents = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            incidents.add(new Nitrate_Object(i, "test name"+i, "test location", "DD/MM/YYYY", "test description\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", "http://www.fullstop.com.au/HTMLfilesv2/060_SoilSolution/030_MonitoringNutrients/MonitoringNitrate_Images/020_MatchStripColour300.jpg",10,10));
        }
        return incidents;
    }
}
