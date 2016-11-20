//*H****************************************************************************
// FILENAME:	ColorRecognition.java
//
// DESCRIPTION:
//  A class to help estimate a color from a set of colors
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

import java.util.HashMap;
import java.util.Map;

public class ColorRecognition {
    private HashMap<Double, Integer> concentrations = new HashMap<Double, Integer>();

    public ColorRecognition(HashMap<Double, Integer> concentrations){
        this.concentrations = concentrations;
    }

    public Map<Double, Double> processImage(Bitmap img) {
        HSBImage hsbSubImage = new HSBImage(img);
        HSBColor median = hsbSubImage.medianColor();
        return estimateFromImage(median);
    }

    private Map<Double, Double> estimateFromImage(HSBColor img){
        Map<Double, Double> distances = new HashMap<Double, Double>();
        for(Map.Entry<Double, Integer> nameColours : concentrations.entrySet()){
            Double name = nameColours.getKey();
            int rgbColor = nameColours.getValue();
            HSBColor hsbColor = new HSBColor(rgbColor);
            double hDist = hsbColor.h - img.h;
            double sDist = hsbColor.s - img.s;
            double bDist = hsbColor.b - img.b;
            double dist = Math.sqrt(hDist * hDist + sDist * sDist + bDist * bDist);
            distances.put(name, dist);
        }
        return distances;
    }
}
