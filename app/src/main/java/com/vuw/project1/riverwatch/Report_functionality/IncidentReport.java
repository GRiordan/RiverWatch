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

    public IncidentReport(String descT, String extraT, String imageP, BasicLoc loc){
        descText = descT;
        extraText = extraT;
        imagePath = imageP;
        location = loc;
    }


    public TypedFile getImageTypedFile(){
        return new TypedFile("multipart/form-data", new File(imagePath));

    }

    public ImageReportDto getDTO(){
        return new ImageReportDto(this);

    }
}
