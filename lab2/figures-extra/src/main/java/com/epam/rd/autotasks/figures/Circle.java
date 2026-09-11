package com.epam.rd.autotasks.figures;

import java.util.Locale;

public class Circle extends Figure {
    private final Point center;
    private final double r;

    public Circle(Point center, double radius) {
        if (radius <= Point.delta || center == null)
            throw new IllegalArgumentException();
        this.center = center;
        this.r = radius;
        this.points = new Point[]{center};
    }

    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public Point centroid() {
        return center;
    }

    @Override
    public Point leftmostPoint() {
        return new Point(center.getX() - r, center.getY());
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s[%s%s]",
                this.getClass().getSimpleName(),
                pointsToString(),
                Point.formatDouble(r));
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (!(figure instanceof Circle))
            return false;
        Circle other = (Circle) figure;
        return center.dist(other.center) < Point.delta &&
                Math.abs(r - other.r) < Point.delta;
    }
}