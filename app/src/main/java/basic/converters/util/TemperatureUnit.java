package basic.converters.util;

/**
 * Created by Edmar on 7/4/2015.
 */
public enum TemperatureUnit {

    FAHRENHEIT {
        public double toFahrenheit(double i) { return i; }
        public double toCelsius(double i) { return (i -  C1) / C2; }
        public double toKelvin(double i) { return (i + C3) / C2; }
        public double toRankine(double i) { return i + C3; }
        public double toReaumur(double i) { return (i - C1) / C4; }
    },
    CELSIUS {
        public double toCelsius(double i) { return i; }
        public double toFahrenheit(double i) { return i * C2 + C1; }
        public double toKelvin(double i) { return i + C5; }
        public double toRankine(double i) { return i * C2 + C1 + C3; }
        public double toReaumur(double i) { return i * C7; }
    },
    KELVIN {
        public double toKelvin(double i) { return i; }
        public double toCelsius(double i) { return i - C5; }
        public double toFahrenheit(double i) { return i * C2 - C3; }
        public double toRankine(double i) { return i * C2; }
        public double toReaumur(double i) { return (i - C5) * C7; }
    },
    RANKINE {
        public double toRankine(double i) { return i; }
        public double toCelsius(double i) { return (i - C1 - C3) / C2; }
        public double toKelvin(double i) { return i - C3; }
        public double toFahrenheit(double i) { return i / C2; }
        public double toReaumur(double i) { return (i - C1 - C3) / C2; }
    },
    REAUMUR {
        public double toReaumur(double i) { return i; }
        public double toCelsius(double i) { return i * C6; }
        public double toKelvin(double i) { return i * C6 + C5; }
        public double toRankine(double i) { return i * C6 + C1 + C3; }
        public double toFahrenheit(double i) { return i * C4 + C1; }
    };

    static final int C1 = 32;
    static final double C2 = 1.8;
    static final double C3 = 459.67;
    static final double C4 = 2.25;
    static final double C5 = 273.15;
    static final double C6 = 1.25;
    static final double C7 = 0.8;

    public double toFahrenheit(double i) { throw new AbstractMethodError(); }
    public double toCelsius(double i) { throw new AbstractMethodError(); }
    public double toKelvin(double i) { throw new AbstractMethodError(); }
    public double toRankine(double i) { throw new AbstractMethodError(); }
    public double toReaumur(double i) { throw new AbstractMethodError(); }
    }
