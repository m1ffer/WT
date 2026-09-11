package com.epam.rd.autotasks.max;

import java.util.Arrays;

public class MaxMethod {
    public static int max(int[] values) {
        return Arrays.stream(values).max().getAsInt();
    }
}
