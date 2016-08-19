package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 23/07/2016.
 */
public class Incident_Report {

    public long id;
    public String name;
    public String location;
    public float lat;
    public float lon;
    public String date;
    public String description;
    public String image;

    public Incident_Report(long id, String name, String location, String date, String description, String image){
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.image = image;
    }
}
