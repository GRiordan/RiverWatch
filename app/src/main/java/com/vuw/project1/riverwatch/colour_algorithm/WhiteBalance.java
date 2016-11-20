//*H****************************************************************************
// FILENAME:	WhiteBalance.java
//
// DESCRIPTION:
//  Takes in a bitmap of the image taken and a bitmap of the white point
//  Outputs a white balanced bitmap of the image
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


import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Arrays;

public class WhiteBalance {

    public Bitmap balance(Bitmap fullImage, Bitmap whiteSpot){
        System.out.printf("Image Dimensions: %dx%d\n", fullImage.getWidth(), fullImage.getHeight());

        int whiteMedian = getMedianColor(whiteSpot);
        //Calculate the white corrected value
        double weightedRed = Color.red(whiteMedian);
        double weightedGreen = Color.green(whiteMedian);
        double weightedBlue = Color.blue(whiteMedian);

        int[] fullRGB = new int[fullImage.getWidth() * fullImage.getHeight()];

        fullImage.getPixels(fullRGB, 0, fullImage.getWidth(), 0, 0, fullImage.getWidth(), fullImage.getHeight());

        for(int y = 0;y < fullImage.getHeight();y ++){
            for(int x = 0;x < fullImage.getWidth();x ++){
                int index = y * fullImage.getWidth() + x;
                int oldRed   = (fullRGB[index] >> 16) & 0xFF;
                int oldGreen = (fullRGB[index] >> 8) & 0xFF;
                int oldBlue  = (fullRGB[index] >> 0) & 0xFF;
                int newRed   = (int) Math.min(oldRed * (255. / weightedRed), 255);
                int newGreen = (int) Math.min(oldGreen * (255. / weightedGreen), 255);
                int newBlue   = (int) Math.min(oldBlue * (255. / weightedBlue), 255);
                int newCol = (0xFF << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                fullRGB[index] = newCol;
            }
        }
        Bitmap newImg = Bitmap.createBitmap(fullImage);
        newImg.setPixels(fullRGB,0,fullImage.getWidth(),0,0,fullImage.getWidth(),fullImage.getHeight());

        return newImg;
    }

    private int getMedianColor(Bitmap img){
        int pointCount = img.getWidth() * img.getHeight();

        int[] rgbs = new int[pointCount];
        int[] reds = new int[pointCount];
        int[] greens = new int[pointCount];
        int[] blues = new int[pointCount];

        img.getPixels(rgbs,0,img.getWidth(),0,0,img.getWidth(),img.getHeight());
//		img.getRGB(0, 0, img.getWidth(), img.getHeight(), rgbs, 0, img.getWidth());
        for(int i = 0;i < rgbs.length;i ++){
            int rgb = rgbs[i];
            reds[i]   = (rgb >> 16) & 0xFF;
            greens[i] = (rgb >> 8) & 0xFF;
            blues[i]  = (rgb >> 0) & 0xFF;
        }

        Arrays.sort(reds);
        Arrays.sort(greens);
        Arrays.sort(blues);

        //Find the average of the inter quartile range
        int r = 0, g = 0, b = 0;
        int lowerQuartile = (int) Math.floor(pointCount / 4.0);
        int upperQuartile = (int) Math.floor(3.0 * (pointCount / 4.0));

        int quartileRange = upperQuartile - lowerQuartile + 1;
        for (int i = lowerQuartile; i <= upperQuartile; i++){
            r += reds[i];
            g += greens[i];
            b += blues[i];
        }

        return Color.rgb(r / quartileRange, g / quartileRange, b / quartileRange);
    }
}
