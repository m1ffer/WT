package com.epam.rd.autotasks.snail;

import java.util.Scanner;

public class Snail
{
    public static void main(String[] args)
    {
        try(Scanner sc = new Scanner(System.in)){
            int a = sc.nextInt(), b = sc.nextInt(), h = sc.nextInt();
            if (a <= b && h > a){
                System.out.println("Impossible");
                return;
            }
            if (a >= h){
                System.out.println("1");
                return;
            }
            h -= a;
            System.out.println(1 + h / (a - b));
        }
    }
}
