package com.epam.rd.autotasks.godutch;

import java.util.Scanner;

public class GoDutch {

    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)){
            int pr = sc.nextInt();
            int n = sc.nextInt();
            if (pr < 0)
                System.out.println("Bill total amount cannot be negative");
            else if (n <= 0)
                System.out.println("Number of friends cannot be negative or zero");
            else
                System.out.println(pr * 11 / 10 / n);
        }
    }
}
