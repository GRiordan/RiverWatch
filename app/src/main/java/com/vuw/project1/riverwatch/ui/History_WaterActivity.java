package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.objects.Water_Report;

import java.util.ArrayList;

public class History_WaterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterHistory_Water mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Water_Report> waterTests = addDummyData();
        mAdapter = new AdapterHistory_Water(this, waterTests, new AdapterHistory_Water.Callback() {
            @Override
            public void open(Water_Report obj) {
                Intent intent = new Intent(History_WaterActivity.this, History_WaterActivity_Item.class);
                intent.putExtra("name", obj.name);
                intent.putExtra("location", obj.location);
                intent.putExtra("date", obj.date);
                intent.putExtra("description", obj.description);
                intent.putExtra("image", obj.image);
                intent.putExtra("temperature", obj.temperature);
                intent.putExtra("pH", obj.pH);
                intent.putExtra("conductivity", obj.conductivity);
                intent.putExtra("turbidity", obj.turbidity);
                startActivity(intent);
            }
        });
        GridLayoutManager llm = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Water_Report> addDummyData(){
        ArrayList<Water_Report> waterTests = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            waterTests.add(new Water_Report(i, "test name"+i, "test location", "DD/MM/YYYY", "test description\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", "http://vignette1.wikia.nocookie.net/clubpenguin/images/a/a7/Water_Droplet_Pin.PNG/revision/latest?cb=20150314141114", 25, 7, 10, 10));
        }
        return waterTests;
    }
}
