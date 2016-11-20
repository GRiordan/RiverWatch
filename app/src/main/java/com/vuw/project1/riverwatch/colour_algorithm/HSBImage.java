//*H****************************************************************************
// FILENAME:	HSBImage.java
//
// DESCRIPTION:
//  A class to hold HSB color values of a bitmap
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

public class HSBImage {
    private HSBColor[] data;

    public HSBImage(Bitmap img){
        int[] colours = new int[img.getWidth() * img.getHeight()];
        img.getPixels(colours, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        data = new HSBColor[colours.length];
        for(int i = 0;i < colours.length;i ++){
            float[] vals = new float[3];
            Color.colorToHSV(colours[i], vals);
            data[i] = new HSBColor(vals[0], vals[1], vals[2]);
        }
    }

    public HSBColor medianColor(){
        float[] hArr =	new float[data.length];
        float[] sArr =	new float[data.length];
        float[] bArr =	new float[data.length];
        for(int i = 0;i < data.length;i ++){
            hArr[i] = data[i].h;
            sArr[i] = data[i].s;
            bArr[i] = data[i].b;
        }
        Arrays.sort(hArr);
        Arrays.sort(sArr);
        Arrays.sort(bArr);
        float hMed = hArr[data.length / 2];
        float sMed = sArr[data.length / 2];
        float bMed = bArr[data.length / 2];
        return new HSBColor(hMed, sMed, bMed);
    }
}
