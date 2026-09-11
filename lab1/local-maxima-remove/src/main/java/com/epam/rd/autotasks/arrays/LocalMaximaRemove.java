package com.epam.rd.autotasks.arrays;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalMaximaRemove {

    public static void main(String[] args) {
        int[] array = new int[]{18, 1, 3, 6, 7, -5};

        System.out.println(Arrays.toString(removeLocalMaxima(array)));
    }

    public static int[] removeLocalMaxima(int[] array){
        ArrayList<Integer> a = new ArrayList<Integer>(array.length);
        if (array[0] <= array[1])
            a.add(array[0]);
        for (int i = 1; i < array.length - 1; i++)
            if (!(array[i] > array[i - 1] && array[i] > array[i + 1]))
                a.add(array[i]);
        if (array[array.length - 1] <= array[array.length - 2])
            a.add(array[array.length - 1]);
        int[] res = new int[a.size()];
        Arrays.setAll(res, a::get);
        return res;
    }
}
