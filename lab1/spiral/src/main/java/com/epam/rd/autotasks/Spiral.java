package com.epam.rd.autotasks;

import java.util.Arrays;

class Spiral {
    static int[][] spiral(int rows, int columns) {
        int[][] res = new int[rows][columns];
        int n = 1;
        for (int step = 0; step < rows / 2; step++){
            for (int i = step; i < columns - step; i++)
                res[step][i] = n++;
            for (int i = step + 1; i < rows - step; i++)
                res[i][columns - step - 1] = n++;
            for (int i = step + 1; i < columns - step; i++)
                res[rows - step - 1][columns - i - 1] = n++;
            for (int i = step + 1; i < rows - step - 1; i++){
                res[rows - i - 1][step] = n++;
            }
        }
        for (int i = rows / 2; i < columns - rows / 2 && rows % 2 == 1; i++)
            res[rows / 2][i] = n++;
        return res;
    }
}
