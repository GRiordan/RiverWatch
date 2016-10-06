package com.vuw.project1.riverwatch.colour_algorithm;

import com.google.gson.annotations.SerializedName;
import com.vuw.project1.riverwatch.Report_functionality.BasicLoc;

/**
 * Created by George on 6/10/2016.
 */

public class ReportDto {

    @SerializedName("geolocation")
    private final BasicLoc location;

    @SerializedName("name")
    private final String name;

    @SerializedName("tags")
    private final String[] tags;

    @SerializedName("description")
    private final String description;

    public ReportDto(NitrateResult report){
        location = report.getLocation();
        description = report.getInfo();
        name = report.getDate();
        tags = new String[1];
        tags[0] = "placeholder";
    }

}