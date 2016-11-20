//*H****************************************************************************
// FILENAME:	 ScreenDimensions.java
//
// DESCRIPTION:
//  Holds the information about the phones screen dimensions
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

package com.vuw.project1.riverwatch.Report_functionality;


import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Fixed by Alex on 18/07/2016.
 */
public class ScreenDimensions {
    private static float screenWidth;
    private static float screenHeight;
    private static float screenCenterX;
    private static float screenCenterY;

    public ScreenDimensions(Context context) {
        Point dimens = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(dimens);

        screenWidth = dimens.x;
        screenHeight = dimens.y;
        screenCenterX = screenWidth / 2;
        screenCenterY = screenHeight / 2;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenCenterX() {
        return screenCenterX;
    }

    public float getScreenCenterY() {
        return screenCenterY;
    }
}
