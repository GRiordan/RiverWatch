package com.vuw.project1.riverwatch.Report_functionality;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 3/10/2016.
 */
public class ImageReportDto {
    @SerializedName("geolocation")
    private final BasicLocation location;

    @SerializedName("name")
    private final String name;

    @SerializedName("tags")
    private final String[] tags;

    @SerializedName("description")
    private final String description;

    public ImageReportDto(IncidentReport report){
        location = report.getLocation();
        description = report.getDescription();
        name = report.getDate();
        tags = new String[1];
        tags[0] = report.getExtraText();
    }

}
