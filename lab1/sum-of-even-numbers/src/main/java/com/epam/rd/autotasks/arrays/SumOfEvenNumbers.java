package com.epam.rd.autotasks.arrays;

import java.util.Arrays;

public class SumOfEvenNumbers {

    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 2, 8, 15, 199};

        System.out.println(sum(array));
    }

    public static int sum(int[] array){
        if (array == null)
            return 0;
        return Arrays.stream(array).filter((a) -> a % 2 == 0).sum();
    }
}
