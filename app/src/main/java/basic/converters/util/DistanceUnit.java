package basic.converters.util;

import basic.converters.helper.Unit;

/**
 * Created by Edmar on 6/20/2015.
 */
public enum DistanceUnit implements Unit {

    INCHES {
        public double toCentimeters(double i){ return i * 2.54; };
        public double toMillimeters(double i){ return i * 25.4; };
        public double toFeet(double i){ return i * 0.0833333; };
        public double toYards(double i){ return i * 0.0277778; };
        public double toMeters(double i) { return i * 0.0254; };
        public double toKilometers(double i) { return i * (2.54 * Math.pow(10, -5)); };
        public double toMiles(double i) { return i * (1.57828 * Math.pow(10, -5)); };
    },
    CENTIMETERS {
        public double toInches(double i){ return i * 0.393701; };
        public double toMillimeters(double i){ return i * 10; };
        public double toFeet(double i){ return i * 0.0328084; };
        public double toYards(double i){ return i * 0.0109361; };
        public double toMeters(double i) { return i * 0.01; };
        public double toKilometers(double i) { return i * Math.pow(10, -5); };
        public double toMiles(double i) { return i * (6.21371 * Math.pow(10, -6)); };
    },
    MILLIMETERS {
        public double toCentimeters(double i){ return i * 0.1; };
        public double toInches(double i){ return i * 0.0393701; };
        public double toFeet(double i){ return i * 0.00328084; };
        public double toYards(double i){ return i * 0.00109361; };
        public double toMeters(double i) { return i * 0.001; };
        public double toKilometers(double i) { return i *  Math.pow(10, -6); };
        public double toMiles(double i) { return i * (6.21371 * Math.pow(10, -7)); };
    },
    FEET {
        public double toCentimeters(double i){ return i * 30.48; };
        public double toMillimeters(double i){ return i * 304.8; };
        public double toInches(double i){ return i * 12; };
        public double toYards(double i){ return i * 0.333333; };
        public double toMeters(double i) { return i * 0.3048; };
        public double toKilometers(double i) { return i * 0.0003048; };
        public double toMiles(double i) { return i * 0.000189394; };
    },
    YARDS {
        public double toCentimeters(double i){ return i * 91.44; };
        public double toMillimeters(double i){ return i * 914.4; };
        public double toInches(double i){ return i * 36; };
        public double toFeet(double i){ return i * 3; };
        public double toMeters(double i) { return i * 0.9144; };
        public double toKilometers(double i) { return i * 0.0009144; };
        public double toMiles(double i) { return i * 0.000568182; };
    },
    METERS {
        public double toCentimeters(double i){ return i * 100; };
        public double toMillimeters(double i){ return i * 1000; };
        public double toFeet(double i){ return i * 3.28084; };
        public double toYards(double i){ return i * 1.09361; };
        public double toInches(double i) { return i * 39.3701; };
        public double toKilometers(double i) { return i * 0.001; };
        public double toMiles(double i) { return i * 0.000621371; };
    },
    KILOMETERS {
        public double toCentimeters(double i){ return i * 100000; };
        public double toMillimeters(double i){ return i * 1000000; };
        public double toFeet(double i){ return i * 3280.84; };
        public double toYards(double i){ return i * 1093.61; };
        public double toMeters(double i) { return i * 1000; };
        public double toInches(double i) { return i * 39370.1; };
        public double toMiles(double i) { return i * 0.621371; };
    },
    MILES {
        public double toCentimeters(double i){ return i * 160934; };
        public double toMillimeters(double i){ return i * 1609344; };
        public double toFeet(double i){ return i * 5280; };
        public double toYards(double i){ return i * 1760; };
        public double toMeters(double i) { return i * 1609.34; };
        public double toKilometers(double i) { return i * 1.60934; };
        public double toInches(double i) { return i * 63360; };
    };

    public double toCentimeters(double i){ throw new AbstractMethodError(); };
    public double toMillimeters(double i){ throw new AbstractMethodError(); };
    public double toFeet(double i){ throw new AbstractMethodError(); };
    public double toYards(double i){ throw new AbstractMethodError(); };
    public double toMeters(double i) { throw new AbstractMethodError(); };
    public double toKilometers(double i) { throw new AbstractMethodError(); };
    public double toInches(double i) { throw new AbstractMethodError(); };
    public double toMiles(double i) { throw new AbstractMethodError(); };
}
