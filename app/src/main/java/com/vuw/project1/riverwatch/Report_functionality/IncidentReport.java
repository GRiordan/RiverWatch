package com.vuw.project1.riverwatch.Report_functionality;

import java.io.File;

import retrofit.mime.TypedFile;

/**
 * Created by Alex on 3/10/2016.
 */
public class IncidentReport {

    private String descText;
    private String extraText;
    private String imagePath;
    private BasicLoc location;
    private String date;

    public IncidentReport(String descT, String extraT, String imageP, BasicLoc loc,String date){
        descText = descT;
        extraText = extraT;
        imagePath = imageP;
        location = loc;
        this.date = date;
    }


    public TypedFile getImageTypedFile(){
        return new TypedFile("multipart/form-data", new File(imagePath));

    }

    public ImageReportDto getDTO(){
        return new ImageReportDto(this);

    }

    public String getDescription(){
        return descText;
    }

    public BasicLoc getLocation(){
        return location;
    }

    public String getExtraText(){
        return extraText;
    }

    public String getDate(){
        return date;
    }

}
