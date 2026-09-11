package com.epam.rd.autotasks.figures;

public class Triangle extends Figure {
    private final Point a, b, c;

    public Triangle(Point a, Point b, Point c) {
        if (a == null || b == null || c == null)
            throw new IllegalArgumentException();
        double area = area(a, b, c);
        if (area <= Point.delta)
            throw new IllegalArgumentException("Triangle is degenerate");
        this.a = a;
        this.b = b;
        this.c = c;
        this.points = new Point[]{a, b, c};
    }

    public static double area(Point a, Point b, Point c) {
        return Math.abs((a.getX() * (b.getY() - c.getY()) +
                b.getX() * (c.getY() - a.getY()) +
                c.getX() * (a.getY() - b.getY())) / 2.0);
    }

    @Override
    public double area() {
        return area(a, b, c);
    }

    @Override
    public Point centroid() {
        return new Point((a.getX() + b.getX() + c.getX()) / 3.0,
                (a.getY() + b.getY() + c.getY()) / 3.0);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (!(figure instanceof Triangle)) return false;
        Triangle other = (Triangle) figure;
        return pointsEqualSets(this.points, other.points);
    }

    private static boolean pointsEqualSets(Point[] p1, Point[] p2) {
        if (p1.length != p2.length) return false;
        boolean[] used = new boolean[p2.length];
        for (Point point : p1) {
            boolean found = false;
            for (int j = 0; j < p2.length; j++) {
                if (!used[j] && point.dist(p2[j]) < Point.delta) {
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }
}