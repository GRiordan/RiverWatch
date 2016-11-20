//*H****************************************************************************
// FILENAME:	StripOverlay.java
//
// DESCRIPTION:
//  A class that is used to construct the overlay for the camera activity
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

package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

/**
 * Repurposed from opera riverwatch team 2015
 * Created by colin on 27/09/15.
 */
public class StripOverlay extends View {

    private int width, height;
    private Rect stripRectangle;
    private HashMap<String, Rect> captureRectangles;

    public StripOverlay(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight){
        this.width = width;
        this.height = height;
        float heightMod = 11F;
        float stripHeight = height / heightMod;
        float startWidth = ((float)width) / 4;
        float blockWidth = stripHeight;
        stripRectangle = new Rect(0, (int)(stripHeight * Math.floor(heightMod / 2)), width, (int)(stripHeight * Math.ceil(heightMod / 2)));
    }

    public Rect getStripRectangle(){

        Log.d("Cats", "" + stripRectangle.left + "," + stripRectangle.top + "," + stripRectangle.right + "," + stripRectangle.bottom);

        int cameraLeft = (int)(((float)stripRectangle.left) / width * 2000 - 1000);
        int cameraTop = (int)(((float)stripRectangle.top) / height * 2000 - 1000);
        int cameraRight = (int)(((float)stripRectangle.right) / width * 2000 - 1000);
        int cameraBottom = (int)(((float)stripRectangle.bottom) / height * 2000 - 1000);

        return new Rect(cameraLeft,cameraTop,cameraRight,cameraBottom);
    }

    public HashMap<String,Rect> getCaptureRectangles(){
        return captureRectangles;
    }

    @Override
    public void onDraw(Canvas canvas){
        Rect leftCaptureRectangle, middleCaptureRectangle, rightCaptureRectangle;
        int stripHeight = (int)(1./11 * height);

        Paint p = new Paint();
        p.setColor(0x99FFFFFF);

        Rect middleWhite = new Rect();
        middleWhite.left = width / 2 - stripHeight;
        middleWhite.top = 5 * stripHeight;
        middleWhite.right = width / 2 + stripHeight;
        middleWhite.bottom = 6 * stripHeight;
        canvas.drawRect(middleWhite, p);
        middleCaptureRectangle = middleWhite;

        Rect leftwhite = new Rect();
        leftwhite.left = 0;
        leftwhite.top = 5 * stripHeight;
        leftwhite.right = width / 2 - 2*stripHeight;
        leftwhite.bottom = 6*stripHeight;
        canvas.drawRect(leftwhite, p);

        Rect rightwhite = new Rect();
        rightwhite.left = width/2 + 2*stripHeight;
        rightwhite.top = 5 * stripHeight;
        rightwhite.right = (int)(rightwhite.left + width/11.);
        rightwhite.bottom = 6*stripHeight;
        canvas.drawRect(rightwhite, p);

        p.setStyle(Paint.Style.STROKE);
        Rect border = new Rect();
        border.left = 0;
        border.top = 5 * stripHeight;
        border.right = rightwhite.right;
        border.bottom = 6*stripHeight;
        canvas.drawRect(border, p);

        leftCaptureRectangle = new Rect();
        rightCaptureRectangle = new Rect();

        leftCaptureRectangle.left = leftwhite.right;
        leftCaptureRectangle.top = leftwhite.top;
        leftCaptureRectangle.bottom = leftwhite.bottom;
        leftCaptureRectangle.right = middleWhite.left;

        rightCaptureRectangle.left = middleWhite.right;
        rightCaptureRectangle.top = middleWhite.top;
        rightCaptureRectangle.bottom = middleWhite.bottom;
        rightCaptureRectangle.right = rightwhite.left;

        captureRectangles = new HashMap<>();
        captureRectangles.put("leftCaptureRectangle",leftCaptureRectangle);
        captureRectangles.put("rightCaptureRectangle",rightCaptureRectangle);
        captureRectangles.put("middleCaptureRectangle",middleCaptureRectangle);
    }
}
