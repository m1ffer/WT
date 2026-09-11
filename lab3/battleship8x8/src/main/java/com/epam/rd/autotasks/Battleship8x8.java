package com.epam.rd.autotasks;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        int x = shot.charAt(0) - 'A', y = shot.charAt(1) - '1';
        int pos = y * 8 + x;
        long mask = Long.MIN_VALUE >>> pos;
        shots |= mask;
        return (ships & mask) != 0;
    }

    public String state() {
        StringBuilder sb = new StringBuilder();
        for (long mask = Long.MIN_VALUE, i = 0; mask != 0; mask >>>= 1, i++){
            if (i != 0 && i % 8 == 0)
                sb.append("\n");
            if ((shots & mask) == mask)
                if ((ships & mask) == mask)
                    sb.append("☒");
                else
                    sb.append("×");
            else if ((ships & mask) == mask)
                sb.append("☐");
            else
                sb.append(".");
        }
        return sb.toString();
    }
}
