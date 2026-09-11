package com.epam.rd.autotasks.figures;

public class Quadrilateral extends Figure {
    private final Point a, b, c, d;

    public Quadrilateral(Point a, Point b, Point c, Point d) {
        if (a == null || b == null || c == null || d == null)
            throw new IllegalArgumentException();
        Point[] pts = {a, b, c, d};

        for (int i = 0; i < pts.length; i++)
            for (int j = i + 1; j < pts.length; j++)
                if (pts[i].dist(pts[j]) < Point.delta)
                    throw new IllegalArgumentException("Duplicate points");

        double sign = 0;
        for (int i = 0; i < 4; i++) {
            Point p1 = pts[i];
            Point p2 = pts[(i + 1) % 4];
            Point p3 = pts[(i + 2) % 4];
            double cross = crossProduct(p1, p2, p3);
            if (Math.abs(cross) < Point.delta)
                throw new IllegalArgumentException();
            double curSign = Math.signum(cross);
            if (sign == 0)
                sign = curSign;
            else if (sign != curSign)
                throw new IllegalArgumentException();
        }

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.points = pts;
    }

    private static double crossProduct(Point p1, Point p2, Point p3) {
        return (p2.getX() - p1.getX()) * (p3.getY() - p2.getY()) -
                (p2.getY() - p1.getY()) * (p3.getX() - p2.getX());
    }

    private static double polygonArea(Point[] pts) {
        double area = 0;
        int n = pts.length;
        for (int i = 0; i < n; i++) {
            Point p1 = pts[i];
            Point p2 = pts[(i + 1) % n];
            area += p1.getX() * p2.getY() - p2.getX() * p1.getY();
        }
        return Math.abs(area) / 2.0;
    }

    @Override
    public double area() {
        return polygonArea(this.points);
    }

    @Override
    public Point centroid() {
        double area1 = Triangle.area(a, b, c);
        double area2 = Triangle.area(a, c, d);
        Point centroid1 = new Point((a.getX() + b.getX() + c.getX()) / 3.0,
                (a.getY() + b.getY() + c.getY()) / 3.0);
        Point centroid2 = new Point((a.getX() + c.getX() + d.getX()) / 3.0,
                (a.getY() + c.getY() + d.getY()) / 3.0);
        double total = area1 + area2;
        double cx = (area1 * centroid1.getX() + area2 * centroid2.getX()) / total;
        double cy = (area1 * centroid1.getY() + area2 * centroid2.getY()) / total;
        return new Point(cx, cy);
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (!(figure instanceof Quadrilateral))
            return false;
        Quadrilateral other = (Quadrilateral) figure;
        return pointsEqualSets(this.points, other.points);
    }

    private static boolean pointsEqualSets(Point[] p1, Point[] p2) {
        if (p1.length != p2.length)
            return false;
        boolean[] used = new boolean[p2.length];
        for (Point point : p1) {
            boolean found = false;
            for (int j = 0; j < p2.length; j++)
                if (!used[j] && point.dist(p2[j]) < Point.delta) {
                    used[j] = true;
                    found = true;
                    break;
                }
            if (!found)
                return false;
        }
        return true;
    }
}