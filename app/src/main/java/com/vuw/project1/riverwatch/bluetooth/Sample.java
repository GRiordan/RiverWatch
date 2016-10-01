package com.vuw.project1.riverwatch.bluetooth;

/**
 * A class to hold an individual sample for water quality reports
 * Created by Liam De Grey(computernerddegrey@gmail.com) on 01-Oct-15.
 */
public class Sample {

    private final double conductivity;
    private final double temperature;
    private final double turbidity;
    private final double ph;

    public Sample(final double conductivity, final double temperature, final double turbidity, final double ph) {
        this.conductivity = conductivity;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.ph = ph;
    }

    public double getConductivity() {
        return conductivity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTurbidity() {
        return turbidity;
    }

    public double getPh() {
        return ph;
    }

}
