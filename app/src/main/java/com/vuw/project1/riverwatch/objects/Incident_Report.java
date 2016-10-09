package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 23/07/2016.
 */
public class Incident_Report {

    public long id;
    public String name;
    public double latitude;
    public double longitude;
    public String date;
    public String description;
    public String image;

    public Incident_Report(long id, String name, double latitude, double longitude, String date, String description, String image){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.image = image;
    }
}
