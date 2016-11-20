//*H****************************************************************************
// FILENAME:	Sample.java
//
// DESCRIPTION:
//  A class to hold an individual sample for water quality reports
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
import com.vuw.project1.riverwatch.util.DateHelper;

import java.io.Serializable;
import java.util.Date;


/**
 * A class to hold an individual sample for water quality reports
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 01-Oct-15.
 */
public class Sample implements Serializable {
    @SerializedName("sampled")
    private final String date;

    @SerializedName("geolocation")
    private final Location location;

    @SerializedName("conductivity")
    private final double conductivity;

    @SerializedName("temperature")
    private final double temperature;

    @SerializedName("turbidity")
    private final double turbidity;

    @SerializedName("ph")
    private final double ph;

    @SerializedName("tsl")
    private final double tsl;

    public Sample(final Date sampled, final Location location, final double conductivity,
                  final double temperature, final double turbidity, final double ph, final double tsl) {
        this.date = DateHelper.dateToServerFormat(sampled);
        this.location = location;
        this.conductivity = conductivity;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.ph = ph;
        this.tsl = tsl;
    }

    public Sample(final String sampled, final Location location, final double conductivity,
                  final double temperature, final double turbidity, final double ph, final double tsl) {
        this.date = sampled;
        this.location = location;
        this.conductivity = conductivity;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.ph = ph;
        this.tsl = tsl;
    }

    public double getConductivity() {
        return conductivity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public double getPh() {
        return ph;
    }

    public double getTsl(){
        return tsl;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Sample fromJson(final String jsonSample) {
        return new Gson().fromJson(jsonSample, Sample.class);
    }
}
