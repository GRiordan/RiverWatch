//*H****************************************************************************
// FILENAME:	History_IncidentActivity.java
//
// DESCRIPTION:
//  activity for the incident reports in the history
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
import android.widget.Button;
import android.widget.Toast;

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.Report_functionality.BasicLocation;
import com.vuw.project1.riverwatch.Report_functionality.IncidentReport;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.service.App;

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
                intent.putExtra("latitude", obj.latitude);
                intent.putExtra("longitude", obj.longitude);
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
                                Toast.makeText(History_IncidentActivity.this, "successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
            @Override
            public void submit(final Incident_Report obj) {
                new AlertDialog.Builder(History_IncidentActivity.this)
                        .setTitle("Submit Report to Website")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final NetworkInfo network = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                                if (network != null && network.isConnected()) {
                                    new App().getServiceBroker().sendReport(new IncidentReport(obj.name, obj.description, obj.image, new BasicLocation(obj.latitude, obj.longitude), obj.date));
                                    Database database = new Database(History_IncidentActivity.this);
                                    database.submittedIncidentReportById(obj.id);
                                    ArrayList<Incident_Report> list = database.getIncidentReportList();
                                    mAdapter.updateList(list);
                                    Toast.makeText(History_IncidentActivity.this, "successfully submitted", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(History_IncidentActivity.this, "unable to connect", Toast.LENGTH_SHORT).show();
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
