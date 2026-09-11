package com.epam.rd.autotasks.segments;

class Point {
    public static final double PRECISION = 0.000001;
    private double x;
    private double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean equal(Point another){
        return Math.abs(x - another.getX()) <= PRECISION && Math.abs(y - another.getY()) <= PRECISION;
    }
}
