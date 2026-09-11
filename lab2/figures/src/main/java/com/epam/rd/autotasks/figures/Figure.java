package com.epam.rd.autotasks.figures;

import java.util.Locale;

public abstract class Figure{
    protected Point[] points;

    public abstract double area();

    public String pointsToString(){
        StringBuilder sb = new StringBuilder();
        for (Point point : points)
            sb.append(point.toString());
        return sb.toString();
    }

    public String toString() {
        return String.format("%s[%s]",
                this.getClass().getSimpleName(),
                pointsToString());
        }

    public Point leftmostPoint(){
        Point leftmost = points[0];
        for (int i = 1; i < points.length; i++)
            if (points[i].getX() < leftmost.getX())
                leftmost = points[i];
        return leftmost;
    }
}
