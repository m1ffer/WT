package com.epam.rd.autotasks.figures;

import java.util.Locale;

public class Circle extends Figure{
    private final Point center;
    private final double r;

    public Circle(Point center, double r){
        this.center = center;
        this.r = r;
        points = new Point[]{center};
    }

    @Override
    public String toString(){
        return String.format(Locale.US, "%s[%s%s]",
                this.getClass().getSimpleName(),
                pointsToString(),
                Point.formatDouble(r));
    }

    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public Point leftmostPoint(){
        return new Point(center.getX() - r, center.getY());
    }
}
