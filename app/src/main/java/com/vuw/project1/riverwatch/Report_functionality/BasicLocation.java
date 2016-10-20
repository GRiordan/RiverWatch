package com.vuw.project1.riverwatch.Report_functionality;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alex on 04/09/2016.
 */


public class BasicLocation implements Serializable {
    @SerializedName("long")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

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

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static BasicLocation fromJson(final String jsonLocation) {
        return new Gson().fromJson(jsonLocation, BasicLocation.class);
    }
}
