//*H****************************************************************************
// FILENAME:	NitrateResult.java
//
// DESCRIPTION:
//  A class to hold all the information of one nitrate/nitrite test
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

    public NitrateResult(Double nitrate, Double nitrite, String info,BasicLocation location, String imagePath, String date){
        this.nitrate = nitrate;
        this.nitrite = nitrite;
        this.info = info;
        this.location = location;
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
