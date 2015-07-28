package basic.converters.util;

/**
 * Created by Edmar on 7/27/2015.
 */
public enum WeightUnit {
    CARAT {
        public double toGrain(double i) { return i * 3.086471670588; }
        public double toKilogram(double i) { return i * 0.0002; }
        public double toNewton(double i) { return i * 0.00196133; }
        public double toOunce(double i) { return i * 0.007054792389916; }
        public double toPennyWeight(double i) { return i * 0.1286029862745; }
        public double toPound(double i) { return i * 0.0004409245243698; }
        public double toQuarter(double i) { return i * 0.00001574730444178; }
        public double toTon(double i) { return i * 2.204622621849 * Math.pow(10, -7); }
    },
    KILOGRAM {
        public double toGrain(double i) { return i * 15432.35835294; }
        public double toCarat(double i) { return i * 5000; }
        public double toNewton(double i) { return i * 9.80665; }
        public double toOunce(double i) { return i * 35.27396194958; }
        public double toPennyWeight(double i) { return i * 643.0149313726; }
        public double toPound(double i) { return i * 2.204622621849; }
        public double toQuarter(double i) { return i * 0.07873652220889; }
        public double toTon(double i) { return i * 0.001102311310924; }
    },
    GRAIN {
        public double toKilogram(double i) { return i * 0.00006479891; }
        public double toCarat(double i) { return i * 0.32399455; }
        public double toNewton(double i) { return i * 0.0006354602307515; }
        public double toOunce(double i) { return i * 0.002285714285714; }
        public double toPennyWeight(double i) { return i * 0.04166666666667; }
        public double toPound(double i) { return i * 0.0001428571428571; }
        public double toQuarter(double i) { return i * 0.000005102040816327; }
        public double toTon(double i) { return i * 7.142857142857 * Math.pow(10, -8); }
    },
    NEWTON {
        public double toKilogram(double i) { return i * 0.1019716212978; }
        public double toCarat(double i) { return i * 509.858106489; }
        public double toGrain(double i) { return i * 1573.662601698; }
        public double toOunce(double i) { return i * 3.596943089595; }
        public double toPennyWeight(double i) { return i * 65.56927507075; }
        public double toPound(double i) { return i * 0.2248089430997; }
        public double toQuarter(double i) { return i * 0.00802889082499; }
        public double toTon(double i) { return i * 0.0001124044715499; }
    },
    OUNCE {
        public double toKilogram(double i) { return i * 0.028349523125; }
        public double toCarat(double i) { return i * 141.747615625; }
        public double toGrain(double i) { return i * 437.5; }
        public double toNewton(double i) { return i * 0.2780138509538; }
        public double toPennyWeight(double i) { return i * 18.22916666667; }
        public double toPound(double i) { return i * 0.0625; }
        public double toQuarter(double i) { return i * 0.002232142857143; }
        public double toTon(double i) { return i * 0.00003125; }
    },
    PENNYWEIGHT {
        public double toKilogram(double i) { return i * 0.00155517384; }
        public double toCarat(double i) { return i * 7.7758692; }
        public double toGrain(double i) { return i * 24; }
        public double toNewton(double i) { return i * 0.01525104553804; }
        public double toOunce(double i) { return i * 0.05485714285714; }
        public double toPound(double i) { return i * 0.003428571428571; }
        public double toQuarter(double i) { return i * 0.0001224489795918; }
        public double toTon(double i) { return i * 0.000001714285714286; }
    },
    POUND {
        public double toKilogram(double i) { return i * 0.45359237; }
        public double toCarat(double i) { return i * 2267.96185; }
        public double toGrain(double i) { return i * 7000; }
        public double toNewton(double i) { return i * 4.44822161526; }
        public double toOunce(double i) { return i * 16; }
        public double toPennyWeight(double i) { return i * 291.6666666667; }
        public double toQuarter(double i) { return i * 0.03571428571429; }
        public double toTon(double i) { return i * 0.0005; }
    },
    QUARTER {
        public double toKilogram(double i) { return i * 12.70058636; }
        public double toCarat(double i) { return i * 63502.9318; }
        public double toGrain(double i) { return i * 196000; }
        public double toNewton(double i) { return i * 124.5502052273; }
        public double toOunce(double i) { return i * 448; }
        public double toPennyWeight(double i) { return i * 8166.666666667; }
        public double toPound(double i) { return i * 28; }
        public double toTon(double i) { return i * 0.014; }
    },
    TON {
        public double toKilogram(double i) { return i * 907.18474; }
        public double toCarat(double i) { return i * 4535923.7; }
        public double toGrain(double i) { return i * 14000000; }
        public double toNewton(double i) { return i * 8896.443230521; }
        public double toOunce(double i) { return i * 32000; }
        public double toPennyWeight(double i) { return i * 583333.3333333; }
        public double toPound(double i) { return i * 2000; }
        public double toQuarter(double i) { return i * 71.42857142857; }
    };

    public double toKilogram(double i) { throw new AbstractMethodError(); }
    public double toCarat(double i) { throw new AbstractMethodError(); }
    public double toGrain(double i) { throw new AbstractMethodError(); }
    public double toNewton(double i) { throw new AbstractMethodError(); }
    public double toOunce(double i) { throw new AbstractMethodError(); }
    public double toPennyWeight(double i) { throw new AbstractMethodError(); }
    public double toPound(double i) { throw new AbstractMethodError(); }
    public double toQuarter(double i) { throw new AbstractMethodError(); }
    public double toTon(double i) { throw new AbstractMethodError(); }
}
