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

    public static List<Sample> makeReportList(JSONObject waterQualityData){
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
                            json.getDouble(CONDUCTIVITY),
                            json.getDouble(TEMPERATURE),
                            json.getDouble(TURBIDITY),
                            json.getDouble(PH)));

                    calendar.add(GregorianCalendar.MINUTE, (int) (json.getDouble(TIME_SINCE_LAST) * (-1)));
                    time = calendar.getTime();

                }
            }
            else {
                list.add(new Sample(
                        waterQualityData.getDouble(CONDUCTIVITY),
                        waterQualityData.getDouble(TEMPERATURE),
                        waterQualityData.getDouble(TURBIDITY),
                        waterQualityData.getDouble(PH)));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

        return list;
    }

}
