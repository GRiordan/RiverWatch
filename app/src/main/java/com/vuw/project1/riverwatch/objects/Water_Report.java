package com.vuw.project1.riverwatch.objects;


/**
 * Created by James on 23/07/2016.
 */
public class Water_Report {

    public long id;
    public String name;
    public double latitude;
    public double longitude;
    public String date;
    public int submitted;

    public Water_Report(long id, String name, double latitude, double longitude, String date, int submitted){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.submitted = submitted;
    }
}
