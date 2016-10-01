package com.vuw.project1.riverwatch.service;

import android.os.AsyncTask;

import java.io.File;
import java.io.Serializable;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.mime.TypedFile;

/**
 * Controls the service requests and responses
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 10-Aug-15.
 */
public class ServiceBroker {
    public interface ServiceCallbacks {
        void OnReportSentResponse(final Boolean response);
    }

    private static final String END_POINT = "wainz.org.nz";
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
    public void sendReport(final BaseReport report) {
        new SendReportTask().execute(report);
    }

    private Boolean sendReportToServer(final BaseReport report) {
        final ResponseDto response;
        if (report instanceof ImageReport) {
            response = service.postReport(new ImageReportDto((ImageReport) report), new TypedFile(IMAGE_MIME_TYPE, new File(((ImageReport) report).getImagePath())));
        } else {
            response = service.postReport(new WaterQualityReportDto((WaterQualityReport) report));
        }

        final boolean success = response.hasSentSuccessfully();
        report.reportSent(success);
        return success;
    }

    private static final class SendReportTask extends AsyncTask<BaseReport, Void, Boolean> implements Serializable {

        @Override
        protected Boolean doInBackground(final BaseReport... reports) {
            return App.getServiceBroker().sendReportToServer(reports[0]);
        }

        @Override
        protected void onPostExecute(final Boolean response) {
            super.onPostExecute(response);


            App.getServiceBroker().callbacks.OnReportSentResponse(response);
        }
    }
}
