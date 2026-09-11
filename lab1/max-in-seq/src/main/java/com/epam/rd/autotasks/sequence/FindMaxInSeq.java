package com.epam.rd.autotasks.sequence;
import java.util.Scanner;

public class FindMaxInSeq {
    public static int max() {
        try(Scanner sc = new Scanner(System.in)){
            int max = Integer.MIN_VALUE;
            for (int n = sc.nextInt(); n != 0; n = sc.nextInt())
                max = Math.max(max, n);
            return max;
        }
    }

    public static void main(String[] args) {

        System.out.println("Test your code here!\n");

        // Get a result of your code

        System.out.println(max());
    }
}
