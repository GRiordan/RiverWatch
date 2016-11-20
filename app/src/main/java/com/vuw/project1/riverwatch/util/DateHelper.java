//*H****************************************************************************
// FILENAME:	DateHelper.java
//
// DESCRIPTION:
//  A helper class for Android Date
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

}
