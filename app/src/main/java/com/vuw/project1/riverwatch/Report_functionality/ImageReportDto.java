//*H****************************************************************************
// FILENAME:	ImageReportDto.java
//
// DESCRIPTION:
//  Used in constructing a object to send to the website
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
