package com.vuw.project1.riverwatch.service;


import android.app.Application;
import android.widget.Toast;

import com.vuw.project1.riverwatch.util.DateHelper;


/**
 * Custom app class to allow for global use of certain variables, and
 * allow for extra reusability and uniformity.
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 24/07/15.
 */
public class App extends Application implements ServiceBroker.ServiceCallbacks {
    private static ServiceBroker serviceBroker;
    private static App instance;

    public App(){
        instance = this;
        serviceBroker = new ServiceBroker(this);

        new DateHelper();

    }

    @Override
    public void onCreate() {
        super.onCreate();


        instance = this;
        serviceBroker = new ServiceBroker(this);

        checkForUnsentReports();
    }

    public static App getInstance() {
        return instance;
    }

    public static ServiceBroker getServiceBroker() {
        return serviceBroker;
    }

    private void checkForUnsentReports() {
        if (!NetworkChecker.checkNetworkConnected(this)) {
            return;
        }/*
        List<BaseReport> reports = null;
        try {
            reports = ReportStorage.retrieveReportsList(this, false);
            reports.addAll(ReportStorage.retrieveReportsList(this, true));
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }

        if (reports != null) {
            for (BaseReport report : reports) {
                if (!report.getSent()) {
                    serviceBroker.sendReport(report);
                }
            }
        }*/
    }

    @Override
    public void OnReportSentResponse(final Boolean response) {
        final String message = response ? "A report has been sent" : "A report failed to send";
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
