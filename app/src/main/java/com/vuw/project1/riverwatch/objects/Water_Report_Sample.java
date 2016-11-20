//*H****************************************************************************
// FILENAME:	Incident_Report.java
//
// DESCRIPTION:
//  Holds information of a water report
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

package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 8/10/2016.
 */

public class Water_Report_Sample {
    public long id;
    public int time;
    public double temperature;
    public double pH;
    public double conductivity;
    public double turbidity;

    public Water_Report_Sample(long id, int time, double temperature, double pH, double conductivity, double turbidity){
        this.id = id;
        this.time = time;
        this.temperature = temperature;
        this.pH = pH;
        this.conductivity = conductivity;
        this.turbidity = turbidity;
    }
}
