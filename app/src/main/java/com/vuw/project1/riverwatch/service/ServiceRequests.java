//*H****************************************************************************
// FILENAME:	ServiceRequests.java
//
// DESCRIPTION:
//  This class contains the service requests allowing the app
//  to send submit reports
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

package com.vuw.project1.riverwatch.service;


import com.vuw.project1.riverwatch.Report_functionality.ImageReportDto;
import com.vuw.project1.riverwatch.bluetooth.WaterQualityReport;
import com.vuw.project1.riverwatch.colour_algorithm.ReportDto;

import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * This class contains the service requests allowing the app
 * to send submit reports
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 14-Sep-15.
 */
public interface ServiceRequests {

    @Multipart
    @POST("/api/image")
    ResponseDto postReport(@Part("data") ImageReportDto report,
                           @Part("image") TypedFile image
    );

    @Multipart
    @POST("/api/chemicaldata")
    ResponseDto postReport(@Part("data") ReportDto report,
                           @Part("image") TypedFile image
    );

    @POST("/api/waterquality")
    ResponseDto postReport(@Body WaterQualityReport report);
}
