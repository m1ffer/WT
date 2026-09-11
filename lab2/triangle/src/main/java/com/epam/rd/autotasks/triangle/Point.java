package com.epam.rd.autotasks.triangle;

public record Point(double x, double y) {
    public static final double PRECISION = 0.00001;
    public static double sqr(double x){
        return x * x;
    }
    public double distance(Point another){
        return Math.sqrt(sqr(x - another.x) + sqr(y - another.y));
    }


    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
