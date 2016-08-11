package com.vuw.project1.riverwatch.colour_algorithm;

/**
 * Created by George on 25/07/2016.
 */
public class NitrateResult {
    private Double nitrate;
    private Double nitrite;
    private String info;

    public NitrateResult(Double nitrate, Double nitrite, String info){
        this.nitrate = nitrate;
        this.nitrite = nitrite;
        this.info = info;
    }

    public Double getNitrate(){
        return nitrate;
    }

    public Double getNitrite(){
        return nitrite;
    }

    public String getInfo(){
        return info;
    }
}
