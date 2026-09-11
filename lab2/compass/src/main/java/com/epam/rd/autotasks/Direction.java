package com.epam.rd.autotasks;

public enum Direction {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

    Direction(final int degrees) {
        this.degrees = degrees;
    }

    private final int degrees;

    public static Direction ofDegrees(int degrees) {
        degrees = Math.floorMod(degrees, 360);
         for (Direction d : values())
             if (d.degrees == degrees)
                 return d;
         return null;
    }

    public static Direction closestToDegrees(int degrees) {
        degrees = Math.floorMod(degrees, 360);
        Direction tmpD = null;
        int tmp = Integer.MAX_VALUE;
        for (Direction d : values()) {
            int diff = Math.abs(d.degrees - degrees);
            diff = Math.min(diff, 360 - diff);
            if (diff < tmp) {
                tmpD = d;
                tmp = diff;
            }
        }
        return tmpD;
    }

    public Direction opposite() {
        return ofDegrees(degrees + 180);
    }

    public int differenceDegreesTo(Direction direction) {
        int tmp = Math.abs(degrees - direction.degrees);
        return tmp > 180 ? 360 - tmp : tmp;
    }
}
