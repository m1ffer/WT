package com.epam.rd.autotasks.pizzasplit;

import java.util.Scanner;

public class PizzaSplit {
    private static int gcd(int a, int b){
        return b == 0 ? a : gcd(b, a % b);
    }
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)){
            int n = sc.nextInt();
            int m = sc.nextInt();
            System.out.println(n * m / gcd(n, m) / m);
        }
    }
}
