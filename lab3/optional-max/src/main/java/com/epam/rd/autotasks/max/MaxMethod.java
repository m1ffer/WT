package com.epam.rd.autotasks.max;

import java.util.Arrays;
import java.util.OptionalInt;

public class MaxMethod {
    public static OptionalInt max(int[] values) {
        if (values == null)
            return OptionalInt.empty();
        return Arrays.stream(values)
                .max();
    }
}
