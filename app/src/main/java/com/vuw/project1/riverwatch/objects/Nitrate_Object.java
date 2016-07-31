package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 23/07/2016.
 */
public class Nitrate_Object {

    public long id;
    public String name;
    public String location;
    public float lat;
    public float lon;
    public String date;
    public String description;
    public String image;
    public double nitrate;
    public double nitrite;

    public Nitrate_Object(long id, String name, String location, String date, String description, String image, double nitrate, double nitrite){
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.image = image;
        this.nitrate = nitrate;
        this.nitrite = nitrite;
    }
}
