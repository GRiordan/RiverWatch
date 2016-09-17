package com.vuw.project1.riverwatch.bluetooth;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A simple location class in the format needed by the intended service
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 23-Sep-15.
 */
public class Location implements Serializable {
    @SerializedName("lat")
    private final double latitude;

    @SerializedName("long")
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Location fromJson(final String jsonLocation) {
        return new Gson().fromJson(jsonLocation, Location.class);
    }
}
