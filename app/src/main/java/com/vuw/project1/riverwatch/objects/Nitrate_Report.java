//*H****************************************************************************
// FILENAME:	Nitrate_Report.java
//
// DESCRIPTION:
//  Holds information of a nitrate report
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
 * Created by James on 23/07/2016.
 */
public class Nitrate_Report {

    public long id;
    public String name;
    public double latitude;
    public double longitude;
    public String date;
    public String description;
    public String image;
    public double nitrate;
    public double nitrite;
    public int submitted;

    public Nitrate_Report(long id, String name, double latitude, double longitude, String date, String description, String image, double nitrate, double nitrite, int submitted){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.image = image;
        this.nitrate = nitrate;
        this.nitrite = nitrite;
        this.submitted = submitted;
    }
}
