//*H****************************************************************************
// FILENAME:	App.java
//
// DESCRIPTION:
//  Custom app class to allow for global use of certain variables, and
//  allow for extra reusability and uniformity.
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


import android.app.Application;

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
