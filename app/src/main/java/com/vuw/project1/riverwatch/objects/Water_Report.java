package com.vuw.project1.riverwatch.objects;


/**
 * Created by James on 23/07/2016.
 */
public class Water_Report {

    public long id;
    public String name;
    public String location;
    public double latitude;
    public double longitude;
    public String date;
    public String description;
    public String image;
    public double temperature;
    public double pH;
    public double conductivity;
    public double turbidity;

    public Water_Report(long id, String name, String location, double latitude, double longitude, String date, String description, String image, double temperature, double pH, double conductivity, double turbidity){
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.image = image;
        this.temperature = temperature;
        this.pH = pH;
        this.conductivity = conductivity;
        this.turbidity = turbidity;
    }
}
