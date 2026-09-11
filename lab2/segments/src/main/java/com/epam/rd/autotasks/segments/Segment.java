package com.epam.rd.autotasks.segments;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.pow;

public record Segment(Point start, Point end) {
    public Segment {
        if (start.equal(end))
            throw new IllegalArgumentException();
    }
    private static double sqr(double x){
        return x * x;
    }
    double length() {
        return Math.sqrt(sqr(start.getX() - end.getX()) + sqr(start.getY() - end.getY()));
    }

    Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    public Point intersection(Segment another) {
        double x1 = start.getX(), y1 = start.getY();
        double x2 = end.getX(), y2 = end.getY();
        double x3 = another.start.getX(), y3 = another.start.getY();
        double x4 = another.end.getX(), y4 = another.end.getY();
        double dx1 = x2 - x1, dy1 = y2 - y1;
        double dx2 = x4 - x3, dy2 = y4 - y3;
        double denom = dx1 * dy2 - dy1 * dx2;
        if (Math.abs(denom) < Point.PRECISION) {
        }
        double t = ((x3 - x1) * dy2 - (y3 - y1) * dx2) / denom;
        double u = ((x3 - x1) * dy1 - (y3 - y1) * dx1) / denom;
        if (t >= -Point.PRECISION && t <= 1 + Point.PRECISION &&
                u >= -Point.PRECISION && u <= 1 + Point.PRECISION) {
            double x = x1 + t * dx1;
            double y = y1 + t * dy1;
            return new Point(x, y);
        }
        return null;
    }

}
