package com.vuw.project1.riverwatch.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.Report_functionality.BasicLocation;
import com.vuw.project1.riverwatch.Report_functionality.IncidentReport;
import com.vuw.project1.riverwatch.bluetooth.Location;
import com.vuw.project1.riverwatch.bluetooth.Sample;
import com.vuw.project1.riverwatch.bluetooth.WaterQualityReport;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.objects.Water_Report;
import com.vuw.project1.riverwatch.objects.Water_Report_Sample;
import com.vuw.project1.riverwatch.service.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History_WaterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterHistory_Water mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Water_Report> waterTests = new Database(this).getWaterReportList();
        mAdapter = new AdapterHistory_Water(this, waterTests, new AdapterHistory_Water.Callback() {
            @Override
            public void open(Water_Report obj) {
                Intent intent = new Intent(History_WaterActivity.this, History_WaterActivity_Samples.class);
                intent.putExtra("id", obj.id);
                intent.putExtra("latitude", obj.latitude);
                intent.putExtra("longitude", obj.longitude);
                startActivity(intent);
            }
            @Override
            public void delete(final Water_Report obj) {
                new AlertDialog.Builder(History_WaterActivity.this)
                        .setTitle("Delete Report")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Database database = new Database(History_WaterActivity.this);
                                database.deleteWaterReportById(obj.id);
                                ArrayList<Water_Report> list = database.getWaterReportList();
                                mAdapter.updateList(list);
                                Toast.makeText(History_WaterActivity.this, "successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
            @Override
            public void submit(final Water_Report obj) {
                new AlertDialog.Builder(History_WaterActivity.this)
                        .setTitle("Submit Report to Website")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final NetworkInfo network = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                                if (network != null && network.isConnected()) {
                                    Database database = new Database(History_WaterActivity.this);
                                    ArrayList<Water_Report_Sample> watRepSamples = database.getWaterReportSamplesList(obj.id);
                                    List<Sample> samples = new ArrayList<Sample>();
                                    for(Water_Report_Sample s : watRepSamples){
                                        samples.add(new Sample(new Date(obj.date), new Location(obj.latitude, obj.longitude), s.conductivity, s.temperature, s.turbidity, s.pH));
                                    }
                                    new App().getServiceBroker().sendReport(new WaterQualityReport(new Location(obj.latitude, obj.longitude), new Date(obj.date), samples,true));
                                    database.submittedWaterReportById(obj.id);
                                    ArrayList<Water_Report> list = database.getWaterReportList();
                                    mAdapter.updateList(list);
                                    Toast.makeText(History_WaterActivity.this, "successfully submitted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(History_WaterActivity.this, "unable to connect", Toast.LENGTH_SHORT).show();
                                }
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
