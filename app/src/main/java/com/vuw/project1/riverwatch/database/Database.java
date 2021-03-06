//*H****************************************************************************
// FILENAME:	Database.java
//
// DESCRIPTION:
//  methods to interact with the database of nitrate/nitrite, incident reports and bluetooth report
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

package com.vuw.project1.riverwatch.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.vuw.project1.riverwatch.objects.Incident_Report;
import com.vuw.project1.riverwatch.objects.Nitrate_Report;
import com.vuw.project1.riverwatch.objects.Water_Report;
import com.vuw.project1.riverwatch.objects.Water_Report_Sample;

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
        Cursor cursor = getReadableDatabase().query("incident_report", null, null, null, null, null, "_id DESC");
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLatitude = cursor.getColumnIndex("latitude");
                    int idxLongitude = cursor.getColumnIndex("longitude");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxDescription = cursor.getColumnIndex("description");
                    int idxImage = cursor.getColumnIndex("image");
                    int idxSubmitted = cursor.getColumnIndex("submitted");
                    incidents.add(new Incident_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getDouble(idxLatitude),
                            cursor.getDouble(idxLongitude),
                            cursor.getString(idxDate),
                            cursor.getString(idxDescription),
                            cursor.getString(idxImage),
                            cursor.getInt(idxSubmitted)
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
                    int idxLatitude = cursor.getColumnIndex("latitude");
                    int idxLongitude = cursor.getColumnIndex("longitude");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxDescription = cursor.getColumnIndex("description");
                    int idxImage = cursor.getColumnIndex("image");
                    int idxSubmitted = cursor.getColumnIndex("submitted");
                    incident = new Incident_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getDouble(idxLatitude),
                            cursor.getDouble(idxLongitude),
                            cursor.getString(idxDate),
                            cursor.getString(idxDescription),
                            cursor.getString(idxImage),
                            cursor.getInt(idxSubmitted)
                    );
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return incident;
    }

    public void deleteIncidentReportById(long id){
        getWritableDatabase().delete("incident_report", "_id = ?", new String[]{""+id});

    }

    /**
     * Database database = new Database(MainActivity.this);
     long id = database.saveIncidentReport("test name", "location", 10, 10, "DD/MM/YYYY", "description", "img");
     ^how to call
     */
    public long saveIncidentReport(String name, double latitude, double longitude, String date, String description, String image, int submitted){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("date", date);
        values.put("description", description);
        values.put("image", image);
        values.put("submitted", submitted);
        return getWritableDatabase().insert("incident_report", null, values);
    }

    public void submittedIncidentReportById(long id){
        ContentValues values = new ContentValues();
        values.put("submitted", 1);
        getWritableDatabase().update("incident_report", values, "_id = ?", new String[]{""+id});
    }

    public ArrayList<Nitrate_Report> getNitrateReportList(){
        ArrayList<Nitrate_Report> nitrateReports = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("nitrate_report", null, null, null, null, null, "_id DESC");
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLatitude = cursor.getColumnIndex("latitude");
                    int idxLongitude = cursor.getColumnIndex("longitude");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxDescription = cursor.getColumnIndex("description");
                    int idxImage = cursor.getColumnIndex("image");
                    int idxNitrate = cursor.getColumnIndex("nitrate");
                    int idxNitrite = cursor.getColumnIndex("nitrite");
                    int idxSubmitted = cursor.getColumnIndex("submitted");
                    nitrateReports.add(new Nitrate_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getDouble(idxLatitude),
                            cursor.getDouble(idxLongitude),
                            cursor.getString(idxDate),
                            cursor.getString(idxDescription),
                            cursor.getString(idxImage),
                            cursor.getDouble(idxNitrate),
                            cursor.getDouble(idxNitrite),
                            cursor.getInt(idxSubmitted)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return nitrateReports;
    }

    public Nitrate_Report getNitrateReportById(long id){
        Nitrate_Report nitrateReport = null;
        Cursor cursor = getReadableDatabase().query("nitrate_report", null, "_id = ?", new String[]{Long.toString(id)}, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLatitude = cursor.getColumnIndex("latitude");
                    int idxLongitude = cursor.getColumnIndex("longitude");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxDescription = cursor.getColumnIndex("description");
                    int idxImage = cursor.getColumnIndex("image");
                    int idxNitrate = cursor.getColumnIndex("nitrate");
                    int idxNitrite = cursor.getColumnIndex("nitrite");
                    int idxSubmitted = cursor.getColumnIndex("submitted");
                    nitrateReport = new Nitrate_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getDouble(idxLatitude),
                            cursor.getDouble(idxLongitude),
                            cursor.getString(idxDate),
                            cursor.getString(idxDescription),
                            cursor.getString(idxImage),
                            cursor.getDouble(idxNitrate),
                            cursor.getDouble(idxNitrite),
                            cursor.getInt(idxSubmitted)
                    );
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return nitrateReport;
    }

    public void deleteNitrateReportById(long id){
        getWritableDatabase().delete("nitrate_report", "_id = ?", new String[]{""+id});
    }

    public void submittedNitrateReportById(long id){
        ContentValues values = new ContentValues();
        values.put("submitted", 1);
        getWritableDatabase().update("nitrate_report", values, "_id = ?", new String[]{""+id});
    }

    /**
     * Database database = new Database(MainActivity.this);
     long id = database.saveIncidentReport("test name", "location", 10, 10, "DD/MM/YYYY", "description", "img");
     ^how to call
     */
    public long saveNitrateReport(String name, double latitude, double longitude, String date, String description, String image, double nitrate, double nitrite, int submitted){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("date", date);
        values.put("description", description);
        values.put("image", image);
        values.put("nitrate", nitrate);
        values.put("nitrite", nitrite);
        values.put("submitted", submitted);
        return getWritableDatabase().insert("nitrate_report", null, values);
    }

    public ArrayList<Water_Report> getWaterReportList(){
        ArrayList<Water_Report> waterReports = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("water_report", null, null, null, null, null, "_id DESC");
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLatitude = cursor.getColumnIndex("latitude");
                    int idxLongitude = cursor.getColumnIndex("longitude");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxSubmitted = cursor.getColumnIndex("submitted");
                    waterReports.add(new Water_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getDouble(idxLatitude),
                            cursor.getDouble(idxLongitude),
                            cursor.getString(idxDate),
                            cursor.getInt(idxSubmitted)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return waterReports;
    }

    public Water_Report getWaterReportById(long id){
        Water_Report waterReport = null;
        Cursor cursor = getReadableDatabase().query("water_report", null, "_id = ?", new String[]{Long.toString(id)}, null, null, null);
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxName = cursor.getColumnIndex("name");
                    int idxLatitude = cursor.getColumnIndex("latitude");
                    int idxLongitude = cursor.getColumnIndex("longitude");
                    int idxDate = cursor.getColumnIndex("date");
                    int idxSubmitted = cursor.getColumnIndex("submitted");
                    waterReport = new Water_Report(
                            cursor.getLong(idxId),
                            cursor.getString(idxName),
                            cursor.getDouble(idxLatitude),
                            cursor.getDouble(idxLongitude),
                            cursor.getString(idxDate),
                            cursor.getInt(idxSubmitted)
                    );
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return waterReport;
    }

    /**
     * Database database = new Database(MainActivity.this);
     long id = database.saveIncidentReport("test name", "location", 10, 10, "DD/MM/YYYY", "description", "img");
     ^how to call
     */
    public long saveWaterReport(String name, double latitude, double longitude, String date, int submitted){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("date", date);
        values.put("submitted", submitted);
        return getWritableDatabase().insert("water_report", null, values);
    }

    public long saveWaterReportSample(long fk_water_report_id, double temperature, double pH, double conductivity, double turbidity, double time){
        ContentValues values = new ContentValues();
        values.put("fk_water_report_id", fk_water_report_id);
        values.put("temperature", temperature);
        values.put("pH", pH);
        values.put("conductivity", conductivity);
        values.put("turbidity", turbidity);
        values.put("time", time);
        return getWritableDatabase().insert("water_report_samples", null, values);
    }

    public ArrayList<Water_Report_Sample> getWaterReportSamplesList(long id){
        ArrayList<Water_Report_Sample> waterReportSamples = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("water_report_samples", null, "fk_water_report_id = ?", new String[]{Long.toString(id)}, null, null, "_id ASC");
        if(cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    int idxId = cursor.getColumnIndex("_id");
                    int idxTime = cursor.getColumnIndex("time");
                    int idxTemperature = cursor.getColumnIndex("temperature");
                    int idxPH = cursor.getColumnIndex("pH");
                    int idxConductivity = cursor.getColumnIndex("conductivity");
                    int idxTurbidity = cursor.getColumnIndex("turbidity");
                    waterReportSamples.add(new Water_Report_Sample(
                            cursor.getLong(idxId),
                            cursor.getInt(idxTime),
                            cursor.getDouble(idxTemperature),
                            cursor.getDouble(idxPH),
                            cursor.getDouble(idxConductivity),
                            cursor.getDouble(idxTurbidity)
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return waterReportSamples;
    }

    public void deleteWaterReportById(long id){
        getWritableDatabase().delete("water_report", "_id = ?", new String[]{""+id});
        getWritableDatabase().delete("water_report_samples", "fk_water_report_id = ?", new String[]{""+id});
    }

    public void submittedWaterReportById(long id){
        ContentValues values = new ContentValues();
        values.put("submitted", 1);
        getWritableDatabase().update("water_report", values, "_id = ?", new String[]{""+id});
    }
}
