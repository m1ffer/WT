package com.epam.rd.autotasks.meetstrangers;

import java.io.IOException;
import java.util.Scanner;

public class HelloStrangers {
    public static void main(String[] args) throws IOException {
        try(Scanner sc = new Scanner(System.in)){
            int n = sc.nextInt();
            if (n == 0)
                System.out.println("Oh, it looks like there is no one here");
            else if (n < 0)
                System.out.println("Seriously? Why so negative?");
            else {
                sc.nextLine();
                while (n-- > 0)
                    System.out.println("Hello, " + sc.nextLine());
            }
        }
    }
}
