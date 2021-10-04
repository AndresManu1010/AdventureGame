package com.company.Other;

import java.io.IOException;
import java.util.InputMismatchException;

public class Scanner {
    java.util.Scanner scan = new java.util.Scanner(System.in);
    public String scanString(){
        System.out.printf(">");
     return scan.next();
    }
    public int scanInt(){
        int returnValue = 0;
        boolean enteredAcceptableValue = false;
        while (!enteredAcceptableValue){
            System.out.printf(">");
            try {
                returnValue = scan.nextInt();
                enteredAcceptableValue = true;
            }catch (InputMismatchException e){
                System.out.println("Please Enter a Number without decimals");
            }
        }
        return  returnValue;
    }
    public double scanDouble(){
        double returnValue = 0;
        boolean enteredAcceptableValue = false;
        while (!enteredAcceptableValue){
            System.out.printf(">");
            try {
                returnValue = scan.nextDouble();
                enteredAcceptableValue = true;
            }catch (InputMismatchException e){
                System.out.println("Please Enter a Number without decimals");
            }
        }
        return  returnValue;
    }
}
