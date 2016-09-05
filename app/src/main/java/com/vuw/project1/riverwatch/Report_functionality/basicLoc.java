package com.vuw.project1.riverwatch.Report_functionality;

/**
 * Created by Alex on 04/09/2016.
 */
public class BasicLoc {
    double longitude;
    double latitude;

    public BasicLoc(double lat, double lon){
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
