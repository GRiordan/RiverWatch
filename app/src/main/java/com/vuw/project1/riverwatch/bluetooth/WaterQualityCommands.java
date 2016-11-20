//*H****************************************************************************
// FILENAME:	WaterQualityCommands.java
//
// DESCRIPTION:
//  A class that handles all the commands that are sent to the hardware device
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


import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Shane on 31/08/15.
 */
public class WaterQualityCommands {

    private static final String TAG = WaterQualityCommands.class.getSimpleName();

    private static final String DEVICE_ID = "DID";
    private static final String SAMPLE_ID = "SID";
    private static final String CONDUCTIVITY = "EC";
    private static final String PH = "pH";
    private static final String TURBIDITY = "Trb";
    private static final String TIME_SINCE_LAST = "TSL";
    private static final String TEMPERATURE = "Tmp";
    private static final String TSL = "TSL";

    public static String getRetrieveDataCommand(){
        return "RetrieveData";
    }

    public static String getTestCommand() {
        return "Test";
    }

    public static String getStatusCommand(){
        return "Status";
    }

    /**
     * Formats the message from water quality device.
     * @param message - message from device
     * @return - may return null if json not formatted correctly.
     */
    public static JSONObject formatRetiredData(String message){
        //Log.i(TAG, message);
        message = message.replace("[databegin]","");
        message = message.replace("[dataend]","");
        try {
            return new JSONObject(message);
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    public static List<Sample> makeReportList(JSONObject waterQualityData, Location location){
        List<Sample> list = new ArrayList<>();

        Date dateTime = GregorianCalendar.getInstance().getTime();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateTime);
        Log.i(TAG, dateTime.toString());
        try {
            //int timeSinceLast = waterQualityData.getInt("TimeSinceLast");
            //calendar.add(GregorianCalendar.MINUTE, timeSinceLast * (-1));
            Date time = calendar.getTime();
            if (waterQualityData.has("samples")) {
                //String status = waterQualityData.getString("status");
                Log.i(TAG, time.toString());
                JSONArray array = waterQualityData.getJSONArray("samples");
                for (int i = array.length() - 1; i >= 0; i--) {
                    JSONObject json = array.getJSONObject(i);
                    Log.i(TAG, json.toString());
                    list.add(new Sample(
                            time,
                            new com.vuw.project1.riverwatch.bluetooth.Location(location.getLatitude(), location.getLongitude()),
                            json.getDouble(CONDUCTIVITY),
                            json.getDouble(TEMPERATURE),
                            json.getDouble(TURBIDITY),
                            json.getDouble(PH),
                            json.getDouble(TSL)));

                    calendar.add(GregorianCalendar.MINUTE, (int) (json.getDouble(TIME_SINCE_LAST) * (-1)));
                    time = calendar.getTime();

                }
            }
            else {
                list.add(new Sample(
                        time,
                        new com.vuw.project1.riverwatch.bluetooth.Location(location.getLatitude(), location.getLongitude()),
                        waterQualityData.getDouble(CONDUCTIVITY),
                        waterQualityData.getDouble(TEMPERATURE),
                        waterQualityData.getDouble(TURBIDITY),
                        waterQualityData.getDouble(PH),
                        waterQualityData.getDouble(TSL)));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        return list;
    }

}
