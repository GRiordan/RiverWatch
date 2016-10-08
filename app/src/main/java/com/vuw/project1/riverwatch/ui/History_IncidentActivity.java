package com.vuw.project1.riverwatch.ui;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
            @Override
            public void delete(final Incident_Report obj) {
                new AlertDialog.Builder(History_IncidentActivity.this)
                        .setTitle("Delete Report")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Database database = new Database(History_IncidentActivity.this);
                                database.deleteIncidentReportById(obj.id);
                                ArrayList<Incident_Report> list = database.getIncidentReportList();
                                mAdapter.updateList(list);
                                Toast.makeText(History_IncidentActivity.this, obj.name+" deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        GridLayoutManager llm = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
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
