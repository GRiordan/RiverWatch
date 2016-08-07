package com.vuw.project1.riverwatch.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.vuw.project1.riverwatch.objects.Incident_Report;

import java.util.ArrayList;

/**
 * Created by James on 6/08/2016.
 */
public class Database extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Incident_Report> getIncidentReportList(){
        ArrayList<Incident_Report> incidents = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("incident_report", null, null, null, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLocation = cursor.getColumnIndex("location");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxDescription = cursor.getColumnIndex("description");
                    int idxImage = cursor.getColumnIndex("image");
                    incidents.add(new Incident_Report(
                            cursor.getInt(idxId),
                            cursor.getString(idxName),
                            cursor.getString(idxLocation),
                            cursor.getString(idxDate),
                            cursor.getString(idxDescription),
                            cursor.getString(idxImage)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return incidents;
    }

    public Incident_Report getIncidentReportById(long id){
        Incident_Report incident = null;
        Cursor cursor = getReadableDatabase().query("incident_report", null, "_id = ?", new String[]{Long.toString(id)}, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLocation = cursor.getColumnIndex("location");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxDescription = cursor.getColumnIndex("description");
                    int idxImage = cursor.getColumnIndex("image");
                    incident = new Incident_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getString(idxLocation),
                            cursor.getString(idxDate),
                            cursor.getString(idxDescription),
                            cursor.getString(idxImage)
                    );
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return incident;
    }

    /**
     * Database database = new Database(MainActivity.this);
     long id = database.saveIncidentReport("test name", "location", 10, 10, "DD/MM/YYYY", "description", "img");
     ^how to call
     */
    public long saveIncidentReport(String name, String location, double latitude, double longitude, String date, String description, String image){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("location", location);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("date", date);
        values.put("description", description);
        values.put("image", image);
        return getWritableDatabase().insert("incident_report", null, values);
    }
}
