package com.epam.rd.autotasks.figures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Point{
    private final double x, y;
    public final static double delta = 0.0001;
    @Override
    public String toString(){
        return String.format("(%s,%s)",
                formatDouble(x),
                formatDouble(y));
    }

    private static double sqr(double x){
        return x * x;
    }
    public static String formatDouble(double x){
        String s = Double.toString(x)
                .replace(',', '.')
                .replaceAll("0*$", "");
        if (s.endsWith("."))
            return s + "0";
        return s;
    }

    public double dist(Point another){
        return Math.sqrt(sqr(x - another.x) + sqr(y - another.y));
    }
}
