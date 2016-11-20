//*H****************************************************************************
// FILENAME:	ReportDto.java
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

package com.vuw.project1.riverwatch.colour_algorithm;


import com.google.gson.annotations.SerializedName;
import com.vuw.project1.riverwatch.Report_functionality.BasicLocation;

/**
 * Created by George on 6/10/2016.
 */

public class ReportDto {

    @SerializedName("geolocation")
    private final BasicLocation location;

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