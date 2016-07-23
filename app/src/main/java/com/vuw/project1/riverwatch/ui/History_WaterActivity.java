package com.vuw.project1.riverwatch.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.objects.Nitrate_Object;
import com.vuw.project1.riverwatch.objects.Water_Object;

import java.util.ArrayList;

public class History_WaterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterHistory_Water mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Water_Object> waterTests = addDummyData();
        mAdapter = new AdapterHistory_Water(this, waterTests, new AdapterHistory_Water.Callback() {
            @Override
            public void open(Water_Object obj) {
                Toast.makeText(History_WaterActivity.this, obj.name, Toast.LENGTH_LONG).show();
            }
        });
        GridLayoutManager llm = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Water_Object> addDummyData(){
        ArrayList<Water_Object> waterTests = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            waterTests.add(new Water_Object(i, "test name"+i, "test location", "DD/MM/YYYY", "test description", "http://vignette1.wikia.nocookie.net/clubpenguin/images/a/a7/Water_Droplet_Pin.PNG/revision/latest?cb=20150314141114", 25, 7, 10, 10));
        }
        return waterTests;
    }
}
