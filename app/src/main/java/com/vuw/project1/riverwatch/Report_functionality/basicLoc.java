package com.vuw.project1.riverwatch.Report_functionality;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alex on 04/09/2016.
 */


public class BasicLoc implements Serializable {
    @SerializedName("lat")
    private double longitude;
    @SerializedName("long")
    private double latitude;

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

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static BasicLoc fromJson(final String jsonLocation) {
        return new Gson().fromJson(jsonLocation, BasicLoc.class);
    }
}
