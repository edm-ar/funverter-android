package com.funverter.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edmar on 8/28/2015.
 */
public abstract class UnitSymbols {

    private static String square = "\u00B2";
    public static Map<String,String> symbols = new HashMap<>();
    static {
        symbols.put("minutes", "min");
        symbols.put("seconds", "s");
        symbols.put("hours", "h");
        symbols.put("inches", "in");
        symbols.put("centimeters", "cm");
        symbols.put("millimeters", "mm");
        symbols.put("feet", "ft");
        symbols.put("yards", "yds");
        symbols.put("meters", "m");
        symbols.put("miles", "m");
        symbols.put("kilometers", "km");
        symbols.put("fahrenheit", "°f");
        symbols.put("celsius", "°c");
        symbols.put("kelvin", "°k");
        symbols.put("rankine", "°r");
        symbols.put("reaumur", "°ré");
        symbols.put("cubicmeter", "m³");
        symbols.put("cubicinch", "in³");
        symbols.put("cubicyard", "yds³");
        symbols.put("liter", "l");
        symbols.put("gallon", "gal");
        symbols.put("usfluidounce", "fl oz");
        symbols.put("cubicfoot", "ft³");
        symbols.put("uspint", "pts");
        symbols.put("acre", "acre");
        symbols.put("are", "a");
        symbols.put("barn", "b");
        symbols.put("hectare", "ha");
        symbols.put("squarefoot", "ft".concat(square));
        symbols.put("squareinch", "in".concat(square));
        symbols.put("squaremeter", "m".concat(square));
        symbols.put("squaremile", "mi".concat(square));
        symbols.put("squarerod", "rd".concat(square));
        symbols.put("squareyard", "yds".concat(square));
        symbols.put("kilogram", "kg");
        symbols.put("carat", "cd");
        symbols.put("grain", "gr");
        symbols.put("newton", "n");
        symbols.put("ounce", "oz");
        symbols.put("pennyweight", "dwt");
        symbols.put("pound", "lbs");
        symbols.put("quarter", "lbs");
    }
}
