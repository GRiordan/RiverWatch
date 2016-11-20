//*H****************************************************************************
// FILENAME:	IncidentReport.java
//
// DESCRIPTION:
//  Holds all the information of an incident report
//
//  A list of names of copyright information is provided in the README
//
//    This file is part of RiverWatch.
//
//    RiverWatch is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    RiverWatch is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with RiverWatch.  If not, see <http://www.gnu.org/licenses/>.
//
// CHANGES:
// DATE			WHO	    DETAILS
// 20/11/1995	George	Added header.
//
//*H*

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
    private BasicLocation location;
    private String date;

    public IncidentReport(String descT, String extraT, String imageP, BasicLocation loc,String date){
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

    public BasicLocation getLocation(){
        return location;
    }

    public String getExtraText(){
        return extraText;
    }

    public String getDate(){
        return date;
    }

}
