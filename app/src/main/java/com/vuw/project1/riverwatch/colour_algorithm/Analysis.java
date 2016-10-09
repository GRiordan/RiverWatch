package com.vuw.project1.riverwatch.colour_algorithm;


import android.content.Context;
import android.graphics.Bitmap;

import com.vuw.project1.riverwatch.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 7/15/2016.
 */
public class Analysis {
    private HSBColor nitrateColor, nitriteColor;
    private double nitrate, nitrite;
    private String path;

    public Analysis(){
        nitrate = 0;
        nitrite = 0;
        nitrateColor = null;
        nitriteColor = null;
    }

    public void processImages(Bitmap left, Bitmap middle, Bitmap right, Context c){

        //white balance the two colored images
        WhiteBalance whiteBalance = new WhiteBalance();
        Bitmap balancedLeft = whiteBalance.balance(left, middle);
        Bitmap balancedRight = whiteBalance.balance(right, middle);
        nitrateColor = new HSBImage(balancedRight).medianColor();
        nitriteColor = new HSBImage(balancedLeft).medianColor();

        //create maps of the different colors
        HashMap<Double, Integer> nitrateColors = createNitrateColArr(c);
        HashMap<Double, Integer> nitriteColors = createNitriteColArr(c);

        //match the colors from images to there best match
        ColorRecognition nitrateRecog = new ColorRecognition(nitrateColors);
        ColorRecognition nitriteRecog = new ColorRecognition(nitriteColors);

        //do the analysis
        Map<Double, Double> nitrateAnalysis = nitrateRecog.processImage(balancedRight);
        Map<Double, Double> nitriteAnalysis = nitriteRecog.processImage(balancedLeft);

        //process results
        processResults(nitrateAnalysis, nitriteAnalysis);
    }

    private void processResults(Map<Double, Double> nitrateAnalysis, Map<Double, Double> nitriteAnalysis) {
        double nitrateSum = 0;
        Double bestNitrateClass = null;
        Double secondBestNitrateClass = null;
        for(Map.Entry<Double, Double> nitrateClass : nitrateAnalysis.entrySet()){
            System.err.println("nitrate: " + nitrateClass.getKey() + ": " + nitrateClass.getValue());
            //Toast.makeText(c, "Nitrate: " + nitrateClass.getKey() + " " + nitrateClass.getValue(), Toast.LENGTH_LONG).show();
            if(bestNitrateClass == null || nitrateAnalysis.get(bestNitrateClass) > nitrateClass.getValue()){
                bestNitrateClass = nitrateClass.getKey();
            }
            else if(secondBestNitrateClass == null || nitrateAnalysis.get(secondBestNitrateClass) > nitrateClass.getValue()){
                secondBestNitrateClass = nitrateClass.getKey();
            }
        }

        Double bestNitriteClass = null;
        Double secondBestNitriteClass = null;
        for(Map.Entry<Double, Double> nitriteClass : nitriteAnalysis.entrySet()) {
            System.err.println("nitrite: " + nitriteClass.getKey() + ": " + nitriteClass.getValue());
            //Toast.makeText(c, "Nitrite" + nitriteClass.getKey() + " " + nitriteClass.getValue(), Toast.LENGTH_LONG).show();
            if (bestNitriteClass == null || nitriteAnalysis.get(bestNitriteClass) > nitriteClass.getValue()) {
                bestNitriteClass = nitriteClass.getKey();
            }
            else if (secondBestNitriteClass == null || nitriteAnalysis.get(secondBestNitriteClass) > nitriteClass.getValue()) {
                secondBestNitriteClass = nitriteClass.getKey();
            }
        }

        double invNitrate1 = 1 / nitrateAnalysis.get(bestNitrateClass);
        double invNitrate2 = 1 / nitrateAnalysis.get(secondBestNitrateClass);
        double nitrateProb = invNitrate1 / (invNitrate1 + invNitrate2);

        double invNitrite1 = 1 / nitriteAnalysis.get(bestNitriteClass);
        double invNitrite2 = 1 / nitriteAnalysis.get(secondBestNitriteClass);
        double nitriteProb = invNitrite1 / (invNitrite1 + invNitrite2);

        nitrite = (bestNitriteClass * nitriteProb + secondBestNitriteClass * (1 - nitriteProb));
        nitrate = (bestNitrateClass * nitrateProb + secondBestNitrateClass * (1 - nitrateProb));
    }

    private HashMap<Double, Integer> createNitrateColArr(Context c) {
        HashMap<Double, Integer> nitrateColors = new HashMap<>();

        nitrateColors.put(10., c.getResources().getColor(R.color.Nitrate10));
        nitrateColors.put(0., c.getResources().getColor(R.color.Nitrate0));
        nitrateColors.put(1., c.getResources().getColor(R.color.Nitrate1));
        nitrateColors.put(2., c.getResources().getColor(R.color.Nitrate2));
        nitrateColors.put(50., c.getResources().getColor(R.color.Nitrate50));
        nitrateColors.put(20., c.getResources().getColor(R.color.Nitrate20));
        nitrateColors.put(5., c.getResources().getColor(R.color.Nitrate5));

        return nitrateColors;
    }

    private HashMap<Double, Integer> createNitriteColArr(Context c) {
        HashMap<Double, Integer> nitriteColors = new HashMap<>();

        nitriteColors.put(1., c.getResources().getColor(R.color.Nitrite1));
        nitriteColors.put(3., c.getResources().getColor(R.color.Nitrite3));
        nitriteColors.put(1.5, c.getResources().getColor(R.color.Nitrite1_5));
        nitriteColors.put(0.3, c.getResources().getColor(R.color.Nitrite0_3));
        nitriteColors.put(0., c.getResources().getColor(R.color.Nitrite0));
        nitriteColors.put(0.15, c.getResources().getColor(R.color.Nitrite0_15));

        return nitriteColors;
    }

    //---------------//
    //    Getters    //
    //---------------//

    public double getNitrate(){
        return nitrate;
    }

    public double getNitrite(){
        return nitrite;
    }

    public HSBColor getNitrateColor(){
        return nitrateColor;
    }

    public HSBColor getNitriteColor(){
        return nitriteColor;
    }

}
