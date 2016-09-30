package com.vuw.project1.riverwatch.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * A simple class to check if network is connected
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 04-Oct-15.
 */
public class NetworkChecker {

    public static boolean checkNetworkConnected(final Context context) {
        final NetworkInfo network = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return network != null && network.isConnected();
    }
}
