package com.vuw.project1.riverwatch.objects;


/**
 * Created by James on 23/07/2016.
 */
public class Nitrate_Report {

    public long id;
    public String name;
    public double latitude;
    public double longitude;
    public String date;
    public String description;
    public String image;
    public double nitrate;
    public double nitrite;

    public Nitrate_Report(long id, String name, double latitude, double longitude, String date, String description, String image, double nitrate, double nitrite){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.image = image;
        this.nitrate = nitrate;
        this.nitrite = nitrite;
    }
}
