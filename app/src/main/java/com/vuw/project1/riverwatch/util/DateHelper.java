package com.vuw.project1.riverwatch.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A helper class for Android Date
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 10-Aug-15.
 */
public final class DateHelper {
    private static final String DATE_FORMAT_DISPLAY = "dd/MM/yyyy";
    private static final String DATE_FORMAT_SERVER = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat serverFormat;
    private static SimpleDateFormat displayFormat;

    public DateHelper() {
        serverFormat = new SimpleDateFormat(DATE_FORMAT_SERVER, Locale.ENGLISH);
        displayFormat = new SimpleDateFormat(DATE_FORMAT_DISPLAY, Locale.ENGLISH);
    }

    public static String dateToServerFormat(final Date date) {
        return serverFormat.format(date) + "+00:00";
    }

    public static String dateToString(final Date date) {
        return displayFormat.format(date);
    }
}
