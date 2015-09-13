package basic.converters.util;

/**
 * Created by edmar on 1/31/15.
 */

//TODO override TimeUnit's implementation so that it is easier to manage at AbstractConverter
public enum TimeUnitExtension {
    //TODO add Centuries and Milleniums
    SECONDS {
        public double toSeconds(double d) { return d; }
        public double toMinutes(double d) { return d/(C4/C3); }
        public double toHours(double d)   { return d/(C5/C3); }
        public double toDays(double d)    { return d/(C6/C3); }
        public double toWeeks(double d) { return d * Math.pow(3.8, -7); }
        public double toMonths(double d) { return d * Math.pow(1.6534, -6); }
        public double toYears(double d) { return d * Math.pow(3.1689, -8); }
    },
    MINUTES {
        public double toSeconds(double d) { return x(d, C4/C3, MAX/(C4/C3)); }
        public double toMinutes(double d) { return d; }
        public double toHours(double d)   { return d/(C5/C4); }
        public double toDays(double d)    { return d/(C6/C4); }
        public double toWeeks(double d) { return d * Math.pow(9.9206, -5); }
        public double toMonths(double d) { return d * Math.pow(2.2816, -5); }
        public double toYears(double d) { return d * Math.pow(1.9013, -6); }
    },
    HOURS {
        public double toSeconds(double d) { return x(d, C5/C3, MAX/(C5/C3)); }
        public double toMinutes(double d) { return x(d, C5/C4, MAX/(C5/C4)); }
        public double toHours(double d)   { return d; }
        public double toDays(double d)    { return d/(C6/C5); }
        public double toWeeks(double d) { return d * 0.00595238; }
        public double toMonths(double d) { return d * 0.00136895; }
        public double toYears(double d) { return d * 0.00011408; }
    },
    DAYS {
        public double toSeconds(double d) { return x(d, C6/C3, MAX/(C6/C3)); }
        public double toMinutes(double d) { return x(d, C6/C4, MAX/(C6/C4)); }
        public double toHours(double d)   { return x(d, C6/C5, MAX/(C6/C5)); }
        public double toDays(double d)    { return d; }
        public double toWeeks(double d) { return d * 0.142857; }
        public double toMonths(double d) { return d * 0.0328549; }
        public double toYears(double d) { return d * 0.00273791; }
    },
    WEEKS {
        public double toSeconds(double d) { return d * 604800; }
        public double toMinutes(double d) { return d * 10080; }
        public double toHours(double d)   { return d * 168; }
        public double toDays(double d)    { return d * 7; }
        public double toWeeks(double d) { return d; }
        public double toYears(double d) { return d * 0.0191654; }
        public double toMonths(double d) { return d * 0.229984; }
    },
    MONTHS {
        public double toSeconds(double d) { return d * Math.pow(2.63, 6); }
        public double toMinutes(double d) { return d * 43829.1; }
        public double toHours(double d)   { return d * 730.484; }
        public double toDays(double d)    { return d * 30.4368; }
        public double toWeeks(double d) { return d * 4.34812; }
        public double toYears(double d) { return d * 0.0833333; }
        public double toMonths(double d) { return d; }
    },
    YEARS {
        public double toSeconds(double d) { return d * Math.pow(3.156, 7); }
        public double toMinutes(double d) { return d * 525949; }
        public double toHours(double d)   { return d * 8765.81; }
        public double toDays(double d)    { return d * 365.242; }
        public double toWeeks(double d) { return d * 52.1775; }
        public double toYears(double d) { return d; }
        public double toMonths(double d) { return d * 12; }
    };

    // Handy constants for conversion methods
    static final double C0 = 1L;
    static final double C1 = C0 * 1000L;
    static final double C2 = C1 * 1000L;
    static final double C3 = C2 * 1000L;
    static final double C4 = C3 * 60L;
    static final double C5 = C4 * 60L;
    static final double C6 = C5 * 24L;

    static final double MAX = Double.MAX_VALUE;

    /**
     * Scale d by m, checking for overflow.
     * This has a short name to make above code more readable.
     */
    static double x(double d, double m, double over) {
        if (d >  over) return Double.MAX_VALUE;
        if (d < -over) return Double.MIN_VALUE;
        return d * m;
    }

    public double toWeeks(double millis) { throw new AbstractMethodError(); }

    public double toMonths(double millis) { throw new AbstractMethodError(); }

    public double toYears(double millis) { throw new AbstractMethodError(); }

    public double toSeconds(double duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to {@code MINUTES.convert(duration, this)}.
     * @param duration the duration
     * @return the converted duration,
     * or {@code double.MIN_VALUE} if conversion would negatively
     * overflow, or {@code double.MAX_VALUE} if it would positively overflow.
     * @since 1.6
     */
    public double toMinutes(double duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to {@code HOURS.convert(duration, this)}.
     * @param duration the duration
     * @return the converted duration,
     * or {@code double.MIN_VALUE} if conversion would negatively
     * overflow, or {@code double.MAX_VALUE} if it would positively overflow.
     * @since 1.6
     */
    public double toHours(double duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to {@code DAYS.convert(duration, this)}.
     * @param duration the duration
     * @return the converted duration
     * @since 1.6
     */
    public double toDays(double duration) {
        throw new AbstractMethodError();
    }

}
