package com.funverter.util;

/**
 * Created by edmar on 2/15/15.
 *
 * This is class the model for conversion entries and will contain the data that we will save in the database
 * and show it in the user interface.
 */
public class ConversionEntry {

    private long id;
    private double value;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
