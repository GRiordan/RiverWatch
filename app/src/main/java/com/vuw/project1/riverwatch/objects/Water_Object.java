package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 23/07/2016.
 */
public class Water_Object {

    public long id;
    public String name;
    public String location;
    public float lat;
    public float lon;
    public String date;
    public String description;
    public String image;
    public double temperature;
    public double pH;
    public double conductivity;
    public double turbidity;

    public Water_Object(long id, String name, String location, String date, String description, String image, double temperature, double pH, double conductivity, double turbidity){
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.image = image;
        this.temperature = temperature;
        this.pH = pH;
        this.conductivity = conductivity;
        this.turbidity = turbidity;
    }
}
