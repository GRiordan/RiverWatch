package com.vuw.project1.riverwatch.ui;

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

import com.vuw.project1.riverwatch.R;
import com.vuw.project1.riverwatch.database.Database;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.objects.Nitrate_Report;

import java.util.ArrayList;

public class History_NitrateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterHistory_Nitrate mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ArrayList<Nitrate_Report> nitrateTests = new Database(this).getNitrateReportList();
        mAdapter = new AdapterHistory_Nitrate(this, nitrateTests, new AdapterHistory_Nitrate.Callback() {
            @Override
            public void open(Nitrate_Report obj) {
                Intent intent = new Intent(History_NitrateActivity.this, History_NitrateActivity_Item.class);
                intent.putExtra("id", obj.id);
//                intent.putExtra("name", obj.name);
//                intent.putExtra("location", obj.location);
//                intent.putExtra("date", obj.date);
//                intent.putExtra("description", obj.description);
//                intent.putExtra("image", obj.image);
//                intent.putExtra("nitrate", obj.nitrate);
//                intent.putExtra("nitrite", obj.nitrite);
                startActivity(intent);
            }
            @Override
            public void delete(final Nitrate_Report obj) {
                new AlertDialog.Builder(History_NitrateActivity.this)
                        .setTitle("Delete Report")
                        .setMessage("Are you sure?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Database database = new Database(History_NitrateActivity.this);
                                database.deleteNitrateReportById(obj.id);
                                ArrayList<Nitrate_Report> list = database.getNitrateReportList();
                                mAdapter.updateList(list);
                                Toast.makeText(History_NitrateActivity.this, obj.name+" deleted", Toast.LENGTH_SHORT).show();
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
