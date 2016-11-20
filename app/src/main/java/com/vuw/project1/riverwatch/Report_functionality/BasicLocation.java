//*H****************************************************************************
// FILENAME:	BasicLocation.java
//
// DESCRIPTION:
//  Holds the information for location services
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

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alex on 04/09/2016.
 */


public class BasicLocation implements Serializable {
    @SerializedName("long")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

    public BasicLocation(double lat, double lon){
        longitude = lon;
        latitude = lat;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static BasicLocation fromJson(final String jsonLocation) {
        return new Gson().fromJson(jsonLocation, BasicLocation.class);
    }
}
