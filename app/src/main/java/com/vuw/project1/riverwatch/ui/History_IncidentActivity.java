package com.vuw.project1.riverwatch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Incident_Report;

import java.util.ArrayList;

public class History_IncidentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterHistory_Incident mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Incident_Report> incidents = new Database(this).getIncidentReportList();
        mAdapter = new AdapterHistory_Incident(this, incidents, new AdapterHistory_Incident.Callback() {
            @Override
            public void open(Incident_Report obj) {
                Intent intent = new Intent(History_IncidentActivity.this, History_IncidentActivity_Item.class);
                intent.putExtra("id", obj.id);
//                intent.putExtra("name", obj.name);
//                intent.putExtra("location", obj.location);
//                intent.putExtra("date", obj.date);
//                intent.putExtra("description", obj.description);
//                intent.putExtra("image", obj.image);
                startActivity(intent);
            }
        });
        GridLayoutManager llm = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }

    public ArrayList<Incident_Report> addDummyData(){
        ArrayList<Incident_Report> incidents = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            incidents.add(new Incident_Report(i, "test name"+i, "test location", "DD/MM/YYYY", "test description\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", "https://cathyqmumford.files.wordpress.com/2010/12/connecticut-river-cow.jpg"));
        }
        return incidents;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
