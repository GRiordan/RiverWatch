//*H****************************************************************************
// FILENAME:	ServiceBroker.java
//
// DESCRIPTION:
//  Controls the service requests and responses
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


import android.os.AsyncTask;

import com.vuw.project1.riverwatch.Report_functionality.ImageReportDto;
import com.vuw.project1.riverwatch.Report_functionality.IncidentReport;
import com.vuw.project1.riverwatch.bluetooth.WaterQualityReport;
import com.vuw.project1.riverwatch.colour_algorithm.NitrateResult;
import com.vuw.project1.riverwatch.colour_algorithm.ReportDto;

import java.io.Serializable;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Controls the service requests and responses
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 10-Aug-15.
 */
public class ServiceBroker {
    public interface ServiceCallbacks {
        void OnReportSentResponse(final Boolean response);
    }

    //private static final String END_POINT = "http://wainz.org.nz";
    //for testing purposes
    private static final String END_POINT = "https://www.wainz.org.nz";
    private static final String IMAGE_MIME_TYPE = "application/pdf";

    private ServiceCallbacks callbacks;
    private ServiceRequests service;

    public ServiceBroker(final App instance) {
        callbacks = instance;

        service = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("debug"))
                .build()
                .create(ServiceRequests.class);
    }

    /**
     * The gui is to call this method, creating an async task
     * that will send the report to the server
     *
     * @param report
     */
    public void sendReport(final WaterQualityReport report) {
        new SendReportTask().execute(report);
    }

    public void sendReport(final IncidentReport report){
        new SendIncidentReportTask().execute(report);
    }

    public void sendReport(final NitrateResult report){
        new SendNitrateReportTask().execute(report);
    }


    private Boolean sendReportToServer(final WaterQualityReport report) {

        ResponseDto response = null;

        try {
            response = service.postReport(report);
        }
        catch (RuntimeException e){
            e.printStackTrace();
        }

        // TODO Change this back when timeout error is fixed
        //final boolean success = response.hasSentSuccessfully();
        //report.reportSent(success);
        //return success
        return true;
    }

    private Boolean sendReportToServer(final IncidentReport report){
        ImageReportDto dtoReport = report.getDTO();
        ResponseDto response = service.postReport(dtoReport, report.getImageTypedFile());

        final boolean success = response.hasSentSuccessfully();

        return success;

    }

    private Boolean sendReportToServer(final NitrateResult report) {
        ReportDto dtoReport = report.getDTO();
        ResponseDto response = service.postReport(dtoReport, report.getImageTypedFile());


        final boolean success = response.hasSentSuccessfully();
        //report.reportSent(success);
        return success;
    }

    private static final class SendNitrateReportTask extends AsyncTask<NitrateResult, Void, Boolean> implements Serializable{
        @Override
        protected Boolean doInBackground(final NitrateResult... reports){
            return App.getServiceBroker().sendReportToServer(reports[0]);
        }

        @Override
        protected void onPostExecute(final Boolean response) {
            super.onPostExecute(response);

            App.getServiceBroker().callbacks.OnReportSentResponse(response);
        }
    }

    private static final class SendIncidentReportTask extends AsyncTask<IncidentReport, Void, Boolean> implements Serializable{
        @Override
        protected Boolean doInBackground(final IncidentReport... reports){
            return App.getServiceBroker().sendReportToServer(reports[0]);
        }

        @Override
        protected void onPostExecute(final Boolean response) {
            super.onPostExecute(response);

            App.getServiceBroker().callbacks.OnReportSentResponse(response);
        }
    }

    private static final class SendReportTask extends AsyncTask<WaterQualityReport, Void, Boolean> implements Serializable {

        @Override
        protected Boolean doInBackground(final WaterQualityReport... reports) {
            return App.getServiceBroker().sendReportToServer(reports[0]);
        }

        @Override
        protected void onPostExecute(final Boolean response) {
            super.onPostExecute(response);

            App.getServiceBroker().callbacks.OnReportSentResponse(response);
        }
    }
}
