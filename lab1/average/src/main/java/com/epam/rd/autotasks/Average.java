package com.epam.rd.autotasks;

import java.util.Scanner;

public class Average {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            int avg = 0, i = 0;
            for (int n = scanner.nextInt(); n != 0; n = scanner.nextInt(), i++)
                avg += n;
            System.out.println(avg / i);
        }
    }

}