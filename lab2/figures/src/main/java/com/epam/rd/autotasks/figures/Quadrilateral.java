package com.epam.rd.autotasks.figures;

public class Quadrilateral extends Figure{
    private final Point a, b, c, d;
    public Quadrilateral(Point a, Point b, Point c, Point d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        points = new Point[]{a, b, c, d};
    }

    @Override
    public double area() {
        return Math.abs(
                (a.getX() * b.getY() + b.getX() * c.getY() + c.getX() * d.getY() + d.getX() * a.getY())
                        -
                        (a.getY() * b.getX() + b.getY() * c.getX() + c.getY() * d.getX() + d.getY() * a.getX())
        ) / 2;
    }
}
