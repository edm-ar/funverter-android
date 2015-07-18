package basic.converters.util;

/**
 * Created by Edmar on 7/16/2015.
 */
public enum AreaUnit {
    ACRE {
        public double toAre (double i) { return i * 40.468564224; }
        public double toBarn (double i) { return i * 4.0468564224 * Math.pow(10, 31); }
        public double toHectare (double i) { return i * 0.40468564224; }
        public double toRood (double i) { return i * 4; }
        public double toSquareFoot (double i) { return i * 43560; }
        public double toSquareInch (double i) { return i * 6272640; }
        public double toSquareMeter (double i) { return i * 4046.8564224; }
        public double toSquareMile (double i) { return i * 0.0015625; }
        public double toSquareRod (double i) { return i * 160; }
        public double toSquareYard (double i) { return i * 4840; }
    },
    ARE {
        public double toAcre (double i) { return i * 0.02471053814672; }
        public double toBarn (double i) { return i * Math.pow(10, 30); }
        public double toHectare (double i) { return i * 0.01; }
        public double toRood (double i) { return i * 0.09884215258687; }
        public double toSquareFoot (double i) { return i * 1076.391041671; }
        public double toSquareInch (double i) { return i * 155000.3100006; }
        public double toSquareMeter (double i) { return i * 100; }
        public double toSquareMile (double i) { return i * 0.00003861021585424; }
        public double toSquareRod (double i) { return i * 3.953686103475; }
        public double toSquareYard (double i) { return i * 119.5990046301; }
    },
    BARN {
        public double toAcre (double i) { return i * 2.471053814672 * Math.pow(10, -32); }
        public double toAre (double i) { return i * Math.pow(10, -30); }
        public double toHectare (double i) { return i * Math.pow(10, -32); }
        public double toRood (double i) { return i * 9.884215258687 * Math.pow(10, -32); }
        public double toSquareFoot (double i) { return i * 1.076391041671 * Math.pow(10, -27); }
        public double toSquareInch (double i) { return i * 1.550003100006 * Math.pow(10, -25); }
        public double toSquareMeter (double i) { return i * Math.pow(10, -28); }
        public double toSquareMile (double i) { return i * 3.861021585425 * Math.pow(10, -35); }
        public double toSquareRod (double i) { return i * 3.953686103475 * Math.pow(10, -30); }
        public double toSquareYard (double i) { return i * 1.195990046301 * Math.pow(10, -28); }
    },
    HECTARE {
        public double toAcre (double i) { return i * 2.471053814672; }
        public double toAre (double i) { return i * 100; }
        public double toBarn (double i) { return i * Math.pow(10, 32); }
        public double toRood (double i) { return i * 9.884215258687; }
        public double toSquareFoot (double i) { return i * 107639.1041671; }
        public double toSquareInch (double i) { return i * 15500031.00006; }
        public double toSquareMeter (double i) { return i * 10000; }
        public double toSquareMile (double i) { return i * 0.003861021585424; }
        public double toSquareRod (double i) { return i * 395.3686103475; }
        public double toSquareYard (double i) { return i * 11959.90046301; }
    },
    ROOD {
        public double toAcre (double i) { return i * 0.25; }
        public double toAre (double i) { return i * 10.117141056; }
        public double toBarn (double i) { return i * 1.0117141056 * Math.pow(10, 31); }
        public double toHectare (double i) { return i * 0.10117141056; }
        public double toSquareFoot (double i) { return i * 10890; }
        public double toSquareInch (double i) { return i * 1568160; }
        public double toSquareMeter (double i) { return i * 1011.7141056; }
        public double toSquareMile (double i) { return i * 0.000390625; }
        public double toSquareRod (double i) { return i * 40; }
        public double toSquareYard (double i) { return i * 1210; }
    },
    SQUAREFOOT {
        public double toAcre (double i) { return i * 0.00002295684113866; }
        public double toAre (double i) { return i * 0.0009290304; }
        public double toBarn (double i) { return i * 9.290304 * Math.pow(10, 26); }
        public double toHectare (double i) { return i * 0.000009290304; }
        public double toRood (double i) { return i * 0.00009182736455463; }
        public double toSquareInch (double i) { return i * 144; }
        public double toSquareMeter (double i) { return i * 0.09290304; }
        public double toSquareMile (double i) { return i * 3.587006427915 * Math.pow(10, -8); }
        public double toSquareRod (double i) { return i * 0.003673094582185; }
        public double toSquareYard (double i) { return i * 0.1111111111111; }
    },
    SQUAREINCH {
        public double toAcre (double i) { return i * 1.594225079074 * Math.pow(10, -7); }
        public double toAre (double i) { return i * 0.0000064516; }
        public double toBarn (double i) { return i * 6.4516 * Math.pow(10, 24); }
        public double toHectare (double i) { return i * 6.4516 * Math.pow(10, -8); }
        public double toRood (double i) { return i * 6.376900316294 * Math.pow(10, -7); }
        public double toSquareFoot (double i) { return i * 0.006944444444445; }
        public double toSquareMeter (double i) { return i * 0.00064516; }
        public double toSquareMile (double i) { return i * 2.490976686052 * Math.pow(10, -10); }
        public double toSquareRod (double i) { return i * 0.00002550760126518; }
        public double toSquareYard (double i) { return i * 0.0007716049382716; }
    },
    SQUAREMETER {
        public double toAcre (double i) { return i * 0.0002471053814672; }
        public double toAre (double i) { return i * 0.01; }
        public double toBarn (double i) { return i *  Math.pow(10, 28); }
        public double toHectare (double i) { return i * 0.0001; }
        public double toRood (double i) { return i * 0.0009884215258687; }
        public double toSquareFoot (double i) { return i * 10.76391041671; }
        public double toSquareInch (double i) { return i * 1550.003100006; }
        public double toSquareMile (double i) { return i * 3.861021585425 * Math.pow(10, -7); }
        public double toSquareRod (double i) { return i * 0.03953686103475; }
        public double toSquareYard (double i) { return i * 1.195990046301; }
    },
    SQUAREMILE {
        public double toAcre (double i) { return i * 640; }
        public double toAre (double i) { return i * 25899.88110336; }
        public double toBarn (double i) { return i *  Math.pow(10, 28); }
        public double toHectare (double i) { return i * 258.9988110336; }
        public double toRood (double i) { return i * 2560; }
        public double toSquareFoot (double i) { return i * 27878400; }
        public double toSquareInch (double i) { return i * 4014489600L; }
        public double toSquareMeter (double i) { return i * 2589988.110336; }
        public double toSquareRod (double i) { return i * 102400; }
        public double toSquareYard (double i) { return i * 3097600; }
    },
    SQUAREROD {
        public double toAcre (double i) { return i * 0.00625; }
        public double toAre (double i) { return i *  0.2529285264; }
        public double toBarn (double i) { return i * 2.529285264 * Math.pow(10, 29); }
        public double toHectare (double i) { return i * 0.002529285264; }
        public double toRood (double i) { return i * 0.025; }
        public double toSquareFoot (double i) { return i * 272.25; }
        public double toSquareInch (double i) { return i * 39204; }
        public double toSquareMeter (double i) { return i * 25.29285264; }
        public double toSquareMile (double i) { return i * 0.000009765625; }
        public double toSquareYard (double i) { return i * 30.25; }
    },
    SQUAREYARD {
        public double toAcre (double i) { return i * 0.0002066115702479; }
        public double toAre (double i) { return i *  0.0083612736; }
        public double toBarn (double i) { return i * 8.3612736 * Math.pow(10, 27); }
        public double toHectare (double i) { return i * 0.000083612736; }
        public double toRood (double i) { return i * 0.0008264462809917; }
        public double toSquareFoot (double i) { return i * 9; }
        public double toSquareInch (double i) { return i * 1296; }
        public double toSquareMeter (double i) { return i * 0.83612736; }
        public double toSquareMile (double i) { return i * 3.228305785124 * Math.pow(10, -7); }
        public double toSquareRod (double i) { return i * 0.03305785123967; }
    };

    public double toAcre (double i) { throw new AbstractMethodError(); }
    public double toAre (double i) { throw new AbstractMethodError(); }
    public double toBarn (double i) { throw new AbstractMethodError(); }
    public double toHectare (double i) { throw new AbstractMethodError(); }
    public double toRood (double i) { throw new AbstractMethodError(); }
    public double toSquareFoot (double i) { throw new AbstractMethodError(); }
    public double toSquareInch (double i) { throw new AbstractMethodError(); }
    public double toSquareMeter (double i) { throw new AbstractMethodError(); }
    public double toSquareMile (double i) { throw new AbstractMethodError(); }
    public double toSquareRod (double i) { throw new AbstractMethodError(); }
    public double toSquareYard (double i) { throw new AbstractMethodError(); }
}
