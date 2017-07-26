package br.com.dfn.radar.model;

import java.io.Serializable;

/**
 * The type Temperature.
 */
public class Temperature implements Serializable {
    /**
     * The Temp.
     */
    private double temp;
    /**
     * The Temp max.
     */
    private double temp_max;
    /**
     * The Temp min.
     */
    private double temp_min;

    /**
     * Gets temp.
     *
     * @return the temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Sets temp.
     *
     * @param temp the temp
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * Gets temp max.
     *
     * @return the temp max
     */
    public double getTempMax() {
        return temp_max;
    }

    /**
     * Sets temp max.
     *
     * @param temp_max the temp max
     */
    public void setTempMax(double temp_max) {
        this.temp_max = temp_max;
    }

    /**
     * Gets temp min.
     *
     * @return the temp min
     */
    public double getTempMin() {
        return temp_min;
    }

    /**
     * Sets temp min.
     *
     * @param temp_min the temp min
     */
    public void setTempMin(double temp_min) {
        this.temp_min = temp_min;
    }
}
