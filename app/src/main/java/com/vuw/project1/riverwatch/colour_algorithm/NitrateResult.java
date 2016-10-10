package com.vuw.project1.riverwatch.colour_algorithm;


import com.vuw.project1.riverwatch.Report_functionality.BasicLocation;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * Created by George on 25/07/2016.
 */
public class NitrateResult {
    private Double nitrate;
    private Double nitrite;
    private String info;
    private BasicLocation location;
    private String imagePath;
    private String date;

    public NitrateResult(Double nitrate, Double nitrite, String info, String imagePath, String date){
        this.nitrate = nitrate;
        this.nitrite = nitrite;
        this.info = info;
//        this.location = location;
        this.imagePath = imagePath;
        this.date = date;
    }

    public TypedFile getImageTypedFile(){
        return new TypedFile("multipart/form-data", new File(imagePath));

    }

    public ReportDto getDTO(){
        return new ReportDto(this);

    }

    public Double getNitrate(){
        return nitrate;
    }

    public Double getNitrite(){
        return nitrite;
    }

    public String getInfo(){
        return info;
    }

    public BasicLocation getLocation(){
        return location;
    }

    public String getImagePath(){
        return imagePath;
    }

    public String getDate(){
        return date;
    }
}
