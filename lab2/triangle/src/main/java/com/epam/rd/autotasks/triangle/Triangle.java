package com.epam.rd.autotasks.triangle;

public class Triangle{
    private final double ab, bc, ca;
    private final Point a, b, c;
    public Triangle(Point a, Point b, Point c) {
        ab = a.distance(b);
        bc = b.distance(c);
        ca = c.distance(a);
        if (ab + bc <= ca ||
            bc + ca <= ab ||
            ab + ca <= bc)
            throw new IllegalArgumentException();
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public double area() {
        double p = (ab + bc + ca) / 2;
        return Math.sqrt(p * (p - ab) * (p - bc) * (p - ca));
    }
    public Point centroid(){
        return new Point((a.x() + b.x() + c.x()) / 3, (a.y() + b.y() + c.y()) / 3);
    }

}
