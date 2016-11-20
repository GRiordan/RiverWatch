//*H****************************************************************************
// FILENAME:	Location.java
//
// DESCRIPTION:
//  A location class in the format needed by the intended service
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



package com.vuw.project1.riverwatch.bluetooth;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * A simple location class in the format needed by the intended service
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 23-Sep-15.
 */
public class Location implements Serializable {
    @SerializedName("lat")
    private final double latitude;

    @SerializedName("long")
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Location fromJson(final String jsonLocation) {
        return new Gson().fromJson(jsonLocation, Location.class);
    }
}
