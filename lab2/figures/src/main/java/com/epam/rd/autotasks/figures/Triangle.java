package com.epam.rd.autotasks.figures;

public class Triangle extends Figure{
    private final Point a, b, c;
    public Triangle(Point a, Point b, Point c){
        this.a = a;
        this.b = b;
        this.c = c;
        points = new Point[]{a, b, c};
    }

    @Override
    public double area() {
        double ab = a.dist(b);
        double bc = b.dist(c);
        double ca = c.dist(a);
        double p = (ab + bc + ca) / 2;
        return Math.sqrt(p * (p - ab) * (p - bc) * (p - ca));
    }
}
