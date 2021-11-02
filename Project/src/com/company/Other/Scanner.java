package com.company.Other;

import java.io.IOException;
import java.util.InputMismatchException;

public class Scanner {
    private java.util.Scanner scan = new java.util.Scanner(System.in);

    /**
     * Scans a string
     * @return the scanned string
     */
    public String scanString() {
        System.out.printf(">");
        return scan.next();
    }

    /**
     * Scans the users input until he enters a integer
     * @return returns the integer the user entered
     */
    public int scanInt() {
        int returnValue = 0;
        boolean enteredAcceptableValue = false;
        while (!enteredAcceptableValue) {
            System.out.printf(">");
            try {
                returnValue = scan.nextInt();
                enteredAcceptableValue = true;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter a Number without decimals");
            }
        }
        return returnValue;
    }
}
