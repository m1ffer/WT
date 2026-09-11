package com.epam.rd.autotasks;

class CycleSwap {
    static void cycleSwap(int[] array) {
        cycleSwap(array, 1);
    }

    static void cycleSwap(int[] array, int shift) {
        if (array.length == 0)
            return;
        int[] tmp = new int[shift];
        System.arraycopy(array, array.length - shift, tmp, 0, shift);
        System.arraycopy(array, 0, array, shift, array.length - shift);
        System.arraycopy(tmp, 0, array, 0, shift);
    }
}
