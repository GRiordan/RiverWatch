package com.vuw.project1.riverwatch.service;

import fantasticfive.riverwatch.models.ImageReportDto;
import fantasticfive.riverwatch.models.ResponseDto;
import fantasticfive.riverwatch.models.WaterQualityReportDto;
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

    @POST("/api/waterquality")
    ResponseDto postReport(@Body WaterQualityReportDto report);
}
