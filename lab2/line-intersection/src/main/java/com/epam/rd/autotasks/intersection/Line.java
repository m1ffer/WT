package com.epam.rd.autotasks.intersection;

public record Line(int k, int b){
    public Point intersection(Line line){
        if(k == line.k())
            return null;
        int x = (line.b() - b) / (k - line.k());
        return new Point(x, k * x + b);
    }
}
