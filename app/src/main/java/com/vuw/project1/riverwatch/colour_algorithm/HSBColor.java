//*H****************************************************************************
// FILENAME:	HSBColor.java
//
// DESCRIPTION:
//  Represents a color in Hue, Brightness and Saturation space
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


import android.graphics.Color;

public class HSBColor {
    //The components of this HSB colour
    public float h, s, b;

    /**
     * Constructs an HSB Colour from the given components
     * @param _h The Hue of this HSB Colour
     * @param _s The Saturation of this HSB Colour
     * @param _b The Brightness of this HSB Colour
     */
    public HSBColor(float _h, float _s, float _b){
        h = _h;
        s = _s;
        b = _b;
    }

    /**
     * Constructs an HSB Colour from the given RGB Colour as an int
     * @param rgb An int representation of the RGB Colour
     */
    public HSBColor(int rgb){
        //Some bit shifting to extract the components
        float[] data = new float[3];
        System.err.println("RGB: " + rgb);
        Color.colorToHSV(rgb, data);
        h = data[0];
        s = data[1];
        b = data[2];
    }

    /**
     * Returns the difference between this HSB Colour and the other
     * @param other The HSBColour to take the difference between
     * @return The difference between this HSBColour and the given one
     */
    public HSBColor differenceFrom(HSBColor other) {
        return new HSBColor(this.h - other.h, this.s - other.s, this.b - other.b);
    }

    public float floatDifferenceFrom(HSBColor other) {
        return Math.abs(this.h - other.h) + Math.abs(this.s - other.s) + Math.abs(this.b - other.b);
    }

    public String toString(){
        return "H: " + h + " S: " + s + " B: " + b;
    }
}
