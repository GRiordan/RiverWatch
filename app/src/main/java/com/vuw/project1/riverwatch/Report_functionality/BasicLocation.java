package com.vuw.project1.riverwatch.Report_functionality;

/**
 * Created by Alex on 04/09/2016.
 */
public class BasicLocation {
    double longitude;
    double latitude;

    public BasicLocation(double lat, double lon){
        longitude = lon;
        latitude = lat;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }
}
