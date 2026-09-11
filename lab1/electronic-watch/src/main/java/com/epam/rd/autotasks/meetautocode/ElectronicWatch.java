package com.epam.rd.autotasks.meetautocode;

import java.util.Scanner;

public class ElectronicWatch {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            int seconds = scanner.nextInt() % 86400;
            final int hours = seconds / 3600;
            final int minutes = seconds / 60 % 60;
            seconds %= 60;
            System.out.printf("%d:%02d:%02d", hours, minutes, seconds);
        }
    }
}
