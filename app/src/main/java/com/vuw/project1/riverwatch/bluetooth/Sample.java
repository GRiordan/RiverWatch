package com.vuw.project1.riverwatch.bluetooth;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import com.vuw.project1.riverwatch.util.DateHelper;

import java.io.Serializable;
import java.util.Date;


/**
 * A class to hold an individual sample for water quality reports
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 01-Oct-15.
 */
public class Sample implements Serializable {
    @SerializedName("sampled")
    private final String date;

    @SerializedName("geolocation")
    private final Location location;

    @SerializedName("conductivity")
    private final double conductivity;

    @SerializedName("temperature")
    private final double temperature;

    @SerializedName("turbidity")
    private final double turbidity;

    @SerializedName("ph")
    private final double ph;

    public Sample(final Date sampled, final Location location, final double conductivity,
                  final double temperature, final double turbidity, final double ph) {
        this.date = DateHelper.dateToServerFormat(sampled);
        this.location = location;
        this.conductivity = conductivity;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.ph = ph;
    }

    public double getConductivity() {
        return conductivity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public double getPh() {
        return ph;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Sample fromJson(final String jsonSample) {
        return new Gson().fromJson(jsonSample, Sample.class);
    }
}
