package com.vuw.project1.riverwatch.objects;

/**
 * Created by James on 8/10/2016.
 */

public class Water_Report_Sample {
    public long id;
    public String time;
    public double temperature;
    public double pH;
    public double conductivity;
    public double turbidity;

    public Water_Report_Sample(long id, String time, double temperature, double pH, double conductivity, double turbidity){
        this.id = id;
        this.time = time;
        this.temperature = temperature;
        this.pH = pH;
        this.conductivity = conductivity;
        this.turbidity = turbidity;
    }
}
