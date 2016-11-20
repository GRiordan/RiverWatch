//*H****************************************************************************
// FILENAME:	WaterQualityReport.java
//
// DESCRIPTION:
//  A report to be sent to the server that is retrieved via bluetooth device
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

package com.vuw.project1.riverwatch.bluetooth;


import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Date;
import java.util.List;


/**
 * A report to be sent to the server that is retrieved via bluetooth device
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 21-Sep-15.
 */
public class WaterQualityReport {
    private enum Health {
        Terrible("Terrible", Color.RED),
        Average("Average", Color.YELLOW),
        Good("Good", Color.GREEN);

        private final String msg;
        private final int color;

        Health(final String msg, final int color) {
            this.msg = msg;
            this.color = color;
        }
    }
    private final List<Sample> samples;
    private Health health;
    private double averageConductivity;
    private double averageTemperature;
    private double averageTurbidity;
    private double averagePh;

    public WaterQualityReport(final Location location, final Date date, final List<Sample> samples,
                              final boolean sent) {

        this.samples = samples;
        findAverages();
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public CharSequence getName() {
        final SpannableString statusSpan = new SpannableString("Health: " + health.msg);
        statusSpan.setSpan(new ForegroundColorSpan(health.color), statusSpan.length() - health.msg.length(), statusSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return statusSpan;
    }

    public CharSequence getDescription() {
        final String descriptionString =
                "Averages from " + samples.size() + " samples:"
                        + "\nConductivity: " + averageConductivity
                        + "\nTemperature: " + averageTemperature
                        + "\nTurbidity: " + averageTurbidity
                        + "\nPh: " + averagePh;
        SpannableString descriptionSpan = new SpannableString(descriptionString);
        setSpan(descriptionString, descriptionSpan, String.valueOf(averageConductivity));
        setSpan(descriptionString, descriptionSpan, String.valueOf(averageTemperature));
        setSpan(descriptionString, descriptionSpan, String.valueOf(averageTurbidity));
        setSpan(descriptionString, descriptionSpan, String.valueOf(averagePh));

        return descriptionSpan;
    }

    private void setSpan(final String initial, final SpannableString spannableString, final String spannedSection) {
        int pos = -1;
        int len = spannedSection.length();
        while ((pos = initial.indexOf(spannedSection, pos + 1)) != -1) {
            spannableString.setSpan(new ForegroundColorSpan(health.color), pos, pos + len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void findHealth() {
        final double conductivityScore, temperatureScore, turbidityScore, phScore;
        if (averageConductivity <= 50) {
            conductivityScore = 1;
        } else if (averageConductivity <= 150) {
            conductivityScore = 2.0 / 3.0;
        } else if (averageConductivity <= 250) {
            conductivityScore = 1.0 / 3.0;
        } else {
            conductivityScore = 0;
        }
        if (averageTemperature <= 5) {
            temperatureScore = 1.0 / 3.0;
        } else if (averageTemperature <= 10) {
            temperatureScore = 2.0 / 3.0;
        } else if (averageTemperature <= 15) {
            temperatureScore = 1;
        } else if (averageTemperature <= 20) {
            temperatureScore = 2.0 / 3.0;
        } else if (averageTemperature <= 25) {
            temperatureScore = 1.0 / 3.0;
        } else {
            temperatureScore = 0;
        }
        if (averageTurbidity <= 55) {
            turbidityScore = 0;
        } else if (averageTurbidity <= 70) {
            turbidityScore = 1.0 / 3.0;
        } else if (averageTurbidity <= 100) {
            turbidityScore = 2.0 / 3.0;
        } else {
            turbidityScore = 1;
        }
        if (averagePh <= 5.5) {
            phScore = 0;
        } else if (averagePh <= 6.5) {
            phScore = 1.0 / 3.0;
        } else if (averagePh <= 8) {
            phScore = 1;
        } else if (averagePh <= 9.5) {
            phScore = 1.0 / 3.0;
        } else {
            phScore = 0;
        }

        final double healthScore = (conductivityScore + temperatureScore + turbidityScore + phScore) / 4;
        if (healthScore <= 1.0 / 3.0) {
            health = Health.Terrible;
        } else if (healthScore <= 2.0 / 3.0) {
            health = Health.Average;
        } else {
            health = Health.Good;
        }
    }

    private void findAverages() {
        averageConductivity = 0;
        averageTemperature = 0;
        averageTurbidity = 0;
        averagePh = 0;

        for (Sample sample : samples) {
            averageConductivity += sample.getConductivity();
            averageTemperature += sample.getTemperature();
            averageTurbidity += sample.getTurbidity();
            averagePh += sample.getPh();
        }

        findHealth();
    }
}